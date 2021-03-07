package com.dumbdogdiner.warrior.utils;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.models.WarriorData;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DatabaseConnection {

    private static class Query {
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (uuid VARCHAR(36), kills int, deaths int, coins int, death_sounds int, UNIQUE (uuid))";
        public static final String INSERT_NEW_USER = "INSERT IGNORE INTO %s VALUES ('%2s', 0, 0, 0, 0)";
        public static final String GET_USER_DATA = "SELECT * FROM %s WHERE uuid = '%2s'";
        public static final String UPDATE_USER_DATA = "UPDATE %s SET kills = %d, deaths = %2d, coins = %3d, death_sounds = %4d WHERE uuid = '%2s'";
    }

    private Warrior self;
    private FileConfiguration conf;

    private HikariDataSource ds;

    public static final String TABLE_NAME = "warrior_user_data";

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
            PreparedStatement stmt = conn.prepareStatement(String.format(Query.CREATE_TABLE, TABLE_NAME));
            stmt.executeUpdate();
            conn.close();
        }
    }

    public WarriorData getData(UUID userId) {
        WarriorData data = new WarriorData(userId);
        try {
            if (this.ds == null) {
                String msg = String.format("Could not get user data for %s! Is the connection alive?", userId);
                Warrior.getPluginLogger().warn(msg);
                return data;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.INSERT_NEW_USER, TABLE_NAME, userId));
                stmt.executeUpdate();

                stmt = conn.prepareStatement(String.format(Query.GET_USER_DATA, TABLE_NAME, userId));
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    data.setKills(rs.getInt("kills"));
                    data.setDeaths(rs.getInt("deaths"));
                    data.setCoins(rs.getInt("coins"));
                    data.setDeathSounds(rs.getInt("death_sounds"));
                    data.setSuccessful(true);
                }

                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return data;

    }


    public void saveData(WarriorData data) {
        try {
            if (this.ds == null) {
                String msg = String.format("Could not save user data for %s! Is the connection alive?", data.getUserId());
                Warrior.getPluginLogger().warn(msg);
                return;
            }

            Connection conn = ds.getConnection();
            if (conn != null && conn.isValid(3)) {
                // UPDATE %s SET kills = %d, deaths = %2d, coins = %3d, death_sounds = %4d WHERE uuid = '%2s'
                PreparedStatement stmt = conn.prepareStatement(String.format(Query.UPDATE_USER_DATA, TABLE_NAME,
                                        data.getKills(),
                                        data.getDeaths(),
                                        data.getCoins(),
                                        data.getDeathSounds(),
                                        data.getUserId())
                );

                stmt.executeUpdate();
                conn.close();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
