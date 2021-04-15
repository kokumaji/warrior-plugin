package com.dumbdogdiner.warrior.commands.kit

import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.managers.KitManager.addKit
import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit
import com.dumbdogdiner.warrior.api.kit.SpecialAbilities
import com.dumbdogdiner.warrior.api.kit.abilities.PaceMakerAbility
import com.dumbdogdiner.warrior.managers.KitManager
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.ArrayList

class KitCreateCommand : SubCommand {
    override fun getAlias(): String {
        return "create"
    }

    override fun getSyntax(): String {
        return "/warrior create <Kit>"
    }

    override fun getPermission(): String {
        return "warrior.command.kit"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        val p = sender as Player
        val inv = p.inventory.contents
        val name = args[1]
        val kit = CustomKit(
            name, 200, "warrior.kit." + name.toLowerCase(), Material.PAPER, SpecialAbilities.getAbility(
                PaceMakerAbility::class.java
            ), arrayOf("Line 1", "Line 2"), *inv
        )
        println("Kit Created")
        kit.save()
        Warrior.kitManager.addKit(kit)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return ArrayList()
    }
}