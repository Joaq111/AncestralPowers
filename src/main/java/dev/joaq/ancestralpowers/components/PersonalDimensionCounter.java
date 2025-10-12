package dev.joaq.ancestralpowers.components;

import com.mojang.serialization.Codec;
import dev.joaq.ancestralpowers.AncestralPowers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;


public class PersonalDimensionCounter extends PersistentState {
    public int personalDimensionCount = 0;

    public PersonalDimensionCounter() {}

    public PersonalDimensionCounter(int personalDimensionCount) {
        this.personalDimensionCount = personalDimensionCount;
    }

    public int getCount() {
        return personalDimensionCount;
    }

    public int incrementAndGet() {
        personalDimensionCount++;
        markDirty();
        return personalDimensionCount;
    }

    public static PersonalDimensionCounter getServerState(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);
        assert serverWorld != null;
        PersonalDimensionCounter state = serverWorld.getPersistentStateManager().getOrCreate(
                new PersistentStateType<>(
                        AncestralPowers.MOD_ID,
                        PersonalDimensionCounter::new,
                        Codec.INT.fieldOf("personalDimensionCount").codec().xmap(PersonalDimensionCounter::new, PersonalDimensionCounter::getCount),
                        null
                )
        );
        return state;
    }
}
