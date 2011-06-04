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
        sender.sendMessage(Constants.B_PluginName + " Dynamic help:");
        if ( hasPermissions( player, "iSort.basic.all", Constants.Op_All ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort all " + ChatColor.WHITE + "- sorts all slots" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all" );
        }
        if ( hasPermissions( player, "iSort.basic.top", Constants.Op_Top ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort top " + ChatColor.WHITE + "- sorts slots 9 - 35" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top" );
        }
        if ( hasPermissions( player, "iSort.basic.range", Constants.Op_Range ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort 9 35 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( ChatColor.GREEN + "/sort 35 9 " + ChatColor.WHITE + "- sorts slots 9 - 35" );
            sender.sendMessage( ChatColor.GREEN + "/sort 10 15 " + ChatColor.WHITE + "- sorts slots 10 - 15" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>" );
        }
        if ( hasPermissions( player, "iSort.basic.chest.command", Constants.Op_Chest ) )
        {
            sender.sendMessage( ChatColor.GREEN + "/sortchest(/srtc) " + ChatColor.WHITE + "- sorts the chest your looking at" );
        } else
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sortchest(/srtc)" );
        }
        if ( hasPermissions( player, "iSort.adv.stack", Constants.Op_Stack ) && Constants.Stack_Toggle )
        {
            sender.sendMessage( ChatColor.GREEN + "/sort stack " + ChatColor.WHITE + "- toggles whether sort stacks the inventory." );
        }
        if ( hasPermissions( player, "iSort.basic.chest.wand", Constants.Op_Wand))
        {
            sender.sendMessage( ChatColor.GREEN + "You are allowed to sort chests with a(n) " + ItemType.toName(Constants.Wand) );
        }
        return true;
    }

    private boolean hasPermissions( Player player, String node , boolean OpOnly)
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
            	if (OpOnly) {
            		return player.isOp();
				}
            	return true;
            }
        } catch ( NullPointerException e )
        {
            InventorySort.log.warning( "Permissions are not working for [InventorySort] defaulting to Op" );
            return player.isOp();
        }
    } 
}
