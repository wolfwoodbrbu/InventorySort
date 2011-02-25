/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Wolfwood.InventorySort.commands;

import Wolfwood.InventorySort.CommandHandler;
import Wolfwood.InventorySort.InventorySort;
import Wolfwood.InventorySort.workers.Sort;
import Wolfwood.InventorySort.workers.SortHelp;
import Wolfwood.InventorySort.workers.TargetBlock;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Faye
 */
public class SortChestCommand extends CommandHandler {

    public SortHelp help;
    public Sort sort;

    public SortChestCommand(InventorySort plugin) {
        super(plugin);
        help = new SortHelp(plugin);
        sort = new Sort(plugin);
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        if (!getPermissions(sender, "iSort.basic.chest")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sortchest");
            return true;
        }
        if (anonymousCheck(sender)) return true;
        Player player = (Player) sender;
        TargetBlock hitBlox = new TargetBlock(player);
        Block target = hitBlox.getTargetBlock();
        int intX = target.getX();
        int intY = target.getY();
        int intZ = target.getZ();
        Block block = player.getWorld().getBlockAt(intX, intY, intZ);
        BlockState state = block.getState();
        if (state instanceof Chest) {
            Chest chest1 = (Chest) state;
            Chest chest2 = isDoubleChest(player.getWorld(), block, 1);
            if (chest2 != null) {
                if (sortDblChst(chest1, chest2, player)) {
            		sender.sendMessage(ChatColor.GRAY + "The chest has been sorted.");
            		return true;
				}
            } else {
                chest2 = isDoubleChest(player.getWorld(), block, -1);
                if (chest2 != null) {
                	if (sortDblChst(chest2, chest1, player)) {
                		sender.sendMessage(ChatColor.GRAY + "The chest has been sorted.");
                		return true;
					} 
                }
            }
            ItemStack[] stack1 = sort.sortItemStack(chest1.getInventory().getContents(),player);
            chest1.getInventory().setContents(stack1);
            chest1.update();
            sender.sendMessage(ChatColor.GRAY + "The chest has been sorted.");
            return true;
        } else {
            sender.sendMessage("You are not looking at a Chest");
            return true;
        }
    }

    private boolean sortDblChst(Chest chest1, Chest chest2, Player player) {
        ItemStack[] s1 = chest1.getInventory().getContents();
        ItemStack[] s2 = chest2.getInventory().getContents();
        ItemStack[] both = sort.sortItemStack(concat(s1, s2),player);
        s1 = Arrays.copyOfRange(both, 0, s1.length);
        s2 = Arrays.copyOfRange(both, s1.length, s1.length + s2.length);
        chest1.getInventory().setContents(s1);
        chest2.getInventory().setContents(s2);
        chest1.update();
        chest2.update();
        return true;
    }

    private ItemStack[] concat(ItemStack[] A, ItemStack[] B) {
        ItemStack[] C = new ItemStack[A.length + B.length];
        System.arraycopy(A, 0, C, 0, A.length);
        System.arraycopy(B, 0, C, A.length, B.length);

        return C;
    }

    private Chest isDoubleChest(World world, Block chest1, int offset) {
        Block chest2 = world.getBlockAt(chest1.getX() + offset, chest1.getY(), chest1.getZ());
        BlockState state = chest2.getState();
        if (state instanceof Chest) {
            return (Chest) state;
        }
        chest2 = world.getBlockAt(chest1.getX(), chest1.getY(), chest1.getZ() + offset);
        state = chest2.getState();
        if (state instanceof Chest) {
            return (Chest) state;
        }
        return null;
    }
}
