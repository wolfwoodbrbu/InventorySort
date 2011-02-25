/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Wolfwood.InventorySort.workers;

import Wolfwood.InventorySort.InventorySort;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Faye
 */
public class Sort {
    private final InventorySort plugin;

    public Sort(InventorySort plugin) {
        this.plugin = plugin;
    }


    public ItemStack[] sortItemStack(ItemStack[] stack, Player player) {
        return sortItemStack(stack, 0, stack.length, player);
    }

    public ItemStack[] sortItemStack(ItemStack[] stack, int start, int end, Player player) {
        if(plugin.stackOption.get(player))
        {
            stack = stackItems(stack, start, end);
        }
        boolean doMore = true;
        int n = end;
        while (doMore) {
            n--;
            doMore = false;  // assume this is our last pass over the array
            for (int i = start; i < n; i++) {
                if (stack[i].getTypeId() > stack[i + 1].getTypeId()) {
                    // exchange elements
                    ItemStack temp = stack[i];
                    stack[i] = stack[i + 1];
                    stack[i + 1] = temp;
                    doMore = true;  // after an exchange, must look again
                }
            }
        }
        return stack;
    }

    private ItemStack[] stackItems(ItemStack[] items, int start, int end) {
        for (int i = start; i < end; i++) {
            ItemStack item = items[i];

            // Avoid infinite stacks and stacks with durability
            if (item == null || item.getAmount() <= 0
                    || ItemType.shouldNotStack(item.getTypeId())) {
                continue;
            }

            // Ignore buckets
            if (item.getTypeId() >= 325 && item.getTypeId() <= 327) {
                continue;
            }

            if (item.getAmount() < 64) {
                int needed = 64 - item.getAmount(); // Number of needed items until 64

                // Find another stack of the same type
                for (int j = i + 1; j < end; j++) {
                    ItemStack item2 = items[j];

                    // Avoid infinite stacks and stacks with durability
                    if (item2 == null || item2.getAmount() <= 0
                            || ItemType.shouldNotStack(item.getTypeId())) {
                        continue;
                    }

                    // Same type?
                    // Blocks store their color in the damage value
                    if (item2.getTypeId() == item.getTypeId() && (!ItemType.usesDamageValue(item.getTypeId()) || item.getDurability() == item2.getDurability())) {
                        // This stack won't fit in the parent stack
                        if (item2.getAmount() > needed) {
                            item.setAmount(64);
                            item2.setAmount(item2.getAmount() - needed);
                            break;
                            // This stack will
                        } else {
                            item.setAmount(item.getAmount() + item2.getAmount());
                            needed = 64 - item.getAmount();
                            items[j].setTypeId(0);
                        }
                    }
                }
            }
        }
        return items;
    }
}
