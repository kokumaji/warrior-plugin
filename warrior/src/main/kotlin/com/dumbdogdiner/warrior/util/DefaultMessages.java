package com.dumbdogdiner.warrior.util;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.Constants;

public class DefaultMessages {

    public static String COMMAND_CLIENT_ONLY;
    public static String COMMAND_SYNTAX_ERROR;
    public static String SUBCMD_SYNTAX;
    public static String COMMAND_PERM_ERROR;
    public static String COMMAND_GENERAL_ERROR;

    // GENERAL PLUGIN LOG MESSAGES
    public static String LOADED_OBJECT = "Loaded {amount} {type}(s) from data folder!";

    // SQL RELATED MESSAGES
    public static final String SQL_ERROR_NOTICE = "&c&lWARNING! &7Warrior does not have an active SQL connection available. User Data won't be loaded/saved until a working SQL database is connected. Use &b/warrior reload database &7to reconnect to your SQL database.";
    public static final String SQL_CONNECT_ATTEMPT = "&7Attempting to connect to SQL database...";
    public static final String SQL_CONNECT_SUCCESS = "&aSuccessfully connected to SQL database!";
    public static final String SQL_CONNECT_FAILURE = "&cCould not connect to SQL database! Please check the console for further details.";

    static {
        update();
    }

    public static void update() {
        COMMAND_CLIENT_ONLY = Warrior.getTranslator().translate(Constants.Lang.ERROR_CLIENT_CMD);
        COMMAND_SYNTAX_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_SYNTAX);
        SUBCMD_SYNTAX = Warrior.getTranslator().translate(Constants.Lang.ERROR_SUBCMD);
        COMMAND_PERM_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_PERM);
        COMMAND_GENERAL_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_GENERAL);
    }

    //public static String PLUGIN_RELOAD_SUCCESS = Warrior.getTranslator().translate("command-messages.reload-config", true);
}
