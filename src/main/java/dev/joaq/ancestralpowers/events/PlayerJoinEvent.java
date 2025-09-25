package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.IntComponent;
import dev.joaq.ancestralpowers.components.MyComponents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerJoinEvent {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            IntComponent comp = MyComponents.RANDOM_INT.get(player);

            int newValue = comp.getValue() + 1;
            comp.setValue(newValue);

            player.sendMessage(Text.literal("You have joined " + comp.getValue() + " times"), false);

        });


    }
}
