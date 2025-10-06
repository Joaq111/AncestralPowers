package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerDeathEvent{

    public static void ImortalRegister() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, amount) -> {
            if (entity instanceof ServerPlayerEntity serverPlayer) {
                PlayerTraits traits = MyComponents.TRAITS.get(serverPlayer);

                if ("Imortalidade".equals(traits.getMainPower()) && traits.getActPower_main()) {
                    serverPlayer.setHealth(1.0f);
                    serverPlayer.sendMessage(Text.literal("Você foi salvo pela imortalidade!"));
                    return false;
                }
            }
            return true;
        });

    }



}
