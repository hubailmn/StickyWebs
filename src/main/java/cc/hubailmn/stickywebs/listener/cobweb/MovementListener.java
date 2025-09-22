package cc.hubailmn.stickywebs.listener.cobweb;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.util.CobWebUtil;
import cc.hubailmn.utility.interaction.CSend;
import cc.hubailmn.utility.interaction.player.PlayerUtil;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MovementListener implements Listener {

    private final Set<UUID> recentlyDamaged = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!StickyWebs.getInstance().getStickyWebsConfig().isDisableFlyThroughCobwebs()) return;

        Player player = event.getPlayer();
        if (recentlyDamaged.contains(player.getUniqueId())) return;

        Location from = event.getFrom();
        Location to = event.getTo();

        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        if (!CobWebUtil.isPlayerInCobweb(nmsPlayer)) return;

        boolean isBedrock = PlayerUtil.isBedrock(player);
        boolean isGliding = player.isGliding();

        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double dz = to.getZ() - from.getZ();

        double speed = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (StickyWebs.getInstance().getStickyWebsConfig().isDebug()) {
            CSend.log(player.getName() + " speed=" + speed + " dy=" + dy + " pitch=" + player.getLocation().getPitch() + " isBedrock=" + isBedrock + " isGliding=" + isGliding);
        }

        if (isGliding) player.setGliding(false);

        if (isBedrock) {
            if (speed > 0.05 && player.getLocation().getPitch() < -70) {
                teleportBack(player, from, to);
            }
        }

        if (from.distance(to) > 0.10) {
            teleportBack(player, from, to);
            player.sendActionBar("§cYou can't move through cobwebs!");
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (CobWebUtil.isPlayerInCobweb(((CraftPlayer) player).getHandle())) {
                recentlyDamaged.add(player.getUniqueId());

                Bukkit.getScheduler().runTaskLater(StickyWebs.getInstance(), () -> {
                    recentlyDamaged.remove(player.getUniqueId());
                }, 5L);
            }
        }
    }

    private void teleportBack(Player player, Location from, Location to) {
        Location teleportLocation = from.clone();
        teleportLocation.setYaw(to.getYaw());
        teleportLocation.setPitch(to.getPitch());
        player.teleport(teleportLocation);
    }
}
