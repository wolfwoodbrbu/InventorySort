/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Wolfwood.InventorySort.commands;

import Wolfwood.InventorySort.CommandHandler;
import Wolfwood.InventorySort.InventorySort;
import Wolfwood.InventorySort.workers.Sort;
import Wolfwood.InventorySort.workers.SortHelp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Faye
 */
public class SortCommand extends CommandHandler {

    public SortHelp help;
    public Sort sort;

    public SortCommand(InventorySort plugin) {
        super(plugin);
        help = new SortHelp(plugin);
        sort = new Sort(plugin);
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        int end = 36;
        int start = 0;
        if (args.length == 0) {
            return help.dispSortHelp(sender);
        } else if (args.length == 1) {
            String what = args[0];
            if (what.equalsIgnoreCase("all")) {
                if (!getPermissions(sender, "iSort.basic.all")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort all");
                    return true;
                }
            } else if (what.equalsIgnoreCase("top")) {
                if (!getPermissions(sender, "iSort.basic.top")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort top");
                    return true;
                }
                end = 36;
                start = 9;
            } else if (what.equalsIgnoreCase("stack")) {
                if (!getPermissions(sender, "iSort.adv.stack")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort stack");
                    return true;
                }
                return toggleStack(sender);
            } else {
                return help.dispSortHelp(sender);
            }
        } else if (args.length == 2) {
            if (!getPermissions(sender, "iSort.basic.range")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort <0-35> <0-35>");
                return true;
            }
            int j = 35;
            int k = 0;
            try {
                j = Integer.valueOf(args[0]);
                k = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage("Those were not numbers!");
                return help.dispSortHelp(sender);
            }
            if (j <= 35 && j >= 0 && k <= 35 && k >= 0) {
                if (k > j) {
                    end = k + 1;
                    start = j;
                } else if (j > k) {
                    end = j + 1;
                    start = k;
                } else if (j == k) {
                    sender.sendMessage("You just want to slot " + j + " sorted? Ok, done.");
                    return true;
                } else {
                    return help.dispSortHelp(sender); //should never get here
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Out of range error.");
                return help.dispSortHelp(sender);
            }
        } else {
            return help.dispSortHelp(sender);
        }
        Player player = (Player) sender;
        ItemStack[] plyrStack = player.getInventory().getContents();
        plyrStack = sort.sortItemStack(plyrStack, start, end, player);
        player.getInventory().setContents(plyrStack);
        sender.sendMessage(ChatColor.GRAY + "Slots " + start + "-" + (end - 1) + " have been sorted!");
        return true;
    }

    private boolean toggleStack(CommandSender sender) {
        Player player = (Player) sender;


        if (!anonymousCheck(sender)) {
            if (plugin.stackOption.get(player)) {
                plugin.stackOption.put(player, Boolean.FALSE);


            } else {
                plugin.stackOption.put(player, Boolean.TRUE);


            }
            return true;


        } else {
            return false;

        }
    }
}
