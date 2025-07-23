package cc.hubailmn.stickywebs.util;

import cc.hubailmn.stickywebs.StickyWebs;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Getter
public class BlockResetManager {

    private final ConcurrentSkipListMap<Long, List<BlockResetTask>> resetQueue = new ConcurrentSkipListMap<>();
    private final Set<UUID> bypassList = ConcurrentHashMap.newKeySet();

    private final JavaPlugin plugin;

    private int maxBlocksPerRun;
    private long taskIntervalTicks;

    public BlockResetManager(JavaPlugin plugin) {
        this.plugin = plugin;
        resetConfig();
        startTask();
    }

    public void resetConfig() {
        var config = StickyWebs.getInstance().getStickyWebsConfig();
        maxBlocksPerRun = config.getMaxBatch();
        taskIntervalTicks = config.getTaskIntervalTicks();
    }

    public boolean toggleBypass(UUID uuid) {
        if (!bypassList.add(uuid)) {
            bypassList.remove(uuid);
            return false;
        }
        return true;
    }

    public boolean isBypass(UUID uuid) {
        return bypassList.contains(uuid);
    }

    public void queueReset(Block block, long delayTicks) {
        long executeAt = Bukkit.getServer().getCurrentTick() + delayTicks;
        Location loc = block.getLocation();
        Material originalType = block.getType();

        resetQueue.computeIfAbsent(executeAt, __ -> new ArrayList<>())
                .add(new BlockResetTask(loc, originalType));
    }

    private void startTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long now = Bukkit.getServer().getCurrentTick();
            int processedBlocks = 0;

            Iterator<Map.Entry<Long, List<BlockResetTask>>> iterator = resetQueue.entrySet().iterator();

            while (iterator.hasNext() && processedBlocks < maxBlocksPerRun) {
                Map.Entry<Long, List<BlockResetTask>> entry = iterator.next();

                if (entry.getKey() > now) break;

                List<BlockResetTask> tasks = entry.getValue();
                Iterator<BlockResetTask> taskIterator = tasks.iterator();

                while (taskIterator.hasNext() && processedBlocks < maxBlocksPerRun) {
                    BlockResetTask task = taskIterator.next();
                    Block block = task.location.getBlock();

                    if (block.getType() != task.originalType) {
                        taskIterator.remove();
                        processedBlocks++;
                        continue;
                    }

                    block.setType(Material.AIR, false);
                    taskIterator.remove();
                    processedBlocks++;

                }

                if (tasks.isEmpty()) {
                    iterator.remove();
                }
            }
        }, 1L, taskIntervalTicks);
    }

    private static class BlockResetTask {
        final Location location;
        final Material originalType;

        BlockResetTask(Location location, Material originalType) {
            this.location = location.clone();
            this.originalType = originalType;
        }
    }
}
