package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowersManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerPowersTickHandler {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register((server) -> {


                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    PlayerTraits traits = MyComponents.TRAITS.get(player);
                    PowersManager.applyMainPower(player, traits.getMainPower(), traits.getActPower_main(), traits.getStamina());
                    PowersManager.applyMovementPower(player, traits.getMovementPower(), traits.getActPower_secondary(), traits.getStamina());


                    player.sendMessage(Text.literal(""+ traits.getStamina()), true);
                    Float current = traits.getStamina();
                    traits.setStamina(Math.min(current + 0.25f, 100f));
                }

        });
    }
}