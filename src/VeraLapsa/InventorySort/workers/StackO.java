package VeraLapsa.InventorySort.workers;

import VeraLapsa.InventorySort.Constants;
import VeraLapsa.InventorySort.InventorySort;
import org.bukkit.entity.Player;
import org.bukkit.configuration.Configuration;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
public class StackO {

    private final InventorySort plugin;

    public StackO(InventorySort plugin) {
        this.plugin = plugin;
    }

    public void addPlayerStackOption(Player player) {
        if (!plugin.stackOption.containsKey(player.getName())) {
            plugin.stackOption.put(player.getName(), getUserSetting(player.getName()));
            if (Constants.Debug) {
                Constants.log.info(player.getName() + " was added to hashmap.");
            }
        }
    }

    public boolean getUserSetting(String name) {
        Configuration config = Constants.Config;
        boolean setting = config.getBoolean("Users." + name + ".Stack", Constants.Stack_Default);
        config.addDefault("Users." + name + ".Stack", setting);
        if (Constants.Debug) {
            Constants.log.info(Constants.B_PluginName + " Saved " + name + "'s setting to config");
        }
        return setting;
    }

    public boolean getSO(Player player) {
        if (Constants.Debug) {
            Constants.log.info(player.getName() + "'s key is " + plugin.stackOption.get(player.getName()) + ".");
        }
        if (plugin.stackOption.get(player.getName())) {
            return true;
        } else {
            return false;
        }

    }

    public void putSO(Player player, boolean option) {

        if (option) {
            plugin.stackOption.put(player.getName(), Boolean.TRUE);
        } else {
            plugin.stackOption.put(player.getName(), Boolean.FALSE);
        }
        if (Constants.Debug) {
            Constants.log.info("" + option);
            getSO(player);
        }
    }
}
