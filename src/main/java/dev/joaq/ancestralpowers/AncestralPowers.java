package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.commands.ModCommands;
import dev.joaq.ancestralpowers.events.PlayerJoinEvent;
import dev.joaq.ancestralpowers.events.PlayerPowersTickHandler;
import dev.joaq.ancestralpowers.networking.ModPacketsC2S;
import dev.joaq.ancestralpowers.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncestralPowers implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "ancestralpowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Identifier identifier(String path) {
        return Identifier.of(AncestralPowers.MOD_ID, path);
    }

    @Override
    public void onInitialize() {

        System.out.println("ancestralpowers: AncestralPowers inicializado!");
        PlayerJoinEvent.register();
        PlayerPowersTickHandler.register();
        ModCommands.register();
        ModPacketsC2S.register();
        ModEntities.register();

    }

    @Override
    public void onInitializeClient() {

    }

}
