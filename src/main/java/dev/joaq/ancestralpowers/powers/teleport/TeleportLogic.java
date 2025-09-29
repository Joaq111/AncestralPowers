package dev.joaq.ancestralpowers.powers.teleport;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportLogic {

    public static Vec3d getTargetPosition(PlayerEntity player, int maxDistance) {
        World world = player.getWorld();
        Vec3d start = player.getEyePos();
        Vec3d look = player.getRotationVec(1);

        Vec3d lastSafePos = start;
        double step = 0.1;

        for (double d = 0; d <= maxDistance; d += step) {
            Vec3d checkPos = start.add(look.multiply(d));
            BlockPos blockPos = new BlockPos(
                    (int)Math.floor(checkPos.x),
                    (int)Math.floor(checkPos.y),
                    (int)Math.floor(checkPos.z)
            );

            if (!world.getBlockState(blockPos).isAir()) break;
            lastSafePos = checkPos;
        }

        return lastSafePos;
    }
}
