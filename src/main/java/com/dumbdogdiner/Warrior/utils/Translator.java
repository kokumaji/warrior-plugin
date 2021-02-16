package com.dumbdogdiner.Warrior.utils;

import com.dumbdogdiner.Warrior.Warrior;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Translator {

    private static final FileConfiguration C = Warrior.getInstance().getConfig();
    private Plugin owner;

    public Translator(Plugin plugin, FileConfiguration conf) throws IOException {
        this.owner = plugin;
        File langFolder = new File(owner.getDataFolder(), "translation");

        if(!langFolder.exists()) {
            langFolder.mkdirs();
            if(owner.getResource("translation/messages.en_US.yml") == null)
                throw new IOException("Could not copy file 'messages.en_US.yml': File doess not exist.");

            owner.saveResource("translation/messages.en_US.yml", true);
        } else {
            File langFile = new File(owner.getDataFolder(), "translation/messages.en_US.yml");

            if(!langFile.exists())
                owner.saveResource("translation/messages.en_US.yml", true);
        }
    }

    public YamlConfiguration getLanguageFile() {
        File f = new File(owner.getDataFolder(), "translation/messages.en_US.yml");
        return YamlConfiguration.loadConfiguration(f);
    }

    public String translate(String stringPath, boolean addPluginPrefix) {
        YamlConfiguration config = getLanguageFile();
        if(config.getString(stringPath) != null) {
            String msg = TranslationUtil.translateColor(config.getString(stringPath));
            if(addPluginPrefix) {
                msg = TranslationUtil.getPrefix() + " " + msg;
            }
            return msg;
        }

        return "§cError in language file! String " + stringPath + " does not exist.";
    }

    public String applyPlaceholders(String msg, Map<String, String> values) {
        StrSubstitutor sub = new StrSubstitutor(values, "{", "}");
        String result = sub.replace(msg);

        return TranslationUtil.translateColor(result);
    }

    public String translate(String stringPath, Map<String, String> values) {
        YamlConfiguration config = getLanguageFile();
        if(config.get(stringPath) != null) {
            StrSubstitutor sub = new StrSubstitutor(values, "{", "}");
            String result = sub.replace(config.getString(stringPath));

            return TranslationUtil.translateColor(result);
        }

        return "§cError in language file! String " + stringPath + " does not exist.";
    }

}
