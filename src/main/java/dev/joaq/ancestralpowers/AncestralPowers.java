package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.events.PlayerJoinEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncestralPowers implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "ancestralpowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        System.out.println("ancestralpowers: AncestralPowers inicializado!");
        PlayerJoinEvent.register();
    }

    @Override
    public void onInitializeClient() {

    }
}
