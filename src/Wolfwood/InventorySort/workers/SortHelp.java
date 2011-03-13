package Wolfwood.InventorySort.workers;


import Wolfwood.InventorySort.Constants;
import Wolfwood.InventorySort.InventorySort;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Faye
 */
public class SortHelp
{
    private final InventorySort plugin;

    public SortHelp( InventorySort plugin )
    {
        this.plugin = plugin;
    }

    public boolean dispSortHelp( CommandSender sender )
    {
        Player player = ( Player ) sender;
        if ( InventorySort.Permissions.has( player, "iSort.basic.all" ) )
        {
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort all " + ChatColor.WHITE + "- sorts all slots" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all" );
        }
        if ( InventorySort.Permissions.has( player, "iSort.basic.top" ) )
        {
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort top " + ChatColor.WHITE + "- sorts slots 9 - 35" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top" );
        }
        if ( InventorySort.Permissions.has( player, "iSort.basic.range" ) )
        {
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort 9 35 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort 35 9 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort 10 15 " + ChatColor.WHITE + "- sorts slots 10 - 15" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>" );
        }
        if ( InventorySort.Permissions.has( player, "iSort.basic.chest" ) )
        {
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sortchest " + ChatColor.WHITE + "- sorts the chest your looking at" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sortchest" );
        }
        if ( InventorySort.Permissions.has( player, "iSort.adv.stack" ) && Constants.Stack_Toggle )
        {
            sender.sendMessage( "Example: " + ChatColor.GREEN + "/sort stack " + ChatColor.WHITE + "- toggles whether sort stacks the inventory." );
        }
        return true;
    }
}
