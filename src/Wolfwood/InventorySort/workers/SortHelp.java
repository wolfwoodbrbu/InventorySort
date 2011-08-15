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

    public SortHelp()
    {

    }

    public boolean dispSortHelp( CommandSender sender )
    {
        Player player = ( Player ) sender;
        sender.sendMessage(Constants.B_PluginName + " Dynamic help:");
        if ( hasPermissions( player, "iSort.basic.all" ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort all " + ChatColor.WHITE + "- sorts all slots" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all" );
        }
        if ( hasPermissions( player, "iSort.basic.top" ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort top " + ChatColor.WHITE + "- sorts slots 9 - 35" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top" );
        }
        if ( hasPermissions( player, "iSort.basic.range" ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort 9 35 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( ChatColor.GREEN + "/sort 35 9 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( ChatColor.GREEN + "/sort 10 15 " + ChatColor.WHITE + "- sorts slots 10 - 15" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>" );
        }
        if ( hasPermissions( player, "iSort.basic.chest.command" ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sortchest(/srtc) " + ChatColor.WHITE + "- sorts the chest your looking at" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sortchest(/srtc)" );
        }
        if ( hasPermissions( player, "iSort.adv.stack" ) && Constants.Stack_Toggle )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort stack " + ChatColor.WHITE + "- toggles whether sort stacks the inventory." );
        }
        if ( hasPermissions( player, "iSort.basic.chest.wand"))
        {
            sender.sendMessage( ChatColor.GREEN + "You are allowed to sort chests with a(n) " + ItemType.toName(Constants.Wand) );
        }
        return true;
    }

    private boolean hasPermissions( Player player, String node)
    {
    	try
        {
            if ( InventorySort.wd != null )
            {
                return InventorySort.wd.getWorldPermissions( player ).has( player, node );
            } else if(InventorySort.Permissions != null)
            {
                return InventorySort.Permissions.has( player, node );
            } else
            {
            	return player.hasPermission( node );
            }
        } catch ( NullPointerException e )
        {
            InventorySort.log.warning( "Permissions are not working for [InventorySort] defaulting to Op" );
            return player.isOp();
        }
    } 
}
