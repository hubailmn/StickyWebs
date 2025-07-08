package cc.hubailmn.stickywebs.lisener.cobweb;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.util.CobWebUtil;
import cc.hubailmn.utility.interaction.SoundUtil;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FireworkListener implements Listener {

  @EventHandler
  public void onFireworkUse(PlayerInteractEvent event) {
    if (!StickyWebs.getInstance().getStickyWebsConfig().isDisableFireworkUse()) return;

    Action action = event.getAction();
    if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

    Player player = event.getPlayer();
    ItemStack item = player.getInventory().getItemInMainHand();
    if (item.getType() != Material.FIREWORK_ROCKET) return;

    ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
    if (CobWebUtil.isPlayerInCobweb(nmsPlayer)) {
      event.setCancelled(true);
      player.sendActionBar("§cYou can't use fireworks in cobwebs!");
      SoundUtil.play(player, SoundUtil.SoundType.DENY);
    }
  }
}
