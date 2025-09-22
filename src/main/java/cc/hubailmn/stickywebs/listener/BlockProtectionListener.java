package cc.hubailmn.stickywebs.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockProtectionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        if (event.getBlock().getType() != Material.BEACON) return;

        if (!event.getPlayer().hasPermission("stickywebs.placebeacon")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) return;

        event.blockList().removeIf(block -> block.getType() == Material.BEACON);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockExplode(BlockExplodeEvent event) {
        if (event.isCancelled()) return;

        event.blockList().removeIf(block -> block.getType() == Material.BEACON);
    }
}