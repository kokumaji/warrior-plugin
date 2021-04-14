package com.dumbdogdiner.warrior.commands.misc;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.command.AsyncCommandLegacy;
import com.dumbdogdiner.warrior.api.command.ExitStatus;
import com.dumbdogdiner.warrior.user.User;
import com.dumbdogdiner.warrior.gui.settings.SettingsGUI;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SettingsCommand extends AsyncCommandLegacy implements TabCompleter {

    public SettingsCommand() {
        super("settings", Warrior.getInstance());
        setPermission("warrior.command.settings");
    }

    @Override
    public ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args) {
        User user = PlayerManager.get(((Player) sender).getUniqueId());
        SettingsGUI langGUI = GUIManager.get(SettingsGUI.class);
        new BukkitRunnable() {

            @Override
            public void run() {
                langGUI.open(user.getBukkitPlayer());
            }
        }.runTask(Warrior.getInstance());

        return ExitStatus.EXECUTE_SUCCESS;
    }

    @Override
    public void onPermissionError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public void onError(CommandSender sender, String label, String[] args) {

    }

    @Override
    public void onSyntaxError(CommandSender sender, String label, String[] args) {

    }
}
