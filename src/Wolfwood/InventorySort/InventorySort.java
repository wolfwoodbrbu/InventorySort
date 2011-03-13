package Wolfwood.InventorySort;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import Wolfwood.InventorySort.commands.SortChestCommand;
import Wolfwood.InventorySort.commands.SortCommand;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.util.config.Configuration;

/**
 * @version 1.6
 * @author Wolfwood AKA Faye AKA needs to get a female call now that I'm transistioning
 */
// this plugin need's the permission's jar and the bukkit snapshot jar
public class InventorySort extends JavaPlugin
{
    public static PermissionHandler Permissions;
    private final Map<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private Map<String, CommandHandler> commands = new HashMap<String, CommandHandler>();
    public static final Logger log = Constants.log;
    public HashMap<Player, Boolean> stackOption = new HashMap<Player, Boolean>();
    private SortPlayerListener playerListener = new SortPlayerListener( this );
    private SortBlockListener blockListener = new SortBlockListener( this );
    // private Listener Listener = new Listener();
    public static WorldsHolder wd;

    public void onEnable()
    {

        PluginDescriptionFile pdfFile = this.getDescription();

        Constants.B_PluginName = "[" + pdfFile.getName() + "]";

        try
        {
            if ( !getDataFolder().exists() )
            {
                getDataFolder().mkdir();
            }
        } catch ( Exception e )
        {
            System.out.println( Constants.B_PluginName + " Could not create directory!" );
            System.out.println( Constants.B_PluginName + " You must manually make the InventorySort/ directory!" );
        }

        // Make sure we can read / write
        getDataFolder().setWritable( true );
        getDataFolder().setExecutable( true );

        // Directory
        Constants.Plugin_Directory = getDataFolder().getPath();

        setupDefaultFile( "config.yml" );

        // Configuration
        loadConfig();

        setupPermissions();

        this.getServer().getPluginManager().registerEvent( Event.Type.PLAYER_JOIN, playerListener, Priority.Low, this );
        this.getServer().getPluginManager().registerEvent( Event.Type.BLOCK_DAMAGED, blockListener, Priority.High, this );
//        registerEvents();

        commands.put( "sort", new SortCommand( this ) );
        commands.put( "sortchest", new SortChestCommand( this ) );

        log.info( Constants.B_PluginName + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public void onDisable()
    {
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( Constants.B_PluginName + " version " + pdfFile.getVersion() + " is disabled!" );
    }

    public boolean isDebugging( final Player player )
    {
        if ( debugees.containsKey( player ) )
        {
            return debugees.get( player ) != null;
        } else
        {
            return false;
        }
    }

    public void setDebugging( final Player player, final boolean value )
    {
        debugees.put( player, value );
    }

    @Override
    public boolean onCommand( CommandSender sender, Command command, String commandLabel, String[] args )
    {
        CommandHandler handler = commands.get( command.getName().toLowerCase() );

        if ( handler != null )
        {
            return handler.perform( sender, args );
        } else
        {
            return false;
        }
    }

//    private class Listener extends ServerListener {
//
//        public Listener() {
//        }
//
//        @Override
//        public void onPluginEnabled(PluginEvent event) {
//            if (event.getPlugin().getDescription().getName().equals("Permissions")) {
//                InventorySort.Permissions = ((Permissions) event.getPlugin()).Security;
//                log.info(Constants.B_PluginName + " Attached plugin to Permissions. Enjoy~");
//            }
//        }
//    }
//    private void registerEvents() {
//        this.getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, Listener, Priority.Monitor, this);
//    }
    private void setupPermissions()
    {


        Plugin p = this.getServer().getPluginManager().getPlugin( "GroupManager" );
        if ( p != null )
        {
            if ( !this.getServer().getPluginManager().isPluginEnabled( p ) )
            {
                this.getServer().getPluginManager().enablePlugin( p );
            }
            GroupManager gm = ( GroupManager ) p;
            wd = gm.getWorldsHolder();
        } else
        {
            wd = null;
            p = this.getServer().getPluginManager().getPlugin( "Permissions" );

            if ( p != null )
            {
                Permissions = (( Permissions ) p).getHandler();
            } else
            {
                log.info( "No permissions system not detected, disabling [InventorySort]." );
                this.getPluginLoader().disablePlugin( this );
            }
        }
    }

    public void loadConfig()
    {
        try
        {
            Constants.load( new Configuration( new File( getDataFolder(), "config.yml" ) ) );
        } catch ( Exception e )
        {
            log.warning( Constants.B_PluginName + " Failed to retrieve configuration from directory. Using defaults." );
        }
    }

    private void setupDefaultFile( String name )
    {
        File actual = new File( getDataFolder(), name );
        if ( !actual.exists() )
        {

            InputStream input = this.getClass().getResourceAsStream( "/Default/" + name );
            if ( input != null )
            {
                FileOutputStream output = null;

                try
                {
                    output = new FileOutputStream( actual );
                    byte[] buf = new byte[ 8192 ];
                    int length = 0;

                    while ( (length = input.read( buf )) > 0 )
                    {
                        output.write( buf, 0, length );
                    }

                    System.out.println( Constants.B_PluginName + " Default setup file written: " + name );
                } catch ( Exception e )
                {
                    e.printStackTrace();
                } finally
                {
                    try
                    {
                        if ( input != null )
                        {
                            input.close();
                        }
                    } catch ( Exception e )
                    {
                    }

                    try
                    {
                        if ( output != null )
                        {
                            output.close();
                        }
                    } catch ( Exception e )
                    {
                    }
                }
            }
        }
    }
}
