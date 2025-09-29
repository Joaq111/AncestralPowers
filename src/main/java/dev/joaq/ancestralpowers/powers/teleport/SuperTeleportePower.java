package dev.joaq.ancestralpowers.powers.teleport;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SuperTeleportePower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);

        // Se não está ativo ou stamina insuficiente, desativa o poder principal
        if (!activate || stamina < 25) {
            traits.setActPower_main(false);
            return;
        }

        // Teleporte
        Vec3d targetPos = TeleportLogic.getTargetPosition(player, 10);
        player.teleport(targetPos.x, targetPos.y, targetPos.z, true);
        player.fallDistance = 0;

        // Partículas no destino
        TeleportParticles.spawnTargetParticle(player, targetPos);

        // Consome stamina e desativa o secundário se necessário
        traits.setStamina(traits.getStamina() - 25);
        traits.setActPower_secondary(false);

        // Partículas contínuas para movimento (opcional, pode ser ativado no tick)
        if (activate && stamina >= 0) {
            TeleportParticles.spawnTargetParticle(player, targetPos);
        }
    }

    @Override
    public void reset(ServerPlayerEntity player) {

    }
}
