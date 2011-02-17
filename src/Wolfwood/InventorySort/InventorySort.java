package Wolfwood.InventorySort;

import com.nijiko.Messaging;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @version 0.1
 * @author Wolfwood
 */
// this plugin need's the permission's jar and the bukkit snapshot jar
public class InventorySort extends JavaPlugin {

    public static PermissionHandler Permissions = null;
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static final Logger log = Logger.getLogger("Minecraft");

    public InventorySort(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder,
            File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onEnable() {
        setupPermissions();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(pdfFile.getName() + " v " + pdfFile.getVersion() + " is disabled!");
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player) != null;
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }

    public void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");


        if (InventorySort.Permissions == null) {
            if (test != null) {
                InventorySort.Permissions = ((Permissions) test).getHandler();
            } else {
                log.info(Messaging.bracketize("Inventory Sort") + " Permission system not enabled. Disabling plugin.");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    // basically if it's the console sending the command it denies it.
    private boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();

        if (commandName.equals("sort")) {
            if (anonymousCheck(sender)) {
                return false;
            }
            return sortInv(sender, trimmedArgs);
        }
        return false;
    }

    private boolean sortInv(CommandSender sender, String[] split) {
        Player player = (Player) sender;
        int end = 36;
        int start = 0;
        if (split.length == 0) {
            return dispSortHelp(sender);
        } else if (split.length == 1) {
            String what = split[0];
            if (what.equalsIgnoreCase("all")) {
                if (!InventorySort.Permissions.has(player, "iSort.basic.all")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all");
                    return true;
                }
                end = 36;
                start = 0;
            } else if (what.equalsIgnoreCase("top")) {
                if (!InventorySort.Permissions.has(player, "iSort.basic.top")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top");
                    return true;
                }
                end = 36;
                start = 9;
            } else {
                return dispSortHelp(sender);
            }
        } else if (split.length == 2 && InventorySort.Permissions.has(player, "iSort.basic.range")) {
            int j = 35;
            int k = 0;
            try {
                j = Integer.valueOf(split[0]);
                k = Integer.valueOf(split[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Those were not numbers!");
                return dispSortHelp(sender);
            }
            if (j <= 35 && j >= 0 && k <= 35 && k >= 0) {
                if (k > j) {
                    end = k + 1;
                    start = j;
                } else if (j > k) {
                    end = j + 1;
                    start = k;
                } else if (j == k) {
                    sender.sendMessage("You just want to slot " + j + " sorted? Ok, done.");
                    return true;
                } else {
                    return dispSortHelp(sender); //should never get here
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Out of range error.");
                return dispSortHelp(sender);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>");
            return true;
        }
        int n = end;
        ItemStack[] x = player.getInventory().getContents();
        boolean doMore = true;
        while (doMore) {
            n--;
            doMore = false;  // assume this is our last pass over the array
            for (int i = start; i < n; i++) {
                if (x[i].getTypeId() > x[i + 1].getTypeId()) {
                    // exchange elements
                    ItemStack temp = x[i];
                    x[i] = x[i + 1];
                    x[i + 1] = temp;
                    doMore = true;  // after an exchange, must look again
                }
            }
        }
        player.getInventory().setContents(x);
        sender.sendMessage(ChatColor.GRAY + "Slots " + start + "-" + (end - 1) + " have been sorted!");
        return true;
    }

    public boolean dispSortHelp(CommandSender sender) {
        Player player = (Player) sender;
        if (InventorySort.Permissions.has(player, "iSort.basic.all")) {
            sender.sendMessage("Example: " + ChatColor.GREEN + "/sort all " + ChatColor.WHITE + "- sorts all slots");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all");
        }
        if (InventorySort.Permissions.has(player, "iSort.basic.top")) {
            sender.sendMessage("Example: " + ChatColor.GREEN + "/sort top " + ChatColor.WHITE + "- sorts slots 9 - 35");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top");
        }
        if (InventorySort.Permissions.has(player, "iSort.basic.range")) {
            sender.sendMessage("Example: " + ChatColor.GREEN + "/sort 9 35 " + ChatColor.WHITE + "- sorts slots 9 - 35");
            sender.sendMessage("Example: " + ChatColor.GREEN + "/sort 35 9 " + ChatColor.WHITE + "- sorts slots 9 - 35");
            sender.sendMessage("Example: " + ChatColor.GREEN + "/sort 10 15 " + ChatColor.WHITE + "- sorts slots 10 - 15");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>");
        }
        return true;
    }
}
