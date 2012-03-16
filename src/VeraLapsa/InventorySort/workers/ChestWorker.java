package VeraLapsa.InventorySort.workers;


import VeraLapsa.InventorySort.Constants;
import java.util.Arrays;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
public class ChestWorker
{
    public Sort sort;
    static boolean Debug;

    public ChestWorker( Sort sort )
    {
        this.sort = sort;
        Debug = Constants.Debug;
    }

    public boolean sortChest( Block target, Player player )
    {
        BlockState state = target.getState();
        if ( state instanceof Chest )
        {
            Chest chest1 = ( Chest ) state;
            Chest chest2 = isDoubleChest( player.getWorld(), target, 1 );
            if ( chest2 != null )
            {
                return sortDblChst( chest1, chest2, player );
            } else
            {
                chest2 = isDoubleChest( player.getWorld(), target, -1 );
                if ( chest2 != null )
                {
                    return sortDblChst( chest2, chest1, player );
                }
            }
            if ( Debug )
            {
                Constants.log.info( Constants.B_PluginName + " Sorting a Chest for " + player.getName() + "." );
            }
            ItemStack[] stack1 = sort.sortItemStack( chest1.getInventory().getContents(), player );
            chest1.getInventory().setContents( stack1 );
            chest1.update();
            return true;
        } else
        {
            return false;
        }
    }

    private boolean sortDblChst( Chest chest1, Chest chest2, Player player )
    {
        if ( Debug )
        {
            Constants.log.info( Constants.B_PluginName + " Sorting a Doublechest for " + player.getName() + "." );
        }
        ItemStack[] s1 = chest1.getInventory().getContents();
        ItemStack[] s2 = chest2.getInventory().getContents();
        ItemStack[] both = sort.sortItemStack( concat( s1, s2 ), player );
        s1 = Arrays.copyOfRange( both, 0, s1.length );
        s2 = Arrays.copyOfRange( both, s1.length, s1.length + s2.length );
        chest1.getInventory().setContents( s1 );
        chest2.getInventory().setContents( s2 );
        chest1.update();
        chest2.update();
        return true;
    }

    private ItemStack[] concat( ItemStack[] A, ItemStack[] B )
    {
        ItemStack[] C = new ItemStack[ A.length + B.length ];
        System.arraycopy( A, 0, C, 0, A.length );
        System.arraycopy( B, 0, C, A.length, B.length );

        return C;
    }

    private Chest isDoubleChest( World world, Block chest1, int offset )
    {
        Block chest2 = world.getBlockAt( chest1.getX() + offset, chest1.getY(), chest1.getZ() );
        BlockState state = chest2.getState();
        if ( state instanceof Chest )
        {
            if ( Debug )
            {
                Constants.log.info( Constants.B_PluginName + " Is a Doublechest with the Offset of " + offset + " in the X direction." );
            }
            return ( Chest ) state;
        }
        chest2 = world.getBlockAt( chest1.getX(), chest1.getY(), chest1.getZ() + offset );
        state = chest2.getState();
        if ( state instanceof Chest )
        {
            if ( Debug )
            {
                Constants.log.info( Constants.B_PluginName + " Is a Doublechest with the Offset of " + offset + " in the Y direction." );
            }
            return ( Chest ) state;
        }
        return null;
    }
}
