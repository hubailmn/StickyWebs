package cc.hubailmn.stickywebs.lisener;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.util.CobWebUtil;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementListener implements Listener {

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!StickyWebs.getInstance().getStickyWebsConfig().isDisableFlyThroughCobwebs()) return;

    Location from = event.getFrom();
    Location to = event.getTo();
    if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
      return;
    }

    Player player = event.getPlayer();
    ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

    if (!CobWebUtil.isPlayerInCobweb(nmsPlayer)) return;

    if (player.isGliding()) {
      player.setGliding(false);
    }

    if (from.distance(to) > 0.10) {
      event.setCancelled(true);
      player.teleport(from);
      player.sendActionBar("§cYou can't move through cobwebs!");
    }
  }

}
