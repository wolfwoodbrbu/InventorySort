package VeraLapsa.InventorySort.commands;

import VeraLapsa.InventorySort.CommandHandler;
import VeraLapsa.InventorySort.Constants;
import VeraLapsa.InventorySort.InventorySort;
import VeraLapsa.InventorySort.workers.Sort;
import VeraLapsa.InventorySort.workers.SortHelp;
import VeraLapsa.InventorySort.workers.StackO;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.Configuration;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */
public class SortCommand extends CommandHandler {

    public SortHelp help;
    public Sort sort;
    private StackO stack;

    public SortCommand(InventorySort plugin) {
        super(plugin);
        help = new SortHelp();
        sort = new Sort(plugin);
        stack = new StackO(plugin);
    }

    @Override
    public boolean perform(CommandSender sender, String[] args) {
        int end = 36;
        int start = 0;
        int numOfArgs = args.length;
        switch (numOfArgs) {
            case 1:
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
                } else if (what.equalsIgnoreCase("stack") && Constants.Stack_Toggle) {
                    if (!getPermissions(sender, "iSort.adv.stack")) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort stack");
                        return true;
                    }
                    return toggleStack(sender);
                } else if (what.equalsIgnoreCase("reload")) {
                    if (!getPermissions(sender, "iSort.adv.reload")) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to run " + ChatColor.GREEN + "/sort reload");
                        return true;
                    }
                    plugin.loadConfig();
                    sender.sendMessage(Constants.B_PluginName + " Config reloaded!");
                    InventorySort.log.info(Constants.B_PluginName + " Config reloaded!");
                    return true;
                } else {
                    return help.dispSortHelp(sender);
                }
                break;
            case 2:
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
                break;
            case 0:
            default:
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
            stack.addPlayerStackOption(player);
            if (stack.getSO(player)) {
                stack.putSO(player, false);
                setUserSetting(player.getName(), false);
                sender.sendMessage(ChatColor.RED + "Stacking is turned off while sorting");
            } else {
                stack.putSO(player, true);
                setUserSetting(player.getName(), true);
                sender.sendMessage(ChatColor.GREEN + "Stacking is turned on while sorting");
            }
            return true;
        } else {
            return false;

        }
    }

    private void setUserSetting(String name, boolean setting) {
        Configuration config = Constants.Config;
        config.addDefault("Users." + name + ".Stack", setting);
        if (Constants.Debug) {
            Constants.log.info(Constants.B_PluginName + " Saved " + name + "'s setting to config");
        }
    }
}
