package Wolfwood.InventorySort;


import java.util.logging.Logger;
import org.bukkit.util.config.Configuration;

public class Constants
{
    public static Configuration Config;
    // Plugin Directory
    // I don't know why I didn't think of this before.
    public static String Plugin_Directory;
    public static String B_PluginName;
    public static final Logger log = Logger.getLogger( "Minecraft" );
    public static boolean Stack_Default = false;
    public static boolean Stack_Toggle = true;
    public static boolean Stack_Tools = false;
    public static boolean Stack_Armor = false;
    public static boolean Debug = false;
    public static int Wand = 280; // stick

    public static void load( Configuration config )
    {
        Config = config;
        config.load();

        Stack_Default = config.getBoolean( "Stack.Default.Enabled", Stack_Default );

        Stack_Toggle = config.getBoolean( "Stack.Toggle.Enabled", Stack_Toggle );

        Stack_Tools = config.getBoolean( "Stack.Tools.Enabled", Stack_Tools );

        Stack_Armor = config.getBoolean( "Stack.Armor.Enabled", Stack_Armor );

        Wand = config.getInt( "Chest.Wand", Wand );

        Debug = config.getBoolean( "Debug.Messages.Enabled", Debug );

    }
}
