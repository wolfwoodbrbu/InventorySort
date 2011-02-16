/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bukkit.Wolfwood.InventorySort;

import java.io.File;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Wolfwood
 */
public class InventorySort extends JavaPlugin {
	private final InventorySortListener playerListener = new InventorySortListener(this, getServer());
	public static final Logger log = Logger.getLogger("Minecraft");
		
	public InventorySort(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin,	ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
	}	
	
	public void onEnable(){
	    PluginManager pm = getServer().getPluginManager();
	    pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
	    
	    PluginDescriptionFile pdfFile = this.getDescription();
	    System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}

	public void onDisable(){

	}
}
