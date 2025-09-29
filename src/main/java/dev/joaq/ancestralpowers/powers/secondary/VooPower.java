package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;

public class VooPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {

        if (!activate) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
            return;
        }
        player.getAbilities().allowFlying = true;
        player.sendAbilitiesUpdate();
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        player.getAbilities().allowFlying = false;
        player.getAbilities().flying = false;
        player.sendAbilitiesUpdate();
    }
}
