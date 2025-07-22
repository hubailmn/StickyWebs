package cc.hubailmn.stickywebs.lisener.minecart;

import cc.hubailmn.stickywebs.StickyWebs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.phys.Vec3;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TntMinecartBoostListener implements Listener {

    private final Map<UUID, Boolean> canBeBoosted = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerPlaceTntMinecart(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        var config = StickyWebs.getInstance().getStickyWebsConfig();

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.TNT_MINECART) return;

        Block block = event.getClickedBlock();
        if (block == null || !config.getValidBoostRails().contains(block.getType())) return;

        if (config.isRequirePowered() &&
                (block.getType() != Material.POWERED_RAIL ||
                        !(block.getBlockData() instanceof org.bukkit.block.data.Powerable p) || !p.isPowered())) {
            return;
        }

        event.setCancelled(true);

        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            item.setAmount(item.getAmount() - 1);
        }

        spawnCustomTntMinecart(player, block);
    }

    private void spawnCustomTntMinecart(Player player, Block baseBlock) {
        var plugin = StickyWebs.getInstance();
        var config = plugin.getStickyWebsConfig();
        ServerLevel level = ((CraftWorld) baseBlock.getWorld()).getHandle();

        double x = baseBlock.getX() + 0.5;
        double y = baseBlock.getY() + 0.05;
        double z = baseBlock.getZ() + 0.5;

        MinecartTNT minecart = new MinecartTNT(level, x, y, z);

        minecart.setNoGravity(false);

        minecart.setDerailedVelocityMod(new Vector(config.getBoostMultiplier(), 1, config.getBoostMultiplier()));

        minecart.setDeltaMovement(new Vec3(0, 0, 0));
        minecart.hurtMarked = true;

        level.addFreshEntity(minecart);

        canBeBoosted.put(minecart.getUUID(), true);
    }

    @EventHandler
    public void onMinecartPushed(VehicleEntityCollisionEvent event) {
        Entity vehicle = event.getVehicle();
        Entity entity = event.getEntity();

        if (!(vehicle instanceof org.bukkit.entity.minecart.ExplosiveMinecart minecart)) return;
        if (!(entity instanceof Player player)) return;

        UUID minecartId = minecart.getUniqueId();
        if (!canBeBoosted.getOrDefault(minecartId, false)) return;

        Vector direction = player.getLocation().getDirection().add(new Vector(0, 0.3, 0)).normalize();
        Vector velocity = direction.multiply(5);

        MinecartTNT nmsMinecart = (MinecartTNT) ((CraftWorld) minecart.getWorld()).getHandle().getEntity(minecartId);

        if (nmsMinecart != null) {
            nmsMinecart.setDeltaMovement(new Vec3(velocity.getX(), velocity.getY(), velocity.getZ()));
            canBeBoosted.put(minecartId, false);
        }
    }

}
