package dev.joaq.ancestralpowers.dimensions;

import dev.joaq.ancestralpowers.AncestralPowers;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDimensions {
    public static final RegistryKey<World> PERSONAL_WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, Identifier.of(AncestralPowers.MOD_ID, "personal_dimension"));

    public static void register() {
        AncestralPowers.LOGGER.info("ModDimensions registered (datapack dimension ancestralpowers:personal_dimension)");
    }
}
