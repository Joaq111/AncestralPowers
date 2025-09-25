package dev.joaq.ancestralpowers.components;

import dev.joaq.ancestralpowers.AncestralPowers;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MyComponents implements EntityComponentInitializer {

    public static final ComponentKey<IntComponent> RANDOM_INT =
            ComponentRegistryV3.INSTANCE.getOrCreate(
                    Identifier.of(AncestralPowers.MOD_ID, "random_int"), IntComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(RANDOM_INT, player -> new RandomIntComponent() {

        }, RespawnCopyStrategy.ALWAYS_COPY);
    }
}