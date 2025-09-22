package cc.hubailmn.stickywebs.listener.blockreset;

import cc.hubailmn.stickywebs.StickyWebs;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Set;

public class BlockPlacedListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        StickyWebs plugin = StickyWebs.getInstance();
        if (!plugin.getStickyWebsConfig().isBlockResetEnabled()) return;
        if (plugin.getBlockResetManager().isBypass(event.getPlayer().getUniqueId())) return;

        Material type = event.getBlockPlaced().getType();
        Set<Material> allowed = plugin.getStickyWebsConfig().getBlockResetAllowedBlocks();

        if (!allowed.isEmpty() && !allowed.contains(type)) return;
        plugin.getBlockResetManager().queueReset(event.getBlockPlaced(), plugin.getStickyWebsConfig().getBlockResetDelay());
    }

}
