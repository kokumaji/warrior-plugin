package com.dumbdogdiner.warrior.commands.misc

import com.dumbdogdiner.warrior.api.command.SubCommand
import com.dumbdogdiner.stickyapi.common.util.NumberUtil
import com.dumbdogdiner.warrior.api.translation.Symbols
import com.dumbdogdiner.warrior.user.UserCache
import com.dumbdogdiner.warrior.Warrior
import com.dumbdogdiner.warrior.api.reflection.FieldUtil
import com.dumbdogdiner.warrior.user.User
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.HashMap

class SymbolsSearchCommand : SubCommand {

    override fun getAlias(): String {
        return "search"
    }

    override fun getSyntax(): String {
        return "/symbol search <identifier>"
    }

    override fun getPermission(): String {
        return "warrior.command.symbols"
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<String>): Boolean {
        if (args.size > 2) return false
        val input = args[1].replace("%#", "")
        val index: Int
        val fieldName: String
        val unicode: String
        if (NumberUtil.isNumeric(input)) {
            val num = input.toInt()
            unicode = FieldUtil.getStringConstant(num, Symbols::class.java)
            fieldName = FieldUtil.getNameDeclared(num, Symbols::class.java)
            index = num
        } else {
            unicode = FieldUtil.getStringConstant(input, Symbols::class.java)
            fieldName = input.toUpperCase()
            index = FieldUtil.indexOfDeclared(fieldName, Symbols::class.java)
        }
        if (index == -1) return false
        val msg: String
        msg = if (sender is Player) {
            val user: User = UserCache.get(sender.uniqueId)
            Warrior.getTranslator().translate("command-messages.symbols-command", object : HashMap<K?, V?>() {
                init {
                    put("arg1", args[1])
                    put("identifier", fieldName)
                    put("index", index.toString())
                    put("unicode", unicode)
                }
            }, user)
        } else {
            Warrior.getTranslator().translate("command-messages.symbols-command", object : HashMap<K?, V?>() {
                init {
                    put("arg1", args[1])
                    put("identifier", fieldName)
                    put("index", index.toString())
                    put("unicode", unicode)
                }
            })
        }
        sender.sendMessage(msg)
        return true
    }

    override fun getArguments(sender: CommandSender, arguments: Array<String>): List<String> {
        return null
    }
}