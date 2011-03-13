package Wolfwood.InventorySort;


import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.Configuration;

/**
 *
 * @author Faye
 */
class SortPlayerListener extends PlayerListener
{
    private final InventorySort plugin;

    public SortPlayerListener( InventorySort plugin )
    {
        super();
        this.plugin = plugin;
    }

    @Override
    public void onPlayerJoin( PlayerEvent event )
    {
        if ( !plugin.stackOption.containsKey( event.getPlayer() ) )
        {
            plugin.stackOption.put( event.getPlayer(), getUserSetting( event.getPlayer().getName() ) );
        }
    }

    private boolean getUserSetting( String name )
    {
        Configuration config = Constants.Config;
        config.load();
        boolean setting = config.getBoolean( "Users." + name + ".Stack", false );
        config.setProperty( "Users." + name + ".Stack", setting );
        if ( config.save() )
        {
            if ( Constants.Debug )
            {
                Constants.log.info( Constants.B_PluginName + " Saved " + name + "'s setting to config" );
            }
        } else
        {
            Constants.log.warning( Constants.B_PluginName + " was not able to save to the config." );
        }

        return setting;
    }
}
