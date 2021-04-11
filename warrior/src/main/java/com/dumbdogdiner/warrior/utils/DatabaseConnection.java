package com.dumbdogdiner.warrior.utils;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathParticle;
import com.dumbdogdiner.warrior.api.user.cosmetics.DeathSound;
import com.dumbdogdiner.warrior.api.user.cosmetics.WarriorTitle;
import com.dumbdogdiner.warrior.api.user.UserData;
import com.dumbdogdiner.warrior.api.user.settings.GameplaySettings;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import com.dumbdogdiner.warrior.api.user.settings.VisualSettings;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;

public class DatabaseConnection {

    private static class Query {

        public static final String ALTER_DATA_TABLE_PARTICLES = "ALTER TABLE %s ADD COLUMN IF NOT EXISTS death_particles int DEFAULT 0 AFTER death_sounds";
        public static final String ALTER_DATA_TABLE_TITLES    = "ALTER TABLE %s ADD COLUMN IF NOT EXISTS warrior_titles int DEFAULT 0 AFTER death_particles";
        public static final String ALTER_DATA_TABLE_TOTALXP    = "ALTER TABLE %s ADD COLUMN IF NOT EXISTS total_xp int DEFAULT 0 AFTER coins";
        public static final String ALTER_DATA_TABLE_RELXP    = "ALTER TABLE %s ADD COLUMN IF NOT EXISTS relative_xp int DEFAULT 0 AFTER total_xp";
        public static final String ALTER_DATA_LANG_CODE    = "ALTER TABLE %s ADD COLUMN IF NOT EXISTS lang VARCHAR(5) DEFAULT 'EN_US' AFTER privacy";

        public static final String CREATE_STATS_TABLE = "CREATE TABLE IF NOT EXISTS %s (uuid VARCHAR(36), kills int, deaths int, coins int, death_sounds int, first_join datetime NULL DEFAULT '1970-01-01 00:00:01', last_join datetime NULL DEFAULT CURRENT_TIMESTAMP(), total_time bigint, UNIQUE (uuid))";
        public static final String CREATE_GENERAL_USER_SETTINGS = "CREATE TABLE IF NOT EXISTS %s (uuid VARCHAR(36) NOT NULL, can_fly bit DEFAULT false, player_visibility tinyint DEFAULT 0 CHECK (player_visibility > -1 AND player_visibility < 3), notifications bit DEFAULT true, privacy tinyint DEFAULT 0 CHECK (privacy > -1 AND privacy < 4), lang VARCHAR(5) DEFAULT 'EN_US', title VARCHAR(32) NULL, last_reset datetime DEFAULT CURRENT_TIMESTAMP, UNIQUE(uuid))";
        public static final String CREATE_GAMEPLAY_SETTINGS = "CREATE TABLE IF NOT EXISTS %s (uuid VARCHAR(36) NOT NULL, show_kills bit DEFAULT 0, show_timer bit DEFAULT 0, active_deathsound VARCHAR(24) NULL DEFAULT NULL, active_deathparticle VARCHAR(24) NULL DEFAULT NULL, armor_color VARCHAR(7) NULL DEFAULT NULL, UNIQUE(uuid))";
        public static final String CREATE_VISUAL_SETTINGS = "CREATE TABLE IF NOT EXISTS %s (uuid VARCHAR(36) NOT NULL, particle_mode tinyint DEFAULT 2 CHECK (particle_mode > -1 AND particle_mode < 3), gore_level tinyint DEFAULT 1 CHECK (gore_level > -1 AND gore_level < 3), UNIQUE(uuid))";

        public static final String INSERT_NEW_USER = "INSERT IGNORE INTO %s VALUES ('%2s', 0, 0, 0, DEFAULT, DEFAULT, DEFAULT, DEFAULT,  DEFAULT, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 0)";
        public static final String INSERT_NEW_GENERAL_SETTINGS = "INSERT IGNORE INTO %s VALUES ('%2s', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT)";
        public static final String INSERT_NEW_GAMEPLAY_SETTINGS = "INSERT IGNORE INTO %s VALUES ('%2s', DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT)";
        public static final String INSERT_NEW_VISUAL_SETTINGS = "INSERT IGNORE INTO %s VALUES ('%2s', DEFAULT, DEFAULT)";

        public static final String GET_USER_DATA = "SELECT * FROM %s WHERE uuid = '%2s'";
        public static final String UPDATE_USER_DATA = "UPDATE %s SET kills = ?, deaths = ?, coins = ?, total_xp = ?, relative_xp = ?, death_sounds = ?, death_particles = ?, warrior_titles = ?, last_join = ?, total_time = ? WHERE uuid = '%2s'";
        public static final String UPDATE_USER_SETTINGS = "UPDATE %s SET can_fly = ?, player_visibility = ?, notifications = ?, privacy = ?, lang = ?, title = ?, last_reset = ? WHERE uuid = '%2s'";
        public static final String UPDATE_GAME_SETTINGS = "UPDATE %s SET show_kills = ?, show_timer = ?, active_deathsound = ?, active_deathparticle = ? WHERE uuid = '%2s'";
        public static final String UPDATE_VISUAL_SETTINGS = "UPDATE %s SET particle_mode = ?, gore_level = ? WHERE uuid = '%2s'";
    }

