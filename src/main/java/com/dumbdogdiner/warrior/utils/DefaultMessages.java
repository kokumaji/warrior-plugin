package com.dumbdogdiner.warrior.utils;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.Constants;

public class DefaultMessages {

    public static String COMMAND_CLIENT_ONLY;
    public static String COMMAND_SYNTAX_ERROR;
    public static String SUBCMD_SYNTAX;
    public static String COMMAND_PERM_ERROR;
    public static String COMMAND_GENERAL_ERROR;

    // GENERAL PLUGIN LOG MESSAGES
    public static String LOADED_OBJECT = "Loaded {AMOUNT} {TYPE}(s) from data folder!";

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
