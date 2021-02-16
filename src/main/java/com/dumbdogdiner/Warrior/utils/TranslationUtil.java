package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.api.translation.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TranslationUtil {

    public static final String HL = "&m                                                &c";

    public static String HL(int length) {
        StringBuilder hl = new StringBuilder();
        for(int i = 0; i < length; i++) {
            hl.append(" ");
        }

        return "&m" + hl + "&r";
    }
    private final static int CENTER_CHAT_PX = 154;
    private final static int MAX_CHAT_PX = 250;

    public static void centerMessage(Player player, String... messageArray) {
        for (String message : messageArray) {
            if (message == null || message.equals("")) player.sendMessage("");
            message = translateColor(message);

            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : message.toCharArray()) {
                if (c == '§') {
                    previousCode = true;
                    continue;
                } else if (previousCode == true) {
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
            player.sendMessage(sb.toString() + message);
        }
    }

    public static String translateColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
