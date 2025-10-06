package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SuperTeleportSecondaryPower extends PowerBase {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Secondary");
    }

    @Override
    protected float staminaCost() {
        return 0.5f;
    }

    @Override
    protected String ActivationType() {
        return "TOGGLE";
    }

    @Override
    protected void disablePowerSpecific(ServerPlayerEntity player) {

    }

    @Override
    protected boolean executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {

        Vec3d targetPos = getTargetGroundPosition(player, 50);
        spawnCircleParticles(player, targetPos, 1.5, 15);

        MyComponents.TRAITS.get(player).setTeleportTarget(targetPos);
        return true;
    }

    private Vec3d getTargetGroundPosition(ServerPlayerEntity player, int maxDistance) {
        Vec3d start = player.getEyePos();
        Vec3d look = player.getRotationVec(1);
        World world = player.getWorld();

        Vec3d lastSafe = start;
        for (double d = 0; d <= maxDistance; d += 0.1) {
            Vec3d checkPos = start.add(look.multiply(d));
            BlockPos blockPos = new BlockPos((int)checkPos.x, (int)checkPos.y, (int)checkPos.z);
            if (!world.getBlockState(blockPos).isAir()) break;
            lastSafe = checkPos;
        }


        BlockPos ground = new BlockPos((int)lastSafe.x, (int)lastSafe.y, (int)lastSafe.z);
        while (world.getBlockState(ground.down()).isAir() && ground.getY() > world.getBottomY()) {
            ground = ground.down();
        }

        return new Vec3d(ground.getX() + 0.5, ground.getY() + 1, ground.getZ() + 0.5);
    }

    private void spawnCircleParticles(ServerPlayerEntity player, Vec3d pos, double radius, int points) {
        if (!(player.getWorld() instanceof ServerWorld serverWorld)) return;
        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double x = pos.x + radius * Math.cos(angle);
            double z = pos.z + radius * Math.sin(angle);
            serverWorld.spawnParticles(
                    ParticleTypes.DRAGON_BREATH,
                    x, pos.y - 1, z,
                    1,
                    0, 0, 0,
                    0.05
            );
        }
    }
}
