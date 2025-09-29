package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.powers.Power;
import dev.joaq.ancestralpowers.powers.teleport.TeleportLogic;
import dev.joaq.ancestralpowers.powers.teleport.TeleportParticles;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SuperTeleporteSecondaryPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        if (!activate) return;

        Vec3d targetPos = TeleportLogic.getTargetPosition(player, 10);
        TeleportParticles.spawnTargetParticle(player, targetPos);
    }

    @Override
    public void reset(ServerPlayerEntity player) {
    }
}
