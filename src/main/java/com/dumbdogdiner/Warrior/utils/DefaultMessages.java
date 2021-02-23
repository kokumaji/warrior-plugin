package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.api.translation.Translator;

public class DefaultMessages {

    public static String COMMAND_CLIENT_ONLY;
    public static String COMMAND_SYNTAX_ERROR;
    public static String SUBCMD_SYNTAX;
    public static String COMMAND_PERM_ERROR;
    public static String COMMAND_GENERAL_ERROR;

    static {
        update();
    }

    public static void update() {
        COMMAND_CLIENT_ONLY = Warrior.getTranslator().translate(Constants.Lang.ERROR_CLIENT_CMD, false);
        COMMAND_SYNTAX_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_SYNTAX, false);
        SUBCMD_SYNTAX = Warrior.getTranslator().translate(Constants.Lang.ERROR_SUBCMD, false);
        COMMAND_PERM_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_PERM, false);
        COMMAND_GENERAL_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_GENERAL, false);
    }

    //public static String PLUGIN_RELOAD_SUCCESS = Warrior.getTranslator().translate("command-messages.reload-config", true);
}
