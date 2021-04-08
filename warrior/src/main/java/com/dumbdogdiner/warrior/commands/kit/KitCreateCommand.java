package com.dumbdogdiner.warrior.commands.kit;

import com.dumbdogdiner.warrior.api.command.SubCommand;
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities;
import com.dumbdogdiner.warrior.api.kit.abilities.PaceMakerAbility;
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit;
import com.dumbdogdiner.warrior.managers.KitManager;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitCreateCommand implements SubCommand {

    @Override
    public String getAlias() {
        return "create";
    }

    @Override
    public String getSyntax() {
        return "/warrior create <Kit>";
    }

    @Override
    public String getPermission() {
        return "warrior.command.kit";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player p = (Player) sender;

        ItemStack[] inv = p.getInventory().getContents();
        String name = args[1];

        CustomKit kit = new CustomKit(name, 200, "warrior.kit." + name.toLowerCase(), Material.PAPER, SpecialAbilities.getAbility(PaceMakerAbility.class), new String[]{"Line 1", "Line 2"}, inv);
        System.out.println("Kit Created");
        kit.save();

        KitManager.addKit(kit);

        return true;
    }

    @Override
    public List<String> getArguments(CommandSender sender, String[] arguments) {
        return new ArrayList<>();
    }
}
