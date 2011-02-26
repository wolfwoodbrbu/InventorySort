<h1>InventorySort - Sorts your inventory or the chest your looking at.</h1>
<h2>Version: 1.5</h2>

<h2>Download:</h2>
- [InventorySortv1.5.zip](http://dl.dropbox.com/u/4299882/Minecraft/InventorySort/1.5/InventorySort1.5.zip)
- [Jar](http://dl.dropbox.com/u/4299882/Minecraft/InventorySort/1.5/InventorySort.jar)

<h2>Source:</h2>
- [https://github.com/wolfwoodbrbu/InventorySort](https://github.com/wolfwoodbrbu/InventorySort)

- [Permissions Required](http://forums.bukkit.org/threads/admn-info-permissions-v2-0-revolutionizing-the-group-system.1403/)

InventorySort also works with Permissions 2.0

InventorySort allows you to sort all or part of your inventory ordered on the block/item TypeId number. You can also sort chests in the same manner.

Commands:
--------

* /sort all - Sorts all of the slots in your inventory(slots 0-35)
* /sort top - Sorts the "Backpack" slots of your inventory(slots 9-35)
* /sort 4 35 - Sorts slots 4-35
* /sort 30 10 - Sorts slots 10-35
* /sortchest - Sorts all of the slots in a chest or doublechest (alias: /srtc)
* /sort stack - Toggles whether sorting stack's first (default off) will remember between sessions as long as the server hasn't been restarted.

Permissions:
-----------

    iSort.basic.all - /sort all command
    iSort.basic.top - /sort top command
    iSort.basic.range - /sort [0-35] [0-35] command
    iSort.basic.chest - /sortchest command
    iSort.adv.stack - /sort stack command[/CODE]

Side Note/Thanks:
----------------

The plugin Stacks items first then sorts them. This is based from the stack command in [WorldGuard](http://forums.bukkit.org/threads/sec-worldguard-v3-2-2-protect-areas-block-fire-info-about-4-x-available.790/). This also used the ItemType.java from it.

To get the chest your looking at I'm using TargetBlock.java from the [MySignEdit](http://forums.bukkit.org/threads/mech-mysignedit-v1-2.1436/) plugin.

I love open-source!

Known Issues:
------------

* <del>When sorting a doublechest if you sort the right side of the chest it places the items in the top slots then the bottom slots to fix this right now sort the left chest.</del>(fixed)

Known Bugs:
----------

* (fixed) <del>Permission's 2.1 bug with commandhandler null error</del>

To Do:
-----

* <del>Sort the same way if either side of a doublechest is targeted.</del>(done)
* <del>Permissions and command arguments for the stacking of items not just always stack.</del>(done)
* Faster sorting Algorithm.
* <del>Remove TSLPC</del> (done)

Change Log:
----------

v1.2 - 2/18/2011

+ Initial Release to bukkit.org

v1.3 - 2/21/2011

+ [Fixed] doublechest detection
+ [Added] /srtc alias for /sortchest

v1.4 - 2/24/2011

+ [Removed]TSLPC
+ Works on CraftBukkit 435+

v1.5 - 2/25/2011

+ Complete rewrite of the InventorySort to allow for easier expandability.
+ [Fixed] Permissions 2.1 errors
+ Works on CraftBukkit 449+
+ [Added] /sort stack command to toggle stacking