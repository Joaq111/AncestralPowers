package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowersManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerPowersTickHandler  {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                PlayerTraits traits = MyComponents.TRAITS.get(player);
                PowersManager.applyAll(player, traits);
            }
        });
    }
}
