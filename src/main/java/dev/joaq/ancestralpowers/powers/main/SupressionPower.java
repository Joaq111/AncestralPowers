package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.powers.Power;
import dev.joaq.ancestralpowers.registry.ModEffects;
import dev.joaq.ancestralpowers.util.PlayerUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class SupressionPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        if (!activate) return;

        ServerPlayerEntity target = PlayerUtils.getPlayerLookedAt(player, 20.0D);
        if (target != null) {

            target.addStatusEffect(new StatusEffectInstance(
                    ModEffects.POWER_SUPPRESSION,
                    1,
                    0,
                    false,
                    false
            ));
            target.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.GLOWING,
                    1,
                    0,
                    false,
                    false
            ));
        }
    }

    @Override
    public void reset(ServerPlayerEntity player) { }
}