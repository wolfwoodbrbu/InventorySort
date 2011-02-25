package Wolfwood.InventorySort;

import Wolfwood.InventorySort.commands.*;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @version 1.5
 * @author Wolfwood
 */
// this plugin need's the permission's jar and the bukkit snapshot jar
public class InventorySort extends JavaPlugin {

    public static PermissionHandler Permissions;
    private final Map<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private Map<String, CommandHandler> commands = new HashMap<String, CommandHandler>();
    public static final Logger log = Logger.getLogger("Minecraft");
    public HashMap<Player, Boolean> stackOption = new HashMap<Player, Boolean>();
    private SortPlayerListener playerListener = new SortPlayerListener(this);

    public void onEnable() {
        setupPermissions();

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");

        commands.put("sort", new SortCommand(this));
        commands.put("sortchest", new SortChestCommand(this));
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is disabled!");
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

        if (this.Permissions == null) {
            if (test != null) {
                this.Permissions = ((Permissions) test).getHandler();
            } else {
                log.warning("Permissions not found, InventorySort will not be enabled!");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        CommandHandler handler = commands.get(command.getName().toLowerCase());

        if (handler != null) {
            return handler.perform(sender, args);
        } else {
            return false;
        }
    }
}
