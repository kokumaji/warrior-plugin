package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.Warrior;

public class DefaultMessages {

    private static Translator TR = Warrior.getInstance().getTranslator();

    public static String COMMAND_SYNTAX_ERROR = TR.translate("command-messages.syntax-error", false);
    public static String SUBCMD_SYNTAX = TR.translate("command-messages.subcmd-syntax", false);
    public static String COMMAND_PERM_ERROR = TR.translate("command-messages.permission-error", false);
    public static String COMMAND_GENERAL_ERROR = TR.translate("command-messages.general-error", true);

    public static String PLUGIN_RELOAD_SUCCESS = TR.translate("command-messages.reload-success", true);

}
