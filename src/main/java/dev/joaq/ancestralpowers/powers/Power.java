package dev.joaq.ancestralpowers.powers;

import net.minecraft.server.network.ServerPlayerEntity;

public interface Power {
    void apply(ServerPlayerEntity player, boolean activate, float stamina);

    void reset(ServerPlayerEntity player);

}

