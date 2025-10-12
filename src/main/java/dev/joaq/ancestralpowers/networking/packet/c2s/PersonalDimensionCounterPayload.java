package dev.joaq.ancestralpowers.networking.packet.c2s;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static dev.joaq.ancestralpowers.AncestralPowers.MOD_ID;

public record PersonalDimensionCounterPayload(Integer totalDirtBlocksBroken) implements CustomPayload {
    public static final Identifier PERSONAL_DIMENSION_COUNTER_ID = Identifier.of(MOD_ID, "dirt_broken");
    public static final CustomPayload.Id<PersonalDimensionCounterPayload> ID = new CustomPayload.Id<>(PERSONAL_DIMENSION_COUNTER_ID);
    public static final PacketCodec<PacketByteBuf, PersonalDimensionCounterPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, PersonalDimensionCounterPayload::totalDirtBlocksBroken,
            PersonalDimensionCounterPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
