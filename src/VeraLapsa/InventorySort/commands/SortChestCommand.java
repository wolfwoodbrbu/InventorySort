package VeraLapsa.InventorySort.commands;


import VeraLapsa.InventorySort.CommandHandler;
import VeraLapsa.InventorySort.InventorySort;
import VeraLapsa.InventorySort.workers.ChestWorker;
import VeraLapsa.InventorySort.workers.Sort;
import VeraLapsa.InventorySort.workers.SortHelp;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
public class SortChestCommand extends CommandHandler
{
    public SortHelp help;
    public Sort sort;
    public ChestWorker CW;

    public SortChestCommand( InventorySort plugin )
    {
        super( plugin );
        help = new SortHelp( );
        sort = new Sort( plugin );
        CW = new ChestWorker( sort );
    }

    @Override
    public boolean perform( CommandSender sender, String[] args )
    {
        if ( !getPermissions( sender, "iSort.basic.chest.command" ) )
        {
            sender.sendMessage( ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sortchest" );
            return true;
        }
        if ( anonymousCheck( sender ) )
        {
            return true;
        }
        Player player = ( Player ) sender;
        Block target = player.getTargetBlock( null, 50 );
        if ( CW.sortChest( target, player ) )
        {
            sender.sendMessage( ChatColor.GRAY + "The chest has been sorted." );
            return true;
        } else
        {
            sender.sendMessage( "You are not looking at a Chest" );
            return true;
        }
    }
}