    private final Warrior self;
    private final FileConfiguration conf;

    private HikariDataSource ds;

    public static final String STATS_TABLE = "warrior_user_data";
    public static final String GENERAL_SETTINGS_TABLE = "warrior_user_settings";
    public static final String GAMEPLAY_SETTINGS_TABLE = "warrior_user_gameplay_settings";
    private static final String VISUAL_SETTINGS_TABLE = "warrior_user_visual_settings";

    public DatabaseConnection(Warrior plugin, FileConfiguration config) throws SQLException {
        this.self = plugin;
        this.conf = config;

        String dbHost = conf.getString("mysql-settings.db-host");
        int    dbPort = conf.getInt("mysql-settings.db-port");
        String dbName = conf.getString("mysql-settings.db-name");
        String dbUser = conf.getString("mysql-settings.db-user");
        String dbPass = conf.getString("mysql-settings.db-pass");

        HikariConfig dbConf = new HikariConfig();
        dbConf.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%2s", dbHost, dbPort, dbName));
        dbConf.setUsername(dbUser);
        dbConf.setPassword(dbPass);
        dbConf.setConnectionTimeout(1000);
        dbConf.setMaximumPoolSize(32);

        if(ds == null) {
            try {
                ds = new HikariDataSource(dbConf);
            } catch(Exception err) {
                String msg = String.format("Could not connect to Database! %s", err.getMessage());
                Warrior.getPluginLogger().error(msg);
            }
        }

        makeTables();

    }

    private void makeTables() throws SQLException {
        if(this.ds == null) {
            String msg = "Could not create SQL tables! Is the connection alive?";
            Warrior.getPluginLogger().warn(msg);
            return;
        }

        Connection conn = ds.getConnection();
        if(conn != null && conn.isValid(3)) {
            Statement stmt = conn.createStatement();
            stmt.addBatch(String.format(Query.CREATE_STATS_TABLE, STATS_TABLE));
            stmt.addBatch(String.format(Query.CREATE_GENERAL_USER_SETTINGS, GENERAL_SETTINGS_TABLE));
            stmt.addBatch(String.format(Query.CREATE_GAMEPLAY_SETTINGS, GAMEPLAY_SETTINGS_TABLE));
            stmt.addBatch(String.format(Query.CREATE_VISUAL_SETTINGS, VISUAL_SETTINGS_TABLE));

            // Update Table if it's a pre 1.4 Table
            stmt.addBatch(String.format(Query.ALTER_DATA_TABLE_PARTICLES, STATS_TABLE));
            stmt.addBatch(String.format(Query.ALTER_DATA_TABLE_TITLES, STATS_TABLE));
            stmt.addBatch(String.format(Query.ALTER_DATA_TABLE_TOTALXP, STATS_TABLE));
            stmt.addBatch(String.format(Query.ALTER_DATA_TABLE_RELXP, STATS_TABLE));
            stmt.addBatch(String.format(Query.ALTER_DATA_LANG_CODE, GENERAL_SETTINGS_TABLE));

            stmt.executeBatch();

            conn.close();
        }
    }

    public void insertUser(User user) {
        try {
            if(this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", user.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                Statement stmt = conn.createStatement();

                stmt.addBatch(String.format(Query.INSERT_NEW_USER, STATS_TABLE, user.getUserId()));
                stmt.addBatch(String.format(Query.INSERT_NEW_GENERAL_SETTINGS, GENERAL_SETTINGS_TABLE, user.getUserId()));
                stmt.addBatch(String.format(Query.INSERT_NEW_GAMEPLAY_SETTINGS, GAMEPLAY_SETTINGS_TABLE, user.getUserId()));
                stmt.addBatch(String.format(Query.INSERT_NEW_VISUAL_SETTINGS, VISUAL_SETTINGS_TABLE, user.getUserId()));

                stmt.executeBatch();

                conn.close();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public UserData getData(UUID userId) {
        UserData data = new UserData(userId);
        try {
            if (this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", userId);
                Warrior.getPluginLogger().warn(msg);
                return data;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.GET_USER_DATA, STATS_TABLE, userId));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    data.setKills(rs.getInt("kills"));
                    data.setDeaths(rs.getInt("deaths"));
                    data.setCoins(rs.getInt("coins"));
                    data.setDeathSounds(rs.getInt("death_sounds"));
                    data.setDeathParticles(rs.getInt("death_particles"));
                    data.setTitles(rs.getInt("warrior_titles"));
                    data.setFirstJoin(rs.getTimestamp("first_join").getTime());
                    data.setLastJoin(rs.getTimestamp("last_join").getTime());
                    data.setTotalTime(rs.getLong("total_time"));

                    data.setTotalXp(rs.getInt("total_xp"));
                    data.setRelativeXp(rs.getInt("relative_xp"));

                    data.setSuccessful(true);
                }

                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return data;

    }

    public void close() {
        if(!ds.isClosed()) this.ds.close();
    }

    public boolean isRunning() {
        if(ds == null) return false;
        return ds.isRunning();
    }

    public GeneralSettings getUserSettings(UUID uuid) {
        GeneralSettings settings = new GeneralSettings(uuid);
        try {
            if (this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", uuid);
                Warrior.getPluginLogger().warn(msg);
                return settings;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.GET_USER_DATA, GENERAL_SETTINGS_TABLE, uuid));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    settings.canFly(rs.getBoolean("can_fly"));
                    settings.setPlayerVisibility(rs.getInt("player_visibility"));
                    settings.receiveNotifications(rs.getBoolean("notifications"));
                    settings.setPrivacyLevel(rs.getInt("privacy"));
                    settings.setLanguage(LanguageCode.valueOf(rs.getString("lang")));
                    settings.setTitle(WarriorTitle.fromString(rs.getString("title")));
                    settings.setLastReset(rs.getTimestamp("last_reset").getTime());
                }

                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return settings;
    }

    public GameplaySettings getGameplaySettings(UUID userId) {
        GameplaySettings settings = new GameplaySettings(userId);
        try {
            if (this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", userId);
                Warrior.getPluginLogger().warn(msg);
                return settings;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.GET_USER_DATA, GAMEPLAY_SETTINGS_TABLE, userId));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    settings.showKills(rs.getBoolean("show_kills"));
                    settings.showTimer(rs.getBoolean("show_timer"));
                    settings.setActiveSound(DeathSound.fromString(rs.getString("active_deathsound")));
                    settings.setActiveParticle(DeathParticle.fromString(rs.getString("active_deathparticle")));
                }

                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return settings;
    }

