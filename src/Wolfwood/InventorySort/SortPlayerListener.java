/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wolfwood.InventorySort;

import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author Faye
 */
class SortPlayerListener {
    private final InventorySort plugin;

    public SortPlayerListener(InventorySort plugin) {
        this.plugin = plugin;
    }

    public void onPlyerLogon(PlayerLoginEvent event) {
        if(!plugin.stackOption.containsKey(event.getPlayer())){
            plugin.stackOption.put(event.getPlayer(), Boolean.FALSE);
        }
    }

}
