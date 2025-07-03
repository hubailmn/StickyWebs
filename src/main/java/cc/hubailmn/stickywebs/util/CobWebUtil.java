package cc.hubailmn.stickywebs.util;

import cc.hubailmn.stickywebs.StickyWebs;
import cc.hubailmn.stickywebs.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CobWebUtil {

  public static boolean isPlayerInCobweb(ServerPlayer player) {
    Config config = StickyWebs.getInstance().getStickyWebsConfig();

    double x = config.getHitboxInflateX();
    double y = config.getHitboxInflateY();
    double z = config.getHitboxInflateZ();
    AABB box = player.getBoundingBox().inflate(x, y, z);

    Level level = player.level();

    int minX = (int) Math.floor(box.minX);
    int minY = (int) Math.floor(box.minY);
    int minZ = (int) Math.floor(box.minZ);

    int maxX = (int) Math.floor(box.maxX);
    int maxY = (int) Math.floor(box.maxY);
    int maxZ = (int) Math.floor(box.maxZ);

    for (BlockPos pos : BlockPos.betweenClosed(minX, minY, minZ, maxX, maxY, maxZ)) {
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() == Blocks.COBWEB) {
        return true;
      }
    }

    return false;
  }

}
