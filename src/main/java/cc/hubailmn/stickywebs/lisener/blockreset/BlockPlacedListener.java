package cc.hubailmn.stickywebs.lisener.blockreset;

import cc.hubailmn.stickywebs.StickyWebs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Set;

public class BlockPlacedListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!StickyWebs.getInstance().getStickyWebsConfig().isBlockResetEnabled()) return;
        if (StickyWebs.getInstance().getBlockResetManager().isBypass(event.getPlayer().getUniqueId())) return;

        Block block = event.getBlockPlaced();
        Material type = block.getType();

        Set<Material> allowed = StickyWebs.getInstance().getStickyWebsConfig().getBlockResetAllowedBlocks();
        if (!allowed.isEmpty() && !allowed.contains(type)) return;

        long delayTicks = StickyWebs.getInstance().getStickyWebsConfig().getBlockResetDelay();
        Bukkit.getScheduler().runTaskLater(StickyWebs.getInstance(), () -> {
            if (block.getType() == type) {
                block.setType(Material.AIR, false);
            }
        }, delayTicks);
    }

}
