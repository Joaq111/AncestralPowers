package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.client.ModKeyBinds;
import dev.joaq.ancestralpowers.commands.ModCommands;
import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.events.PlayerJoinEvent;
import dev.joaq.ancestralpowers.events.PlayerPowersTickHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AncestralPowers implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "ancestralpowers";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {


        System.out.println("ancestralpowers: AncestralPowers inicializado!");
        PlayerJoinEvent.register();
        PlayerPowersTickHandler.register();
        ModCommands.register();
        // registra a tecla
        ModKeyBinds.registerKeyBinds();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ModKeyBinds.POWER_KEY.wasPressed()) {
                if (client.player != null) {
                    PlayerTraits traits = MyComponents.TRAITS.get(client.player);
                    traits.setActivate(!traits.getActivate());
                    client.player.sendMessage(Text.literal("Você usou seu poder!" + traits.getActivate()), false);
                }
            }
            while (ModKeyBinds.POWER_KEY2.wasPressed()) {
                if (client.player != null) {

                    client.player.sendMessage(Text.literal("Você usou seu poder secundario!"), false);

                }
            }
        });
    }

    @Override
    public void onInitializeClient() {

    }
}
