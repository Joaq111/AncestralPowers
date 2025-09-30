package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TelepotePower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        if (!activate) return;

        if (stamina < 25) {
            traits.setActPower_secondary(false);
            return;
        }
        Vec3d targetPos = getTargetGroundPosition(player, 10);
        MyComponents.TRAITS.get(player).setTeleportTarget(targetPos);

        if (targetPos == null) {
            player.sendMessage(Text.literal("⚠ Defina um alvo com o poder secundário antes de teleportar!"), true);
            traits.setActPower_secondary(false);

            return;
        }

        player.teleport(targetPos.x, targetPos.y, targetPos.z, true);
        player.fallDistance = 0;

        traits.setStamina(traits.getStamina() - 25);
        traits.setActPower_secondary(false);
        traits.clearTeleportTarget();
    }

    @Override
    public void reset(ServerPlayerEntity player) {}

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

        // desce até o chão
        BlockPos ground = new BlockPos((int)lastSafe.x, (int)lastSafe.y, (int)lastSafe.z);
        while (world.getBlockState(ground.down()).isAir() && ground.getY() > world.getBottomY()) {
            ground = ground.down();
        }

        return new Vec3d(ground.getX() + 0.5, ground.getY() + 1, ground.getZ() + 0.5);
    }

}