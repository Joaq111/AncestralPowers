package dev.joaq.ancestralpowers.networking;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleGPayload;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleRPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModPacketsC2S {

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(ToggleRPayload.PAYLOAD_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            player.getServer().execute(() -> {
                PlayerTraits traits = MyComponents.TRAITS.get(player);
                traits.setActPower_main(!traits.getActPower_main());
                player.sendMessage(Text.literal("R = " + traits.getActPower_main()), false);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(ToggleGPayload.PAYLOAD_ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            player.getServer().execute(() -> {
                PlayerTraits traits = MyComponents.TRAITS.get(player);
                traits.setActPower_secondary(!traits.getActPower_secondary());
                player.sendMessage(Text.literal("G = " + traits.getActPower_secondary()), false);
            });
        });
    }
}
