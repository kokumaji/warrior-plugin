package com.dumbdogdiner.Warrior.commands.warrior;

import com.dumbdogdiner.Warrior.Warrior;
import com.dumbdogdiner.Warrior.api.command.SubCommand;
import com.dumbdogdiner.Warrior.utils.TranslationUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarriorAboutCommand implements SubCommand {

    private final String[] about = {
            " ",
            "&3&l" + Warrior.getInstance().getName() + " &7developed by &b" + String.join(", ", Warrior.getInstance().getDescription().getAuthors()),
            "&7Running version &b" + Warrior.getInstance().getDescription().getVersion(),
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

        if(sender instanceof Player) sender.sendMessage(TranslationUtil.prettyMessage(about));
        else for (String s : about) {
            if(s.equals(" ")) continue;
            sender.sendMessage(TranslationUtil.translateColor(s));
        }

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }

}
