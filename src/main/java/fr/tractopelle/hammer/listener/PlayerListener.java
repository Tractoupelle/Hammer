package fr.tractopelle.hammer.listener;

import fr.tractopelle.hammer.CorePlugin;
import fr.tractopelle.hammer.item.RNBItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerListener implements Listener {

    /* TODO
        Change verif where is looking the player with yaw and pitch
     */

    private final CorePlugin corePlugin;
    private final List<Location> blacklist = new ArrayList<>();
    public HashMap<String, Integer> blockFace = new HashMap<>();

    public PlayerListener(CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @EventHandler
    public void getBlockFace(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        BlockFace bFace = event.getBlockFace();

        if (itemInHand == null || itemInHand.getType().equals(Material.AIR) || !itemInHand.hasItemMeta())
            return;

        final RNBItem rnbItem = new RNBItem(itemInHand);

        if (rnbItem.containsTag("identifier") && rnbItem.getString("identifier").equalsIgnoreCase("hammer")) {
            if (bFace == BlockFace.UP || bFace == BlockFace.DOWN) {
                blockFace.put(player.getName(), 1);
            }
            if (bFace == BlockFace.NORTH || bFace == BlockFace.SOUTH) {
                blockFace.put(player.getName(), 2);
            }
            if (bFace == BlockFace.WEST || bFace == BlockFace.EAST) {
                blockFace.put(player.getName(), 3);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        if (event.isCancelled()) return;

        if (blacklist.contains(event.getBlock().getLocation())) return;

        final ItemStack itemStack = event.getPlayer().getItemInHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta())
            return;

        final RNBItem rnbItem = new RNBItem(itemStack);

        if (rnbItem.containsTag("identifier") && rnbItem.getString("identifier").equalsIgnoreCase("hammer")) {

            for (final Block block : getBlocks(event.getPlayer(), event.getBlock())) {

                blacklist.add(block.getLocation());

                final BlockBreakEvent breakEvent = new BlockBreakEvent(block, event.getPlayer());
                Bukkit.getPluginManager().callEvent(breakEvent);

                if (!breakEvent.isCancelled() && !(corePlugin.getConfiguration().getStringList("BLACKLIST-BLOCKS").contains(block.getType().name())))
                    block.breakNaturally();

                blacklist.remove(block.getLocation());


            }
        }
    }

    public List<Block> getBlocks(final Player player, final Block mainBlock) {

        ArrayList<Block> blocks = new ArrayList<Block>();

        if (blockFace.get(player.getName()) == 1) {
            blocks.add(mainBlock.getRelative(BlockFace.NORTH_WEST));
            blocks.add(mainBlock.getRelative(BlockFace.NORTH));
            blocks.add(mainBlock.getRelative(BlockFace.NORTH_EAST));
            blocks.add(mainBlock.getRelative(BlockFace.WEST));
            blocks.add(mainBlock.getRelative(BlockFace.EAST));
            blocks.add(mainBlock.getRelative(BlockFace.SOUTH_WEST));
            blocks.add(mainBlock.getRelative(BlockFace.SOUTH));
            blocks.add(mainBlock.getRelative(BlockFace.SOUTH_EAST));
        }
        if (blockFace.get(player.getName()) == 2) {
            blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
            blocks.add(mainBlock.getRelative(BlockFace.UP));
            blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
            blocks.add(mainBlock.getRelative(BlockFace.WEST));
            blocks.add(mainBlock.getRelative(BlockFace.EAST));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST));
        }
        if (blockFace.get(player.getName()) == 3) {
            blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
            blocks.add(mainBlock.getRelative(BlockFace.UP));
            blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
            blocks.add(mainBlock.getRelative(BlockFace.NORTH));
            blocks.add(mainBlock.getRelative(BlockFace.SOUTH));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN));
            blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH));
        }
        return blocks;
    }
    
}
