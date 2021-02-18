package com.dumbdogdiner.Warrior.api.translation;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Translator {

    private Plugin owner;

    @Getter @Setter
    private YamlConfiguration languageFile;

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

        File f = new File(plugin.getDataFolder(), "translation/messages.en_US.yml");
        this.languageFile = YamlConfiguration.loadConfiguration(f);

    }

    public String translate(String stringPath, boolean addPluginPrefix) {
        if(languageFile.getString(stringPath) != null) {
            String msg = TranslationUtil.translateColor(languageFile.getString(stringPath));
            if(addPluginPrefix) {
                msg = TranslationUtil.getPrefix() + msg;
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
        if(languageFile.get(stringPath) != null) {
            StrSubstitutor sub = new StrSubstitutor(values, "{", "}");
            String result = sub.replace(languageFile.getString(stringPath));

            return TranslationUtil.translateColor(result);
        }

        return "§cError in language file! String " + stringPath + " does not exist.";
    }

}
