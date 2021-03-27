package com.dumbdogdiner.warrior.listeners;

import com.dumbdogdiner.warrior.api.effects.WarriorEffects;
import com.dumbdogdiner.warrior.api.events.WarriorLevelUpEvent;
import com.dumbdogdiner.warrior.api.user.WarriorUser;
import com.dumbdogdiner.warrior.api.sessions.LobbySession;
import com.dumbdogdiner.warrior.managers.PlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();

        if (!PlayerManager.contains(uuid)) {
            WarriorUser user = PlayerManager.addUser(uuid);
            user.loadData();
            user.setSession(new LobbySession(user.getUserId()));

            /** This is what a custom resource pack prompt
             *  could look like :
             *
             *             ChatMessage msg = new ChatMessage(String.format(TranslationUtil.translateColor("\n \n \n&3&l(%s) &7For an enhanced visual experience, please use the included resource pack. Would you like to download it now?\n \n"), Symbols.FULLWIDTH_EXCLAMATION_MARK));
             *             msg.appendMessage(new ChatMessage(TranslationUtil.translateColor("&8» &a&lAccept &8- ")).setClickAction(Warrior.getInstance(), p, 30, player -> {
             *                 Packet resourcePack = new Packet(PacketType.Play.Server.RESOURCE_PACK_SEND);
             *                 resourcePack.setString(0, "http://localhost/files/resources.zip");
             *                 resourcePack.setString(1, "f2b2cadba2bcd8eb9097156fbf670a43dd86b58b");
             *
             *                 p.sendMessage(TranslationUtil.getPrefix() + TranslationUtil.translateColor("&7&oSending Resource Pack..."));
             *
             *                 new BukkitRunnable() {
             *
             *                     @Override
             *                     public void run() {
             *                         user.sendPacket(resourcePack);
             *                     }
             *
             *                 }.runTaskLater(Warrior.getInstance(), 40L);
             *             }).setTooltip("&7Click to Accept"));
             *
             *             msg.appendMessage(new ChatMessage(TranslationUtil.translateColor("&c&lDeny &8- ")).setTooltip("&7Click to Deny."));
             *             msg.appendMessage(new ChatMessage(TranslationUtil.translateColor("&7&lDon't ask again! &8«\n \n ")).setTooltip("&7Click to stop receiving this message."));
             *
             *             p.spigot().sendMessage(msg.getComponent());
             *
             *
             *             NMSUtil.injectPlayer(new PacketListener() {
             *
             *                 // SERVER -> CLIENT
             *                 @Override
             *                 public void onSend(ChannelHandlerContext ctx, ServerPacket packet, ChannelPromise promise) {
             *
             *                 }
             *
             *                 // CLIENT -> SERVER
             *                 @Override
             *                 public void onReceive(ChannelHandlerContext ctx, ClientPacket packet) {
             *                     if(packet.equals(PacketType.Play.Client.RESOURCE_PACK_STATUS)) {
             *                         String status = EnumUtil.asString(packet.get(NMSUtil.getNMSClass("PacketPlayInResourcePackStatus").getDeclaredClasses()[0]).get(0));
             *                         switch (status) {
             *                             case "ACCEPTED":
             *                                 String acceptMsg = TranslationUtil.getPrefix() + TranslationUtil.translateColor("&7Resource Pack downloaded successfully!");
             *                                 user.sendMessage(acceptMsg);
             *                                 break;
             *                             case "DECLINED":
             *                                 String declineMsg = TranslationUtil.getPrefix() + TranslationUtil.translateColor("&7Resource Pack download cancelled.");
             *                                 user.sendMessage(declineMsg);
             *                                 break;
             *                         }
             *                     }
             *                 }
             *
             *             }, user, Warrior.getInstance());
             */

        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        PlayerManager.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onLevelUp(WarriorLevelUpEvent e) {
        // we dont want to announce it for every single level up!
        if(e.getLevel()%5 == 0)
            e.getUser().spawnEffect(WarriorEffects.LEVELUP);
    }

}
