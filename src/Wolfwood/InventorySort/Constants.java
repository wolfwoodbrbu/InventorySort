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
    
    public static boolean Op_All = false;
    public static boolean Op_Top = false;
    public static boolean Op_Range = false;
    public static boolean Op_Chest = false;
    public static boolean Op_Wand = false;
    public static boolean Op_Reload = true;
    public static boolean Op_Stack = false;

    public static void load( Configuration config )
    {
        Config = config;
        config.load();

        Stack_Default = config.getBoolean( "Stack.Default.Enabled", Stack_Default );
        config.setProperty( "Stack.Default.Enabled", Stack_Default );

        Stack_Toggle = config.getBoolean( "Stack.Toggle.Enabled", Stack_Toggle );
        config.setProperty( "Stack.Toggle.Enabled", Stack_Toggle );

        Stack_Tools = config.getBoolean( "Stack.Tools.Enabled", Stack_Tools );
        config.setProperty( "Stack.Tools.Enabled", Stack_Tools );

        Stack_Armor = config.getBoolean( "Stack.Armor.Enabled", Stack_Armor );
        config.setProperty( "Stack.Armor.Enabled", Stack_Armor );

        Wand = config.getInt( "Chest.Wand", Wand );
        config.setProperty( "Chest.Wand", Wand );

        Debug = config.getBoolean( "Debug.Messages.Enabled", Debug );
        if (Debug) {
			log.info(B_PluginName + " Stack_Default: " + Stack_Default);
			log.info(B_PluginName + " Stack_Toggle: " + Stack_Toggle);
			log.info(B_PluginName + " Stack_Tools: " + Stack_Tools);
			log.info(B_PluginName + " Stack_Armor: " + Stack_Armor);
			log.info(B_PluginName + " Wand: " + Wand);
			log.info(B_PluginName + " Debug: " + Debug);
		}
        
        config.save();

    }
    
    public static void loadInternalPermissions() {
    	Configuration config = Config;
    	config.load();
    	
    	Op_All = config.getBoolean("NoPermissions.OPOnly.C_All", Op_All);
    	config.setProperty("NoPermissions.OPOnly.C_All", Op_All);
    	
    	Op_Top = config.getBoolean("NoPermissions.OPOnly.C_Top", Op_Top);
    	config.setProperty("NoPermissions.OPOnly.C_Top", Op_Top);
    	
    	Op_Range = config.getBoolean("NoPermissions.OPOnly.C_Range", Op_Range);
    	config.setProperty("NoPermissions.OPOnly.C_Range", Op_Range);
    	
    	Op_Chest = config.getBoolean("NoPermissions.OPOnly.C_Chest", Op_Chest);
    	config.setProperty("NoPermissions.OPOnly.C_Chest", Op_Chest);
    	
    	Op_Reload = config.getBoolean("NoPermissions.OPOnly.C_Reload", Op_Reload);
    	config.setProperty("NoPermissions.OPOnly.C_Reload", Op_Reload);
    	
    	Op_Wand = config.getBoolean("NoPermissions.OPOnly.Wand", Op_Wand);
    	config.setProperty("NoPermissions.OPOnly.Wand", Op_Wand);
    	
    	Op_Stack = config.getBoolean("NoPermissions.OPOnly.C_Stack", Op_Stack);
    	config.setProperty("NoPermissions.OPOnly.C_Stack", Op_Stack);
    	
    	config.save();
		
	}
}
