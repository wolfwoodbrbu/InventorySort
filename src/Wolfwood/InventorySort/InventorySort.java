package Wolfwood.InventorySort;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import Wolfwood.InventorySort.commands.SortChestCommand;
import Wolfwood.InventorySort.commands.SortCommand;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

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
    private Listener Listener = new Listener();


    public void onEnable() {

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");
        setupPermissions();
        
        this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Low, this);
        registerEvents();

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


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        CommandHandler handler = commands.get(command.getName().toLowerCase());

        if (handler != null) {
            return handler.perform(sender, args);
        } else {
            return false;
        }
    }
    private class Listener extends ServerListener {

        public Listener() {
        }

        @Override
        public void onPluginEnabled(PluginEvent event) {
            if(event.getPlugin().getDescription().getName().equals("Permissions")) {
                InventorySort.Permissions = ((Permissions)event.getPlugin()).Security;
                log.info("InventorySort Attached plugin to Permissions. Enjoy~");
            }
        }
    }
    private void registerEvents() {
        this.getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, Listener, Priority.Monitor, this);
    }
    
    private void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

        if (this.Permissions == null) {
            if (test != null) {
                this.getServer().getPluginManager().enablePlugin(test); // This line.
                this.Permissions = ((Permissions)test).getHandler();
            } else {
                log.info("Permission system not detected, defaulting to OP");
            }
        }
    }



}
