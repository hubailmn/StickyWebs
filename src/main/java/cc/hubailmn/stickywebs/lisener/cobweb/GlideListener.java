package cc.hubailmn.stickywebs.lisener.cobweb;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.util.CobWebUtil;
import cc.hubailmn.utility.interaction.SoundUtil;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class GlideListener implements Listener {

    @EventHandler
    public void onGlide(EntityToggleGlideEvent event) {
        if (!StickyWebs.getInstance().getStickyWebsConfig().isDisableGlideInCobwebs()) return;

        if (!(event.getEntity() instanceof Player player)) return;
        if (!event.isGliding()) return;

        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
        if (CobWebUtil.isPlayerInCobweb(nmsPlayer)) {
            event.setCancelled(true);
            player.setGliding(false);
            player.sendActionBar("§cYou can't glide inside cobwebs!");
            SoundUtil.play(player, SoundUtil.SoundType.DENY);
        }
    }

}
