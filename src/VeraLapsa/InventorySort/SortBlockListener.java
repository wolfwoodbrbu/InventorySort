package VeraLapsa.InventorySort;


import VeraLapsa.InventorySort.workers.ChestWorker;
import VeraLapsa.InventorySort.workers.Sort;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
class SortBlockListener implements Listener 
{
    private final InventorySort plugin;
    Sort sort;
    ChestWorker CW;

    public SortBlockListener( InventorySort plugin )
    {
        this.plugin = plugin;
        sort = new Sort( this.plugin );
        CW = new ChestWorker( sort );
    }

    @EventHandler(priority= EventPriority.HIGHEST)
    public void onBlockDamage( BlockDamageEvent event )
    {
        Player player = event.getPlayer();

        if ( event.getPlayer().getItemInHand().getTypeId() == Constants.Wand && hasPermissions( player ) )
        {
            if ( CW.sortChest( event.getBlock(), player ) )
            {
                player.sendMessage( ChatColor.GRAY + "The chest has been sorted." );
            } else
            {
                //player.sendMessage( "That is not a Chest" );
            }
        }
    }

    private boolean hasPermissions( Player player )
    {
        String node = "iSort.basic.chest.wand";
        try
        {   
            if (Constants.Op_Wand) {
                return player.isOp();
            } else {
                return player.hasPermission(node); 
            }
        } catch ( NullPointerException e )
        {
            InventorySort.log.warning( "Permissions are not working for [InventorySort] defaulting to Op" );
            return player.isOp();
        }
    }
}
