package me.kokumaji.warrior.api.translation;

import org.bukkit.ChatColor;

public class Color {

    private static final char COLOR_CHAR = '&';

    public static String translateColor(String str) {
        return translateColor(str, COLOR_CHAR);
    }

    public static String translateColor(String str, char colorChar) {
        return ChatColor.translateAlternateColorCodes(colorChar, str);
    }

}
