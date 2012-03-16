package Wolfwood.InventorySort;


import java.util.logging.Logger;
import org.bukkit.configuration.Configuration;

/**
 * @version 2.0
 * @author Faye Bickerton AKA VeraLapsa
 */

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
    
    public static boolean Op_All = false;
    public static boolean Op_Top = false;
    public static boolean Op_Range = false;
    public static boolean Op_Chest = false;
    public static boolean Op_Wand = false;
    public static boolean Op_Reload = true;
    public static boolean Op_Stack = false;

    public static void load( Configuration config )
    {
        

        Stack_Default = config.getBoolean( "Stack.Default.Enabled", Stack_Default );
        config.addDefault("Stack.Default.Enabled", Stack_Default );

        Stack_Toggle = config.getBoolean( "Stack.Toggle.Enabled", Stack_Toggle );
        config.addDefault( "Stack.Toggle.Enabled", Stack_Toggle );

        Stack_Tools = config.getBoolean( "Stack.Tools.Enabled", Stack_Tools );
        config.addDefault( "Stack.Tools.Enabled", Stack_Tools );

        Stack_Armor = config.getBoolean( "Stack.Armor.Enabled", Stack_Armor );
        config.addDefault( "Stack.Armor.Enabled", Stack_Armor );

        Wand = config.getInt( "Chest.Wand", Wand );
        config.addDefault( "Chest.Wand", Wand );

        Debug = config.getBoolean( "Debug.Messages.Enabled", Debug );
        if (Debug) {
			log.info(B_PluginName + " Stack_Default: " + Stack_Default);
			log.info(B_PluginName + " Stack_Toggle: " + Stack_Toggle);
			log.info(B_PluginName + " Stack_Tools: " + Stack_Tools);
			log.info(B_PluginName + " Stack_Armor: " + Stack_Armor);
			log.info(B_PluginName + " Wand: " + Wand);
			log.info(B_PluginName + " Debug: " + Debug);
		}
        
        Config = config;

    }
    
}
