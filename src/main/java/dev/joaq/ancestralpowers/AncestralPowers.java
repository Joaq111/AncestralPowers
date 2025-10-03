package dev.joaq.ancestralpowers;

import dev.joaq.ancestralpowers.commands.ModCommands;
import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.events.PlayerJoinEvent;
import dev.joaq.ancestralpowers.events.PlayerPowersTickHandler;
import dev.joaq.ancestralpowers.networking.ModPacketsC2S;
import dev.joaq.ancestralpowers.registry.ModEffects;
import dev.joaq.ancestralpowers.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, amount) -> {
            if (entity instanceof ServerPlayerEntity serverPlayer) {
                PlayerTraits traits = MyComponents.TRAITS.get(serverPlayer);

                // só impede a morte se o jogador tiver Imortalidade ativa
                if ("Imortalidade".equals(traits.getMainPower()) && traits.getActPower_main()) {
                    // opcional: gasta stamina
                    // traits.setStamina(traits.getStamina() - 0.5f);

                    serverPlayer.sendMessage(Text.literal("Você foi salvo pela imortalidade!"));
                    return false; // impede a morte
                }
            }
            return true; // deixa morrer normalmente
        });
        System.out.println("ancestralpowers: AncestralPowers inicializado!");
        PlayerJoinEvent.register();
        PlayerPowersTickHandler.register();
        ModCommands.register();
        ModPacketsC2S.register();
        ModEntities.register();
        ModEffects.register();
    }

    @Override
    public void onInitializeClient() {

    }

}
