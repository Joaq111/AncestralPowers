    package dev.joaq.ancestralpowers.powers.teleport;

    import net.minecraft.entity.player.PlayerEntity;
    import net.minecraft.util.hit.BlockHitResult;
    import net.minecraft.util.hit.HitResult;
    import net.minecraft.util.math.Vec3d;
    import net.minecraft.world.RaycastContext;

    public class TeleportRaycast {

        public static Vec3d getLookPosition(PlayerEntity player, double maxDistance) {
            Vec3d start = player.getCameraPosVec(1.0f);
            Vec3d direction = player.getRotationVec(1.0f);
            Vec3d end = start.add(direction.multiply(maxDistance));

            HitResult hit = player.getWorld().raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.OUTLINE,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));

            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                return new Vec3d(blockHit.getPos().getX() + 0.5, blockHit.getPos().getY(), blockHit.getPos().getZ() + 0.5);
            }

            return end;
        }
    }

