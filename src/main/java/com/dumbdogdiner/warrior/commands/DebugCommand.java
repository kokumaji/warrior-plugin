package com.dumbdogdiner.warrior.commands;

import com.dumbdogdiner.warrior.api.WarriorUser;
import com.dumbdogdiner.warrior.api.nms.Packet;
import com.dumbdogdiner.warrior.api.nms.PacketType;
import com.dumbdogdiner.warrior.api.nms.objects.SoundCategory;
import com.dumbdogdiner.warrior.api.nms.objects.SoundEffect;

import com.dumbdogdiner.warrior.managers.PlayerManager;
import org.bukkit.Location;
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

        Location loc = ((Player) sender).getLocation();

        Packet soundEffect = new Packet(PacketType.Server.NAMED_SOUND);

        soundEffect.setDeclared("a", SoundEffect.fromBukkit(Sound.ENTITY_FOX_HURT).toNMS());
        soundEffect.setDeclared("b", SoundCategory.toNMS(SoundCategory.AMBIENT));

        soundEffect.setInteger(0, (int) (loc.getX() * 8.0D));
        soundEffect.setInteger(1, (int) (loc.getY() * 8.0D));
        soundEffect.setInteger(2, (int) (loc.getZ() * 8.0D));

        soundEffect.setFloat(0, 1f);
        soundEffect.setFloat(1, 1f);

        WarriorUser user = PlayerManager.get(((Player) sender).getUniqueId());
        user.sendPacket(soundEffect.getPacket());

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
