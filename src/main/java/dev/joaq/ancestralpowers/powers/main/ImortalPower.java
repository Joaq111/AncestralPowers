package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class ImortalPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {


            ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, amount) -> {
                if (entity instanceof ServerPlayerEntity serverPlayer) {
                    PlayerTraits traits = MyComponents.TRAITS.get(serverPlayer);

                    if ("Imortalidade".equals(traits.getMainPower())) {
                        return false;
                    }
                }
                return true;
            });

    }


    @Override
    public void reset(ServerPlayerEntity player) { }
}
