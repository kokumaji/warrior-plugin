package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.translation.Constants;
import com.dumbdogdiner.Warrior.api.translation.Translator;

public class DefaultMessages {

    public static String COMMAND_SYNTAX_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_SYNTAX, false);
    public static String SUBCMD_SYNTAX = Warrior.getTranslator().translate(Constants.Lang.ERROR_SUBCMD, false);
    public static String COMMAND_PERM_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_PERM, false);
    public static String COMMAND_GENERAL_ERROR = Warrior.getTranslator().translate(Constants.Lang.ERROR_GENERAL, false);

    //public static String PLUGIN_RELOAD_SUCCESS = Warrior.getTranslator().translate("command-messages.reload-config", true);
}
