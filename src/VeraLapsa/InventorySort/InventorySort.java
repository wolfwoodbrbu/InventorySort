package VeraLapsa.InventorySort;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import VeraLapsa.InventorySort.commands.SortChestCommand;
import VeraLapsa.InventorySort.commands.SortCommand;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.bukkit.plugin.PluginManager;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
// this plugin need's the permission's jar and the bukkit snapshot jar
public class InventorySort extends JavaPlugin {

    private final Map<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private Map<String, CommandHandler> commands = new HashMap<String, CommandHandler>();
    public static final Logger log = Constants.log;
    public HashMap<String, Boolean> stackOption = new HashMap<String, Boolean>();
    private SortBlockListener blockListener = new SortBlockListener(this);

    public void onEnable() {

        PluginDescriptionFile pdfFile = this.getDescription();

        Constants.B_PluginName = "[" + pdfFile.getName() + "]";

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
        } catch (Exception e) {
            System.out.println(Constants.B_PluginName + " Could not create directory!");
            System.out.println(Constants.B_PluginName + " You must manually make the InventorySort/ directory!");
        }

        // Make sure we can read / write
        getDataFolder().setWritable(true);
        getDataFolder().setExecutable(true);
        
        // Configuration
        loadConfig();

        log.info(Constants.B_PluginName + "Using Bukkit's SuperPerms for permissions. See Plugin's page for nodes.");

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this.blockListener, this);

        commands.put("sort", new SortCommand(this));
        commands.put("sortchest", new SortChestCommand(this));

        log.info(Constants.B_PluginName + " version " + pdfFile.getVersion() + " is enabled!");


    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info(Constants.B_PluginName + " version " + pdfFile.getVersion() + " is disabled!");
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

    public void loadConfig() {
        try {
            getConfig().options().copyDefaults(true);
            Constants.load(getConfig());
        } catch (Exception e) {
            log.warning(Constants.B_PluginName + " Failed to retrieve configuration from directory. Using defaults.");
        }
    }

    private void setupDefaultFile(String name) {
        File actual = new File(getDataFolder(), name);
        if (!actual.exists()) {

            InputStream input = this.getClass().getResourceAsStream("/Default/" + name);
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(actual);
                    byte[] buf = new byte[8192];
                    int length = 0;

                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                    System.out.println(Constants.B_PluginName + " Default setup file written: " + name);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (Exception e) {
                    }

                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
