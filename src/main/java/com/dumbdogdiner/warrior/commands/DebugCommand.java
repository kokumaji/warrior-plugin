package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.kit.effects.DeathSound;
import com.dumbdogdiner.warrior.api.kit.effects.WarriorTitle;

import com.dumbdogdiner.warrior.gui.SettingsGUI;
import com.dumbdogdiner.warrior.managers.GUIManager;
import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        SettingsGUI settingsGUI = GUIManager.get(SettingsGUI.class);
        settingsGUI.open((Player) sender);

        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        user.setActiveTitle(WarriorTitle.LVL_30);
        user.setActiveSound(DeathSound.THUNDERSTORM);

        /*
        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        SoundEffect testSound = new SoundEffect(InstrumentSound.BANJO.getSound(), 2f, Note.C2)
                                    .repeat(24, 1L)
                                    .descending();

        testSound.play(user.getBukkitPlayer());
        user.unlockSound(DeathSound.THUNDERSTORM);
        user.unlockSound(DeathSound.WITHER_DEATH);

        List<DeathSound> sounds = DeathSounds.getDeathSounds(user);
        System.out.println(Arrays.toString(sounds.stream().map(DeathSound::name).toArray(String[]::new)));
        user.setActiveSound(sounds.get(sounds.size() - 1));

        user.sendActionBar("Test Message!");
        */
        return true;
    }

}
