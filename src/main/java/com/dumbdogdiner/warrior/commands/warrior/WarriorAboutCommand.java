package com.dumbdogdiner.warrior.commands.warrior;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarriorAboutCommand implements SubCommand {

    private final String[] about = {
            " ",
            "&3&l{PluginName} &7developed by &b{Authors}",
            "&7Running version &b{Version}",
            " ",
            "&7SQL Status &8» {SqlStatus}",
            " "
    };

    @Override
    public String getAlias() {
        return "about";
    }

    @Override
    public String getSyntax() {
        return "/warrior about";
    }

    @Override
    public String getPermission() {
        return "warrior.command.admin";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length > 1) return false;
        Plugin self = Warrior.getInstance();
        String message = Warrior.getTranslator().applyPlaceholders(String.join("\n", about), new HashMap<>() {
            {
                put("Description", self.getDescription().getDescription());
                put("PluginName", self.getName());
                put("Authors", TranslationUtil.stringifyList(self.getDescription().getAuthors()).replace("and", "&7and&b"));
                put("Version", self.getDescription().getVersion());
                put("SqlStatus", Warrior.getConnection().getStatus());
            }
        });
        if(sender instanceof Player) sender.sendMessage(TranslationUtil.prettyMessage(message));
        else for (String s : message.split("\n")) Warrior.getPluginLogger().info(s);

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }

}
