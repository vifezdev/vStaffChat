package lol.vifez.vstaff.listener;

import lol.vifez.vstaff.vStaff;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    private final vStaff plugin;

    public PlayerChatListener(vStaff plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.isStaffChatEnabled() && player.hasPermission("vstaff.chat")) {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("vstaff.chat")) {
                    onlinePlayer.sendMessage(ChatColor.GRAY + "[Staff] " + player.getDisplayName() + ": " + event.getMessage());
                }
            }
            event.setCancelled(true);
        } else if (plugin.isStaffChatEnabled()) {
            player.sendMessage(ChatColor.RED + "You don't have permission to view staff chat!");
            event.setCancelled(true);
        }
    }
}