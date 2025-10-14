package dev.joaq.ancestralpowers.components;

import com.mojang.serialization.Codec;
import dev.joaq.ancestralpowers.AncestralPowers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

public class DimensionalArenaCounter extends PersistentState {
    public int dimensionalArenaCounter = 0;

    public DimensionalArenaCounter() {}

    public DimensionalArenaCounter(int dimensionalArenaCounter) {
        this.dimensionalArenaCounter = dimensionalArenaCounter;
    }

    public int getCount() {
        return dimensionalArenaCounter;
    }

    public int incrementAndGet() {
        dimensionalArenaCounter++;
        markDirty();
        return dimensionalArenaCounter;
    }

    public static DimensionalArenaCounter getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);
        assert serverWorld != null;
        DimensionalArenaCounter state = serverWorld.getPersistentStateManager().getOrCreate(
                new PersistentStateType<>(
                        AncestralPowers.MOD_ID,
                        DimensionalArenaCounter::new,
                        Codec.INT.fieldOf("dimensionalArenaCounter").codec().xmap(DimensionalArenaCounter::new, DimensionalArenaCounter::getCount),
                        null
                )
        );
        return state;
    }
}
