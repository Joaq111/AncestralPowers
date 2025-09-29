package dev.joaq.ancestralpowers.networking;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.networking.packet.c2s.UseActivePowerTypesC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModPacketsC2S {

    public static void register() {
        ServerPlayConnectionEvents.INIT.register((handler, server) ->
                ServerPlayNetworking.registerReceiver(handler, UseActivePowerTypesC2SPacket.PACKET_ID, ModPacketsC2S::onUseActivePowers)
        );
    }

    private static void onUseActivePowers(UseActivePowerTypesC2SPacket payload, ServerPlayNetworking.Context context) {
        ServerPlayerEntity player = context.player();

        context.server().execute(() -> {
            PlayerTraits traits = MyComponents.TRAITS.get(player);
            for (Identifier powerId : payload.powerIds()) {
                if (powerId.equals(Identifier.of("ancestralpowers", "main_power"))) {
                    traits.setActPower_main(!traits.getActPower_main());
                    player.sendMessage(Text.literal("Poder 1 =  " + traits.getActPower_main()), false);
                }
            }
            for (Identifier powerId : payload.powerIds()) {
                if (powerId.equals(Identifier.of("ancestralpowers", "secondary_power"))) {
                    traits.setActPower_secondary(!traits.getActPower_secondary());
                    player.sendMessage(Text.literal("Poder 2 =  " + traits.getActPower_secondary()), false);
                }
            }

        });
    }
}
