package com.dumbdogdiner.warrior.commands.misc;

import com.dumbdogdiner.stickyapi.common.util.NumberUtil;
import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.reflection.FieldUtil;
import com.dumbdogdiner.warrior.api.translation.Placeholders;
import com.dumbdogdiner.warrior.api.translation.Symbols;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public class SymbolsSearchCommand implements SubCommand {
    @Override
    public String getAlias() {
        return "search";
    }

    @Override
    public String getSyntax() {
        return "/symbol search <identifier>";
    }

    @Override
    public String getPermission() {
        return "warrior.command.symbols";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length > 2) return false;
        String input = args[1].replace("%#", "");
        int index;
        String fieldName;
        String unicode;

        if(NumberUtil.isNumeric(input)) {
            Integer num = Integer.parseInt(input);
            unicode = FieldUtil.getStringConstant(num, Symbols.class);
            fieldName = FieldUtil.getNameDeclared(num, Symbols.class);
            index = num;
        } else {
            unicode = FieldUtil.getStringConstant(input, Symbols.class);
            fieldName = input.toUpperCase();
            index = FieldUtil.indexOfDeclared(fieldName, Symbols.class);
        }

        if(index == -1) return false;

        String msg;
        if(sender instanceof Player) {
            WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
            msg = Warrior.getTranslator().translate("command-messages.symbols-command", new HashMap<>() {
                {
                    put("arg1", args[1]);
                    put("identifier", fieldName);
                    put("index", String.valueOf(index));
                    put("unicode", unicode);
                }
            }, user);
        } else {
            msg = Warrior.getTranslator().translate("command-messages.symbols-command", new HashMap<>() {
                {
                    put("arg1", args[1]);
                    put("identifier", fieldName);
                    put("index", String.valueOf(index));
                    put("unicode", unicode);
                }
            });
        }

        sender.sendMessage(msg);

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return null;
    }
}