    public VisualSettings getVisualSettings(UUID userId) {
        VisualSettings settings = new VisualSettings(userId);
        try {
            if (this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", userId);
                Warrior.getPluginLogger().warn(msg);
                return settings;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.GET_USER_DATA, VISUAL_SETTINGS_TABLE, userId));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    settings.setParticleMode(rs.getInt("particle_mode"));
                    settings.setGoreLevel(rs.getInt("gore_level"));
                }

                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    // TODO: ADD METHODS FOR USER/GAMEPLAY SETTINGS SAVING

    public void saveData(UserData data) {
        try {
            if (this.ds == null) {
                String msg = String.format("Could not save user data for %s! Is the connection alive?", data.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.UPDATE_USER_DATA, STATS_TABLE, data.userId));
                stmt.setInt(1, data.kills);
                stmt.setInt(2, data.deaths);
                stmt.setInt(3, data.coins);
                stmt.setInt(4, data.totalXp);
                stmt.setInt(5, data.relativeXp);
                stmt.setInt(6, data.deathSounds);
                stmt.setInt(7, data.deathParticles);
                stmt.setInt(8, data.titles);
                stmt.setTimestamp(9, new Timestamp(data.lastJoin));
                stmt.setLong(10, data.totalTime);

                stmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveSettings(GeneralSettings data) {
        if(data == null) return;
        try {
            if (this.ds == null) {
                String msg = String.format("Could not save user data for %s! Is the connection alive?", data.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.UPDATE_USER_SETTINGS, GENERAL_SETTINGS_TABLE, data.getUserId()));
                stmt.setBoolean(1, data.canFly());
                stmt.setInt(2, data.getPlayerVisibility());
                stmt.setBoolean(3, data.receiveNotifications());
                stmt.setInt(4, data.getPrivacyLevel());
                stmt.setString(5, data.getLanguage().toString());
                stmt.setString(6, data.getTitle() == null ? WarriorTitle.EMPTY.name() : data.getTitle().name());
                stmt.setTimestamp(7, new Timestamp(data.getLastReset()));

                stmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveGameplaySettings(GameplaySettings data) {
        if(data == null) return;
        try {
            if (this.ds == null) {
                String msg = String.format("Could not save user data for %s! Is the connection alive?", data.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.UPDATE_GAME_SETTINGS, GAMEPLAY_SETTINGS_TABLE, data.getUserId()));
                stmt.setBoolean(1, data.showKills());
                stmt.setBoolean(2, data.showTimer());
                stmt.setString(3, data.getActiveSound() == null ? DeathSound.DEFAULT.name() : data.getActiveSound().name());
                stmt.setString(4, data.getActiveParticle() == null ? DeathParticle.HEART.name() : data.getActiveParticle().name());

                stmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveVisualSettings(VisualSettings data) {
        if(data == null) return;
        try {
            if (this.ds == null) {
                String msg = String.format("Could not save user data for %s! Is the connection alive?", data.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.UPDATE_VISUAL_SETTINGS, VISUAL_SETTINGS_TABLE, data.getUserId()));
                stmt.setInt(1, data.getParticleMode());
                // if easter egg is enabled, set to 1
                stmt.setInt(2, Math.min(1, data.getGoreLevel()));

                stmt.executeUpdate();
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getStatus() {
        if(ds == null) return "&c&lERROR";
        else return "&a&lACTIVE";
    }

}
