package Wolfwood.InventorySort;
/**
 * This is from the plugin MySignEdit
 */
import java.util.ArrayList;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TargetBlock
{

    private Location loc;
    private double viewHeight;
    private int maxDistance;
    private int blockToIgnore[];
    private double checkDistance;
    private double curDistance;
    private double xRotation;
    private double yRotation;
    private Vector targetPos;
    private Vector targetPosDouble;
    private Vector prevPos;
    private Vector offset;

    public TargetBlock(Player player)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(player.getLocation(), 300, 1.6499999999999999D, 0.20000000000000001D, null);
    }

    public TargetBlock(Location loc)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(loc, 300, 0.0D, 0.20000000000000001D, null);
    }

    public TargetBlock(Player player, int maxDistance, double checkDistance)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(player.getLocation(), maxDistance, 1.6499999999999999D, checkDistance, null);
    }

    public TargetBlock(Location loc, int maxDistance, double checkDistance)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(loc, maxDistance, 0.0D, checkDistance, null);
    }

    public TargetBlock(Player player, int maxDistance, double checkDistance, int blocksToIgnore[])
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(player.getLocation(), maxDistance, 1.6499999999999999D, checkDistance, blocksToIgnore);
    }

    public TargetBlock(Location loc, int maxDistance, double checkDistance, int blocksToIgnore[])
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        setValues(loc, maxDistance, 0.0D, checkDistance, blocksToIgnore);
    }

    public TargetBlock(Player player, int maxDistance, double checkDistance, ArrayList blocksToIgnore)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        int bti[] = convertStringArraytoIntArray(blocksToIgnore);
        setValues(player.getLocation(), maxDistance, 1.6499999999999999D, checkDistance, bti);
    }

    public TargetBlock(Location loc, int maxDistance, double checkDistance, ArrayList blocksToIgnore)
    {
        targetPos = new Vector();
        targetPosDouble = new Vector();
        prevPos = new Vector();
        offset = new Vector();
        int bti[] = convertStringArraytoIntArray(blocksToIgnore);
        setValues(loc, maxDistance, 0.0D, checkDistance, bti);
    }

    private void setValues(Location loc, int maxDistance, double viewHeight, double checkDistance, int blocksToIgnore[])
    {
        this.loc = loc;
        this.maxDistance = maxDistance;
        this.viewHeight = viewHeight;
        this.checkDistance = checkDistance;
        blockToIgnore = blocksToIgnore;
        curDistance = 0.0D;
        xRotation = (loc.getYaw() + 90F) % 360F;
        yRotation = loc.getPitch() * -1F;
        double h = checkDistance * Math.cos(Math.toRadians(yRotation));
        offset.setY(checkDistance * Math.sin(Math.toRadians(yRotation)));
        offset.setX(h * Math.cos(Math.toRadians(xRotation)));
        offset.setZ(h * Math.sin(Math.toRadians(xRotation)));
        targetPosDouble = new Vector(loc.getX(), loc.getY() + viewHeight, loc.getZ());
        targetPos = new Vector(targetPosDouble.getBlockX(), targetPosDouble.getBlockY(), targetPosDouble.getBlockZ());
        prevPos = targetPos.clone();
    }

    public void reset()
    {
        targetPosDouble = new Vector(loc.getX(), loc.getY() + viewHeight, loc.getZ());
        targetPos = new Vector(targetPosDouble.getBlockX(), targetPosDouble.getBlockY(), targetPosDouble.getBlockZ());
        prevPos = targetPos.clone();
        curDistance = 0.0D;
    }

    public double getDistanceToBlock()
    {
        Vector blockUnderPlayer = new Vector((int)Math.floor(loc.getX() + 0.5D), (int)Math.floor(loc.getY() - 0.5D), (int)Math.floor(loc.getZ() + 0.5D));
        Block blk = getTargetBlock();
        double x = blk.getX() - blockUnderPlayer.getBlockX();
        double y = blk.getY() - blockUnderPlayer.getBlockY();
        double z = blk.getZ() - blockUnderPlayer.getBlockZ();
        return Math.sqrt(Math.pow(x, 2D) + Math.pow(y, 2D) + Math.pow(z, 2D));
    }

    public int getDistanceToBlockRounded()
    {
        Vector blockUnderPlayer = new Vector((int)Math.floor(loc.getX() + 0.5D), (int)Math.floor(loc.getY() - 0.5D), (int)Math.floor(loc.getZ() + 0.5D));
        Block blk = getTargetBlock();
        double x = blk.getX() - blockUnderPlayer.getBlockX();
        double y = blk.getY() - blockUnderPlayer.getBlockY();
        double z = blk.getZ() - blockUnderPlayer.getBlockZ();
        return (int)Math.round(Math.sqrt(Math.pow(x, 2D) + Math.pow(y, 2D) + Math.pow(z, 2D)));
    }

    public int getXDistanceToBlock()
    {
        reset();
        return (int)Math.floor((double)(getTargetBlock().getX() - loc.getBlockX()) + 0.5D);
    }

    public int getYDistanceToBlock()
    {
        reset();
        return (int)Math.floor((double)(getTargetBlock().getY() - loc.getBlockY()) + viewHeight);
    }

    public int getZDistanceToBlock()
    {
        reset();
        return (int)Math.floor((double)(getTargetBlock().getZ() - loc.getBlockZ()) + 0.5D);
    }

    public Block getTargetBlock()
    {
        reset();
        while(getNextBlock() != null && (getCurrentBlock().getTypeId() == 0 || blockToIgnoreHasValue(getCurrentBlock().getTypeId()))) ;
        return getCurrentBlock();
    }

    public boolean setTargetBlock(int typeID)
    {
        if(Material.getMaterial(typeID) != null)
        {
            reset();
            while(getNextBlock() != null && getCurrentBlock().getTypeId() == 0) ;
            if(getCurrentBlock() != null)
            {
                Block blk = loc.getWorld().getBlockAt(targetPos.getBlockX(), targetPos.getBlockY(), targetPos.getBlockZ());
                blk.setTypeId(typeID);
                return true;
            }
        }
        return false;
    }

    public boolean setTargetBlock(Material type)
    {
        reset();
        while(getNextBlock() != null && (getCurrentBlock().getTypeId() == 0 || blockToIgnoreHasValue(getCurrentBlock().getTypeId()))) ;
        if(getCurrentBlock() != null)
        {
            Block blk = loc.getWorld().getBlockAt(targetPos.getBlockX(), targetPos.getBlockY(), targetPos.getBlockZ());
            blk.setType(type);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setTargetBlock(String type)
    {
        Material mat = Material.valueOf(type);
        if(mat != null)
        {
            reset();
            while(getNextBlock() != null && (getCurrentBlock().getTypeId() == 0 || blockToIgnoreHasValue(getCurrentBlock().getTypeId()))) ;
            if(getCurrentBlock() != null)
            {
                Block blk = loc.getWorld().getBlockAt(targetPos.getBlockX(), targetPos.getBlockY(), targetPos.getBlockZ());
                blk.setType(mat);
                return true;
            }
        }
        return false;
    }

    public Block getFaceBlock()
    {
        while(getNextBlock() != null && (getCurrentBlock().getTypeId() == 0 || blockToIgnoreHasValue(getCurrentBlock().getTypeId()))) ;
        if(getCurrentBlock() != null)
        {
            return getPreviousBlock();
        } else
        {
            return null;
        }
    }

    public boolean setFaceBlock(int typeID)
    {
        if(Material.getMaterial(typeID) != null && getCurrentBlock() != null)
        {
            Block blk = loc.getWorld().getBlockAt(prevPos.getBlockX(), prevPos.getBlockY(), prevPos.getBlockZ());
            blk.setTypeId(typeID);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setFaceBlock(Material type)
    {
        if(getCurrentBlock() != null)
        {
            Block blk = loc.getWorld().getBlockAt(prevPos.getBlockX(), prevPos.getBlockY(), prevPos.getBlockZ());
            blk.setType(type);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setFaceBlock(String type)
    {
        Material mat = Material.valueOf(type);
        if(mat != null && getCurrentBlock() != null)
        {
            Block blk = loc.getWorld().getBlockAt(prevPos.getBlockX(), prevPos.getBlockY(), prevPos.getBlockZ());
            blk.setType(mat);
            return true;
        } else
        {
            return false;
        }
    }

    public Block getNextBlock()
    {
        prevPos = targetPos.clone();
        do
        {
            curDistance += checkDistance;
            targetPosDouble.setX(offset.getX() + targetPosDouble.getX());
            targetPosDouble.setY(offset.getY() + targetPosDouble.getY());
            targetPosDouble.setZ(offset.getZ() + targetPosDouble.getZ());
            targetPos = new Vector(targetPosDouble.getBlockX(), targetPosDouble.getBlockY(), targetPosDouble.getBlockZ());
        } while(curDistance <= (double)maxDistance && targetPos.getBlockX() == prevPos.getBlockX() && targetPos.getBlockY() == prevPos.getBlockY() && targetPos.getBlockZ() == prevPos.getBlockZ());
        if(curDistance > (double)maxDistance)
        {
            return null;
        } else
        {
            return loc.getWorld().getBlockAt(targetPos.getBlockX(), targetPos.getBlockY(), targetPos.getBlockZ());
        }
    }

    public Block getCurrentBlock()
    {
        if(curDistance > (double)maxDistance)
        {
            return null;
        } else
        {
            return loc.getWorld().getBlockAt(targetPos.getBlockX(), targetPos.getBlockY(), targetPos.getBlockZ());
        }
    }

    public boolean setCurrentBlock(int typeID)
    {
        if(Material.getMaterial(typeID) != null)
        {
            Block blk = getCurrentBlock();
            if(blk != null)
            {
                blk.setTypeId(typeID);
                return true;
            }
        }
        return false;
    }

    public boolean setCurrentBlock(Material type)
    {
        Block blk = getCurrentBlock();
        if(blk != null)
        {
            blk.setType(type);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setCurrentBlock(String type)
    {
        Material mat = Material.valueOf(type);
        if(mat != null)
        {
            Block blk = getCurrentBlock();
            if(blk != null)
            {
                blk.setType(mat);
                return true;
            }
        }
        return false;
    }

    public Block getPreviousBlock()
    {
        return loc.getWorld().getBlockAt(prevPos.getBlockX(), prevPos.getBlockY(), prevPos.getBlockZ());
    }

    public boolean setPreviousBlock(int typeID)
    {
        if(Material.getMaterial(typeID) != null)
        {
            Block blk = getPreviousBlock();
            if(blk != null)
            {
                blk.setTypeId(typeID);
                return true;
            }
        }
        return false;
    }

    public boolean setPreviousBlock(Material type)
    {
        Block blk = getPreviousBlock();
        if(blk != null)
        {
            blk.setType(type);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean setPreviousBlock(String type)
    {
        Material mat = Material.valueOf(type);
        if(mat != null)
        {
            Block blk = getPreviousBlock();
            if(blk != null)
            {
                blk.setType(mat);
                return true;
            }
        }
        return false;
    }

    private int[] convertStringArraytoIntArray(ArrayList array)
    {
        if(array != null)
        {
            int intarray[] = new int[array.size()];
            for(int i = 0; i < array.size(); i++)
            {
                try
                {
                    intarray[i] = Integer.parseInt((String)array.get(i));
                }
                catch(NumberFormatException nfe)
                {
                    intarray[i] = 0;
                }
            }

            return intarray;
        } else
        {
            return null;
        }
    }

    private boolean blockToIgnoreHasValue(int value)
    {
        if(blockToIgnore != null && blockToIgnore.length > 0)
        {
            int ai[];
            int k = (ai = blockToIgnore).length;
            for(int j = 0; j < k; j++)
            {
                int i = ai[j];
                if(i == value)
                {
                    return true;
                }
            }

        }
        return false;
    }
}