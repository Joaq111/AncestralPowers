package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.commands.ModCommands;
import dev.joaq.ancestralpowers.events.PlayerDeathEvent;
import dev.joaq.ancestralpowers.events.PlayerJoinEvent;
import dev.joaq.ancestralpowers.events.PlayerPowersTickHandler;
import dev.joaq.ancestralpowers.networking.ModPacketsC2S;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleGPayload;
import dev.joaq.ancestralpowers.networking.packet.c2s.ToggleRPayload;
import dev.joaq.ancestralpowers.registry.ModEffects;
import dev.joaq.ancestralpowers.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncestralPowers implements ModInitializer, ClientModInitializer, DedicatedServerModInitializer {
    public static final String MOD_ID = "ancestralpowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier identifier(String path) {
        return Identifier.of(AncestralPowers.MOD_ID, path);
    }

    @Override
    public void onInitialize() {

        PayloadTypeRegistry.playC2S().register(ToggleGPayload.PAYLOAD_ID, ToggleGPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ToggleRPayload.PAYLOAD_ID, ToggleRPayload.CODEC);
        ModPacketsC2S.register();
        PlayerJoinEvent.register();
        PlayerPowersTickHandler.register();
        ModCommands.register();
        ModEntities.register();
        ModEffects.register();
        PlayerDeathEvent.ImortalRegister();
        System.out.println("ancestralpowers: AncestralPowers inicializado!");
    }


    @Override
    public void onInitializeClient() {

    }

    @Override
    public void onInitializeServer() {

    }
}
