package dev.joaq.ancestralpowers.structureplacement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.joaq.ancestralpowers.ancestralpowersStructurePlacements;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.chunk.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.gen.chunk.placement.SpreadType;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class DistanceBasedStructurePlacement extends RandomSpreadStructurePlacement {

    public static final MapCodec<DistanceBasedStructurePlacement> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Vec3i.createOffsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(DistanceBasedStructurePlacement::getLocateOffset),
            StructurePlacement.FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", StructurePlacement.FrequencyReductionMethod.DEFAULT).forGetter(DistanceBasedStructurePlacement::getFrequencyReductionMethod),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(DistanceBasedStructurePlacement::getFrequency),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("salt").forGetter(DistanceBasedStructurePlacement::getSalt),
            StructurePlacement.ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(DistanceBasedStructurePlacement::getExclusionZone),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spacing").forGetter(DistanceBasedStructurePlacement::getSpacing),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(DistanceBasedStructurePlacement::getSeparation),
            SpreadType.CODEC.optionalFieldOf("spread_type", SpreadType.LINEAR).forGetter(DistanceBasedStructurePlacement::getSpreadType),
            Codec.intRange(0, Integer.MAX_VALUE).optionalFieldOf("min_distance_from_world_origin").forGetter(DistanceBasedStructurePlacement::minDistanceFromWorldOrigin)
    ).apply(instance, DistanceBasedStructurePlacement::new));

    private final Optional<Integer> minDistanceFromWorldOrigin;

    public DistanceBasedStructurePlacement(Vec3i locationOffset,
                                           StructurePlacement.FrequencyReductionMethod frequencyReductionMethod,
                                           float frequency,
                                           int salt,
                                           Optional<ExclusionZone> exclusionZone,
                                           int spacing,
                                           int separation,
                                           SpreadType spreadType,
                                           Optional<Integer> minDistanceFromWorldOrigin
    ) {
        super(locationOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
        this.minDistanceFromWorldOrigin = minDistanceFromWorldOrigin;

        if (spacing <= separation) {
            throw new RuntimeException("""
                Spacing cannot be less or equal to separation.
                Please correct this error as there's no way to spawn this structure properly
                    Spacing: %s
                    Separation: %s.
            """.formatted(spacing, separation));
        }
    }

    public Optional<Integer> minDistanceFromWorldOrigin() {
        return this.minDistanceFromWorldOrigin;
    }

    @Override
    protected boolean isStartChunk(StructurePlacementCalculator structurePlacementCalculator, int x, int z) {
        if (minDistanceFromWorldOrigin.isPresent()) {
            long xBlockPos = x * 16L;
            long zBlockPos = z * 16L;

            if ((xBlockPos * xBlockPos) + (zBlockPos * zBlockPos) < (((long) minDistanceFromWorldOrigin.get()) * minDistanceFromWorldOrigin.get())) {
                return false;
            }
        }

        ChunkPos chunkpos = this.getStartChunk(structurePlacementCalculator.getStructureSeed(), x, z);
        return chunkpos.x == x && chunkpos.z == z;
    }

    @Override
    public StructurePlacementType<?> getType() {
        return ancestralpowersStructurePlacements.DISTANCE_BASED_STRUCTURE_PLACEMENT;
    }
}
