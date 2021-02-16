package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.translation.DefaultFontInfo;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationUtil {

    public static final String HL = "&m                                                &c";

    public static String HL(int length) {
        StringBuilder hl = new StringBuilder();
        hl.append(" ".repeat(Math.max(0, length)));

        return "&m" + hl + "&r";
    }
    private final static int CENTER_CHAT_PX = 154;
    private final static int MAX_CHAT_PX = 250;

    public static String prettyMessage(String... args) {
        if(args.length == 1 && args[0].contains("\n")) args = args[0].split("\n");

        List<String> finalMsg = new ArrayList<>();
        finalMsg.add(TranslationUtil.translateColor(Warrior.getInstance().COMMAND_HEADER));
        finalMsg.addAll(Arrays.asList(args));
        finalMsg.add("&8" + TranslationUtil.translateColor(HL));

        return String.join("\n", centerStrings(finalMsg.toArray(String[]::new)));
    }

    public static String centerString(String message) {
            message = translateColor(message);

            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : message.toCharArray()) {
                if (c == '§') {
                    previousCode = true;
                    continue;
                } else if (previousCode) {
                    previousCode = false;
                    if (c == 'l' || c == 'L') {
                        isBold = true;
                        continue;
                    } else isBold = false;
                } else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            int toCompensate = CENTER_CHAT_PX - halvedMessageSize;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }

            return sb.toString() + message + ChatColor.RESET;

    }


    public static String[] centerStrings(String... messageArray) {
        List<String> finalMsg = new ArrayList<>();
        for(String s : messageArray) {
            finalMsg.add(centerString(s));
        }

        return finalMsg.toArray(String[]::new);
    }

    public static String getPrefix() {
        return Warrior.getInstance().getConfig().getString("general-settings.plugin-prefix");
    }

    public static String translateColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
