package com.dumbdogdiner.warrior.api.util;

import com.dumbdogdiner.warrior.api.Warrior;
import com.dumbdogdiner.warrior.api.translation.DefaultFontInfo;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.user.settings.GeneralSettings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Translation utilities.
 */
public class TranslationUtil {
    private TranslationUtil() {}

    public static String stringifyList(List<String> stringList) {
        if(stringList == null) return "none";
        if(stringList.size() == 1) return stringList.get(0);
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringList.size(); i++) {
            if((i == stringList.size() - 2)) {
                sb.append(stringList.get(i));
                continue;
            }
            if(!(i == stringList.size() - 1)) sb.append(stringList.get(i)).append(", ");
            else sb.append(" and ").append(stringList.get(i));
        }

        return sb.toString();
    }

    public static String privacyString(WarriorUser user) {
        GeneralSettings settings = user.getSettings();

        return settings.getPrivacyLevel() > 0 ? settings.getPrivacyLevel() < 3 ?
                settings.getPrivacyLevel() < 2 ? "Hide Last Join" : "Hide First + Last Join"
                : "Fully Hidden!" : "All Public!";
    }

    public static void sendToMultiple(List<Player> list, String msg, boolean includeConsole) {
        Warrior.getService().getLogger().info(msg);
        for(Player p : list) {
            p.sendMessage(translateColor(msg));
        }
    }

    public static void sendToMultiple(List<Player> list, String msg) {
        sendToMultiple(list, msg, false);
    }

    protected static class URLPair {
        String fullPath;
        String shortened;

        public URLPair(String fullUrl, String shortenedUrl) {
            this.fullPath = fullUrl;
            this.shortened = shortenedUrl;
        }

        public String getShortened() {
            return shortened;
        }

        public String getFullPath() {
            return fullPath;
        }

    }

    private static final Pattern urlPattern = Pattern.compile("(https:\\/\\/|http:\\/\\/)((?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?");

    private static final String[] FULLWIDTH = {
            " ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ",
            "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ"
    };

    private static final String[] HALFWIDTH = {
            " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    };

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
        finalMsg.add(TranslationUtil.translateColor(Warrior.getService().getCommandHeader()));
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
        // TODO: Add default and null handing.
        String prefix = Warrior.getService().getConfig().getString("general-settings.plugin-prefix");
        if(!prefix.endsWith(" ")) prefix = prefix + " ";

        return TranslationUtil.translateColor(prefix);
    }

    public static String translateColor(String str) {
        if(str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static boolean isHexColor(String string) {
        Pattern p = Pattern.compile("^#([a-fA-F0-9]{6})$");
        Matcher m = p.matcher(string);

        return m.find();
    }

    public static String applyColorGradient(String string, String hexColorStart, String hexColorEnd, int steps) {
        if(!(isHexColor(hexColorStart) && isHexColor(hexColorEnd))) return string;
        return applyColorGradient(
                string, Color.decode(hexColorStart),
                Color.decode(hexColorEnd), steps
        );
    }

    public static String capitalize(String string) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < string.length(); i++) {
            if(i == 0) {
                sb.append(Character.toUpperCase(string.charAt(i)));
                continue;
            }
            sb.append(Character.toLowerCase(string.charAt(i)));
        }

        return sb.toString();
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

    public static boolean validateUsername(String string) {
        return MathUtil.inRange(string.length(), 3, 16) && !containsSpecialCharacter(string);
    }

    private static boolean containsSpecialCharacter(String string) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9_]");
        Matcher matcher = pattern.matcher(string);

        return matcher.find();
    }

    public static String toFullwidth(String string) {

        StringBuilder result = new StringBuilder();
        for(Character c : string.toCharArray()) {
            int index = MathUtil.indexOf(HALFWIDTH, String.valueOf(c));
            if(index == -1) result.append(c);
            else result.append(FULLWIDTH[index]);
        }

        return result.toString();

    }

    public static URLPair findURL(String text) {
        Matcher matcher = urlPattern.matcher(text);

        if(matcher.find()) {
            return new URLPair(matcher.group(0), matcher.group(2));
        }
        return null;
    }

    public static net.md_5.bungee.api.chat.TextComponent convertURLs(String original) {
        net.md_5.bungee.api.chat.TextComponent finalComp = new net.md_5.bungee.api.chat.TextComponent();
        net.md_5.bungee.api.chat.TextComponent tmp = new net.md_5.bungee.api.chat.TextComponent();

        String[] split = original.split(" ");
        int i = 0;
        for(String s : split) {
            URLPair url = findURL(s + " ");
            if((url) == null) {
                tmp.setText(tmp.getText() + s + " ");
                if(split.length == i + 1) finalComp.addExtra(tmp);
            } else {
                finalComp.addExtra(tmp);
                tmp = new net.md_5.bungee.api.chat.TextComponent();

                net.md_5.bungee.api.chat.TextComponent urlComponent = new TextComponent(url.getShortened() + " ");
                urlComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url.getFullPath()));
                urlComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Click to open URL"), new Text("\n§8" + url.getFullPath())));
                urlComponent.setBold(true);
                finalComp.addExtra(urlComponent);
            }

            i++;
        }

        return finalComp;
    }

}
