package Wolfwood.InventorySort;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class CommandHandler {
    protected final InventorySort plugin;

    public CommandHandler(InventorySort plugin) {
        this.plugin = plugin;
    }

    public abstract boolean perform(CommandSender sender, String[] args);
    
    protected static boolean anonymousCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot execute that command, I don't know who you are!");
            return true;
        } else {
            return false;
        }
    }

    protected static Player getPlayer(CommandSender sender, String[] args, int index) {
        if (args.length > index) {
            List<Player> players = sender.getServer().matchPlayer(args[index]);

            if (players.isEmpty()) {
                sender.sendMessage("I don't know who '" + args[index] + "' is!");
                return null;
            } else {
                return players.get(0);
            }
        } else {
            if (anonymousCheck(sender)) {
                return null;
            } else {
                return (Player)sender;
            }
        }
    }

    protected static boolean getPermissions(CommandSender sender, String node){
    	try{
    		return InventorySort.Permissions.has((Player)sender, node);
    	} catch (NullPointerException e) {
    		InventorySort.log.warning("Permissions not working for InventorySort defaulting to Op");
			return sender.isOp();
		}
    }
}
