package com.dumbdogdiner.warrior.utils;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.DefaultFontInfo;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.awt.*;
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
        String prefix = Warrior.getInstance().getConfig().getString("general-settings.plugin-prefix");
        if(!prefix.endsWith(" ")) prefix = prefix + " ";

        return TranslationUtil.translateColor(prefix);
    }

    public static String translateColor(String str) {
        if(str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static String applyColorGradient(String string, Color start, Color end, int steps) {
        return applyColorGradient(string, getColorGradient(start, end, steps), steps);
    }

    public static String applyColorGradient(String string, List<Color> colors, int steps) {
        if((string.length() / steps) < 1) steps = string.length();
        List<String> strings = new ArrayList<String>();
        int index = 0;
        int i = 0;

        while (index < string.length()) {
            Color c = colors.get(Math.min(i, colors.size() - 1));
            strings.add(net.md_5.bungee.api.ChatColor.of(c) + string.substring(index, Math.min(index + (string.length() / steps), string.length())));
            index += (string.length() / steps);

            i++;
        }

        return String.join("", strings);
    }

    public static ArrayList<Color> getColorGradient(Color start, Color end, int steps) {
        ArrayList<Color> colors = new ArrayList<>();
        int stepR = ((end.getRed() - start.getRed()) / (steps - 1));
        int stepG = ((end.getGreen() - start.getGreen()) / (steps - 1));
        int stepB = ((end.getBlue() - start.getBlue()) / (steps - 1));

        for (int i = 0; i < steps; i++)
        {

            colors.add(new Color(start.getRed() + (stepR * i),
                    start.getGreen() + (stepG * i),
                    start.getBlue() + (stepB * i)));
        }

        return colors;
    }

    public static String readableLocation(Location location, boolean showVar, boolean showWorld) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String output;

        if(showVar) {
            output = String.format("X%d Y%2d Z%3d", x, y, z);
        } else {
            output = String.format("%d %2d %3d", x, y, z);
        }

        if(showWorld) {
            output = String.format(output + ", World %s", location.getWorld().getName());
        }

        return output;

    }

}
