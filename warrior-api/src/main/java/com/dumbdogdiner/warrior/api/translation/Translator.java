package com.dumbdogdiner.warrior.api.translation;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Translator {

    private Plugin owner;

    @Getter @Setter
    private YamlConfiguration englishFile;

    @Getter @Setter
    private YamlConfiguration germanFile;

    @Getter @Setter
    private YamlConfiguration variablesEnglish;

    @Getter @Setter
    private YamlConfiguration variablesGerman;

    public Translator(Plugin plugin, FileConfiguration conf) throws IOException {
        this.owner = plugin;
        File langFolder = new File(owner.getDataFolder(), "translation");

        if(!langFolder.exists()) {
            langFolder.mkdirs();
            if(owner.getResource("translation/messages.en_US.yml") == null)
                throw new IOException("Could not copy file 'messages.en_US.yml': File doess not exist.");

            owner.saveResource("translation/messages.en_US.yml", true);
            owner.saveResource("translation/messages.de_DE.yml", true);
            owner.saveResource("translation/variables.de_DE.yml", true);
            owner.saveResource("translation/variables.en_US.yml", true);
        } else {
            File langFile = new File(owner.getDataFolder(), "translation/messages.en_US.yml");
            File langFileGerman = new File(owner.getDataFolder(), "translation/messages.de_DE.yml");

            File varFile = new File(owner.getDataFolder(), "translation/variables.en_US.yml");
            File varFileGerman = new File(owner.getDataFolder(), "translation/variables.de_DE.yml");

            if(!langFile.exists())
                owner.saveResource("translation/messages.en_US.yml", true);

            if(!langFileGerman.exists())
                owner.saveResource("translation/messages.de_DE.yml", true);

            if(!varFile.exists())
                owner.saveResource("translation/variables.en_US.yml", true);

            if(!varFileGerman.exists())
                owner.saveResource("translation/variables.de_DE.yml", true);
        }

        File f = new File(plugin.getDataFolder(), "translation/messages.en_US.yml");
        this.englishFile = YamlConfiguration.loadConfiguration(f);

        File f2 = new File(plugin.getDataFolder(), "translation/messages.de_DE.yml");
        this.germanFile = YamlConfiguration.loadConfiguration(f2);

        File f3 = new File(plugin.getDataFolder(), "translation/variables.de_DE.yml");
        this.variablesGerman = YamlConfiguration.loadConfiguration(f3);

        File f4 = new File(plugin.getDataFolder(), "translation/variables.en_US.yml");
        this.variablesEnglish = YamlConfiguration.loadConfiguration(f4);

    }

    public String translate(String stringPath) {
        return translate(stringPath, null, null);
    }

    public String translate(String stringPath, WarriorUser user) {
        return translate(stringPath, null, user);
    }

    public String translate(String stringPath, Map<String, String> values, WarriorUser user) {
        LanguageCode lang = LanguageCode.EN_US;
        if(user != null) {
            lang = user.getSettings().getLanguage();
        }

        YamlConfiguration languageFile = lang == LanguageCode.DE_DE ? germanFile : englishFile;

        if(languageFile.get(stringPath) != null) {
            String result = Symbols.parseSymbols(Objects.requireNonNull(languageFile.getString(stringPath)));

            if(values != null) {
                result = Placeholders.applyPlaceholders(result, values);
            }

            if(Warrior.usePlaceholderAPI() && user != null) {
                result = PlaceholderAPI.setPlaceholders(user.getBukkitPlayer(), result);
            }

            if(user != null) {
                YamlConfiguration varFile = lang == LanguageCode.DE_DE ? variablesGerman : variablesEnglish;
                result = Placeholders.applyPlaceholders(Placeholders.parseConditional(result, user.getBukkitPlayer()), varFile);
            } else {
                result = Placeholders.applyPlaceholders(Placeholders.parseConditional(result), variablesEnglish);
            }

            return TranslationUtil.translateColor(result);
        }

        return "§cError in language file! String " + stringPath + " does not exist.";
    }

    public String translate(String stringPath, Map<String, String> values) {
        return translate(stringPath, values, null);
    }

    public String[] getStrings(String s, LanguageCode lang) {
        return lang == LanguageCode.DE_DE ? germanFile.getStringList(s).toArray(String[]::new)
                : englishFile.getStringList(s).toArray(String[]::new);
    }
}
