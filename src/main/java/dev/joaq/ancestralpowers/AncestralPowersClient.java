package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.client.ModKeyBinds;
import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.networking.packet.c2s.UseActivePowerTypesC2SPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class AncestralPowersClient implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        ModKeyBinds.registerKeyBinds();
        PayloadTypeRegistry.playC2S().register(
                UseActivePowerTypesC2SPacket.PACKET_ID,
                UseActivePowerTypesC2SPacket.PACKET_CODEC
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ModKeyBinds.POWER_KEY.wasPressed()) {
                if (client.player != null) {
                    // manda pro servidor
                    ClientPlayNetworking.send(new UseActivePowerTypesC2SPacket(
                            List.of(Identifier.of("ancestralpowers", "main_power"))
                    ));
                }
            }
            while (ModKeyBinds.POWER_KEY2.wasPressed()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(new UseActivePowerTypesC2SPacket(
                            List.of(Identifier.of("ancestralpowers", "secondary_power"))
                    ));
                }
            }
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {

        });


    }
}