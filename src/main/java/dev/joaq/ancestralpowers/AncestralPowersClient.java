package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.client.ModKeyBinds;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleGPayload;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleRPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;


@Environment(EnvType.CLIENT)
public class AncestralPowersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeyBinds.registerKeyBinds();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            if (ModKeyBinds.G_KEY.wasPressed()) {
                ClientPlayNetworking.send(new ToggleGPayload(true));
            }

            if (ModKeyBinds.R_KEY.wasPressed()) {
                ClientPlayNetworking.send(new ToggleRPayload(true));
            }
        });
    }

}
