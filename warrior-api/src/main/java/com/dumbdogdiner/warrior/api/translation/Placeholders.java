package com.dumbdogdiner.warrior.api.translation;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.sessions.ArenaSession;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholders {

    private static final Pattern conditionalRegex = Pattern.compile("\\{(?<condition>[a-zA-Z0-9<>=]+)\\|\"(?<condtrue>[a-zA-Z0-9&]+)\"\\|\"(?<condfalse>[a-zA-Z0-9&]+)\"\\}");

    public static String applyPlaceholders(String msg, Map<String, String> values) {
        if(values != null) {
            StrSubstitutor sub = new StrSubstitutor(values, "{", "}");
            msg = sub.replace(msg);
        }

        return TranslationUtil.translateColor(msg);
    }

    public static String applyPlaceholders(String msg, LanguageCode lang) {
        YamlConfiguration conf = lang == LanguageCode.DE_DE ? WarriorAPI.getService().getTranslator().getVariablesGerman()
                : WarriorAPI.getService().getTranslator().getVariablesEnglish();
        return applyPlaceholders(msg, conf);
    }

    public static String applyPlaceholders(String msg, YamlConfiguration file) {
        if(file != null) {
            Map<String,String> values = new HashMap<String,String>();
            for (Map.Entry<String, Object> entry : file.getValues(true).entrySet()) {
                if(entry.getValue() instanceof String){
                    values.put(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            StrSubstitutor sub = new StrSubstitutor(values, "{", "}");
            msg = sub.replace(msg);
        }

        return TranslationUtil.translateColor(msg);
    }

    public static String parseConditional(String msg) {
        return parseConditional(msg, null);
    }

    public static String parseConditional(String msg, Player player) {
        Matcher m = conditionalRegex.matcher(msg);
        if(!m.find()) return msg;

        final StringBuffer builder = new StringBuffer();

        do {
            boolean parsedBoolean;
            if(player == null) {
                parsedBoolean = Boolean.parseBoolean(m.group("condition"));
            } else parsedBoolean = parsePlayerCondition(m.group("condition"), player);

            String string = parsedBoolean ? m.group("condtrue") : m.group("condfalse");

            m.appendReplacement(builder, string);
        } while (m.find());

        return TranslationUtil.translateColor(m.appendTail(builder).toString());
    }

    private static boolean parsePlayerCondition(String condition, Player player) {
        WarriorUser user = WarriorAPI.getService().getPlayerManager().get(player.getUniqueId());
        if(user == null) return false;
        // might be able to replace this with sexy switch statement.
        switch (condition.toLowerCase()) {
            case "notifications":
                return user.getSettings().receiveNotifications();
            case "online":
                return player.isOnline();
            case "flying":
                return player.isFlying();
            case "ingame":
                return user.getSession() instanceof ArenaSession;
        }
        return false;
    }
}
