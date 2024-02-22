package lol.vifez.vstaff;

import lol.vifez.vstaff.listener.PlayerChatListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class vStaff extends JavaPlugin {
    private boolean staffChatEnabled = false;
    private String toggleCommand;
    private String staffChatPermission;

    @Override
    public void onEnable() {
        getLogger().info("vStaff has loaded!");
        saveDefaultConfig();
        loadConfig();

        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("vStaff has disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(toggleCommand)) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }
            Player player = (Player) sender;
            if (!player.hasPermission(staffChatPermission)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use staff chat!");
                return true;
            }

            staffChatEnabled = !staffChatEnabled;
            if (staffChatEnabled) {
                player.sendMessage(ChatColor.GREEN + "Staff chat enabled!");
            } else {
                player.sendMessage(ChatColor.RED + "Staff chat disabled!");
            }
            return true;
        }
        return false;
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        toggleCommand = config.getString("toggle_command", "sc");
        staffChatPermission = config.getString("staff_chat_permission", "vstaff.chat");
    }

    public boolean isStaffChatEnabled() {
        return staffChatEnabled;
    }
}
