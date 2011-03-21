package Wolfwood.InventorySort;


import Wolfwood.InventorySort.workers.ChestWorker;
import Wolfwood.InventorySort.workers.Sort;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

/**
 *
 * @author Faye
 */
class SortBlockListener extends BlockListener
{
    private final InventorySort plugin;
    Sort sort;
    ChestWorker CW;

    public SortBlockListener( InventorySort plugin )
    {
        this.plugin = plugin;
        sort = new Sort( plugin );
        CW = new ChestWorker( sort );
    }

    @Override
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
                player.sendMessage( "That is not a Chest" );
            }
        }
    }

    private boolean hasPermissions( Player player )
    {
        String node = "iSort.basic.chest.wand";
        try
        {
            if ( InventorySort.wd != null )
            {
                return InventorySort.wd.getWorldPermissions( player ).has( player, node );
            } else
            {
                return InventorySort.Permissions.has( player, node );
            }
        } catch ( NullPointerException e )
        {
            InventorySort.log.warning( "Permissions are not working for [InventorySort] defaulting to Op" );
            return player.isOp();
        }
    }
}