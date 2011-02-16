/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bukkit.Wolfwood.InventorySort;

import org.bukkit.Server;
import org.bukkit.event.player.PlayerChatEvent;

/**
 *
 * @author Wolfwood
 */
class InventorySortListener {
    private final InventorySort plugin;
    Server server = null;


    public InventorySortListener(InventorySort instance, Server server)
    {
	this.plugin = instance;
	this.server = server;
    }

    public void onPlayerCommand(PlayerChatEvent event){

    }

}
