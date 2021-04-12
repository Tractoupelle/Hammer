package fr.tractopelle.hammer.listener;

import fr.tractopelle.hammer.CorePlugin;
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

public class HammerListener implements Listener {

    private final CorePlugin corePlugin;
    private final List<Location> blacklist = new ArrayList<>();
    public HashMap<String, Integer> blockFace = new HashMap<>();

    public HammerListener(CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @EventHandler
    public void getBlockFace(final PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        BlockFace bFace = event.getBlockFace();

        if (itemInHand.getType().equals(Material.DIAMOND_PICKAXE)) {
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

        final ItemStack item = event.getPlayer().getItemInHand();

        if (item != null &&
                item.getType().equals(corePlugin.getHammerManager().getMaterial())
                && item.hasItemMeta()
                && item.getItemMeta().hasLore()
                && item.getItemMeta().getLore().equals(corePlugin.getHammerManager().getLore())) {

            for (final Block block : getBlocks(event.getPlayer(), event.getBlock())) {

                blacklist.add(block.getLocation());

                final BlockBreakEvent breakEvent = new BlockBreakEvent(block, event.getPlayer());
                Bukkit.getPluginManager().callEvent(breakEvent);

                if (!breakEvent.isCancelled() && (!(corePlugin.getHammerManager().getBlacklistBlocks().contains(block.getType()))))
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
