package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.util.RandomUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerJoinEvent {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            PlayerTraits traits = MyComponents.TRAITS.get(player);


            if(traits.getMovementPower() == null) {
                traits.setMovementPower(RandomUtils.randomMovement());
                traits.setMainPower(RandomUtils.randomMain());
                traits.setIntelligence(RandomUtils.randomIntelligence());
                traits.setActivate(false);
            }
//            PowersManager.applyAll(player, traits);

            player.sendMessage(Text.literal(
                    "Seus poderes: " + traits.getMovementPower() + " | " +
                            traits.getMainPower() + " | " + traits.getIntelligence()
            ), false);
        });


    }

}
