package VeraLapsa.InventorySort;


import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */

public abstract class CommandHandler
{
    protected final InventorySort plugin;

    public CommandHandler( InventorySort plugin )
    {
        this.plugin = plugin;
    }

    public abstract boolean perform( CommandSender sender, String[] args );

    protected static boolean anonymousCheck( CommandSender sender )
    {
        if ( !(sender instanceof Player) )
        {
            sender.sendMessage( "Cannot execute that command, I don't know who you are!" );
            return true;
        } else
        {
            return false;
        }
    }

    protected static Player getPlayer( CommandSender sender, String[] args, int index )
    {
        if ( args.length > index )
        {
            List<Player> players = sender.getServer().matchPlayer( args[index] );

            if ( players.isEmpty() )
            {
                sender.sendMessage( "I don't know who '" + args[index] + "' is!" );
                return null;
            } else
            {
                return players.get( 0 );
            }
        } else
        {
            if ( anonymousCheck( sender ) )
            {
                return null;
            } else
            {
                return ( Player ) sender;
            }
        }
    }

    protected static boolean getPermissions( CommandSender sender, String node )
    {
        Player player = ( Player ) sender;
        try
        {
            return player.hasPermission(node);
        } catch ( NullPointerException e )
        {
            InventorySort.log.warning( "Permissions are not working for [InventorySort] defaulting to Op" );
            return sender.isOp();
        }
    }
}
