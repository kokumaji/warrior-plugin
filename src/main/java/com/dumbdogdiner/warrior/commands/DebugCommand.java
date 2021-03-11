package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.nms.objects.SoundEffect;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        /*SettingsGUI settingsGUI = GUIManager.get(SettingsGUI.class);
        settingsGUI.open((Player) sender);

        WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());
        user.unlockSound(DeathSound.DOG_BARK);
        user.unlockSound(DeathSound.CAT_MEOW);
        user.unlockSound(DeathSound.GHAST_DEATH);*/

        SoundEffect sound = SoundEffect.fromBukkit(Sound.BLOCK_DISPENSER_LAUNCH);
        System.out.println(sound.getKey().toString());

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
