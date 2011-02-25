/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wolfwood.InventorySort;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

/**
 *
 * @author Faye
 */
class SortPlayerListener extends PlayerListener {
    private final InventorySort plugin;

    public SortPlayerListener(InventorySort plugin) {
    	super();
        this.plugin = plugin;
    }
    
    @Override
    public void onPlayerJoin(PlayerEvent event) {
        if(!plugin.stackOption.containsKey(event.getPlayer())){
            plugin.stackOption.put(event.getPlayer(), Boolean.FALSE);
        }
    }

}
