package dev.joaq.ancestralpowers.events;

import dev.joaq.ancestralpowers.components.MyComponents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class ComponentEvents {

    public static void register() {
        // Exemplo: copiar o valor quando o player respawna
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            int oldValue = MyComponents.RANDOM_INT.get(oldPlayer).getValue();
            MyComponents.RANDOM_INT.get(newPlayer).setValue(oldValue);
        });
    }
}
