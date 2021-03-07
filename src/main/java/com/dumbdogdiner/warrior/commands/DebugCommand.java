package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.utils.TranslationUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        ArrayList<Color> colors = TranslationUtil.getColorGradient(Color.ORANGE, Color.MAGENTA, 8);
        String testString = String.join(" ", args);


        sender.sendMessage(TranslationUtil.applyColorGradient(testString, colors, 8));
        /*WarriorUser user = PlayerManager.get(((Player)sender).getUniqueId());

        SoundEffect testSound = new SoundEffect(InstrumentSound.BANJO.getSound(), 2f, Note.C2)
                                    .repeat(24, 1L)
                                    .descending();

        testSound.play(user.getBukkitPlayer());
        user.unlockSound(DeathSound.THUNDERSTORM);
        user.unlockSound(DeathSound.WITHER_DEATH);

        List<DeathSound> sounds = DeathSounds.getDeathSounds(user);
        System.out.println(Arrays.toString(sounds.stream().map(DeathSound::name).toArray(String[]::new)));
        user.setActiveSound(sounds.get(sounds.size() - 1));

        user.sendActionBar("Test Message!");*/
        return true;
    }

}
