package dev.joaq.ancestralpowers.networking.packet.c2s;

import dev.joaq.ancestralpowers.AncestralPowers;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record UseActivePowerTypesC2SPacket(List<Identifier> powerIds) implements CustomPayload {

    public static final Id<UseActivePowerTypesC2SPacket> PACKET_ID = new Id<>(AncestralPowers.identifier("use_active_power_types"));
    public static final PacketCodec<RegistryByteBuf, UseActivePowerTypesC2SPacket> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.collection(ArrayList::new, Identifier.PACKET_CODEC), UseActivePowerTypesC2SPacket::powerIds,
            UseActivePowerTypesC2SPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
