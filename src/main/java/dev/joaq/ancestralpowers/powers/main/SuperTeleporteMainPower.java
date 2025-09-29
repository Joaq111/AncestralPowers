package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import dev.joaq.ancestralpowers.powers.teleport.TeleportLogic;
import dev.joaq.ancestralpowers.powers.teleport.TeleportParticles;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SuperTeleporteMainPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        if (!activate) return;

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        if (stamina < 25) {
            traits.setActPower_main(false);
            return;
        }

        Vec3d targetPos = TeleportLogic.getTargetPosition(player, 10);
        player.teleport(targetPos.x, targetPos.y, targetPos.z, true);
        player.fallDistance = 0;

        TeleportParticles.spawnTargetParticle(player, targetPos);

        traits.setStamina(traits.getStamina() - 25);
        traits.setActPower_main(false);
    }

    @Override
    public void reset(ServerPlayerEntity player) {
    }
}
