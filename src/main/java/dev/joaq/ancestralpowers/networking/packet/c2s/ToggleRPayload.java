package dev.joaq.ancestralpowers.networking.packet.c2s;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ToggleRPayload(boolean value) implements CustomPayload {
    public static final Identifier ID = Identifier.of("ancestralpowers", "toggle_r");
    public static final Id<ToggleRPayload> PAYLOAD_ID = new Id<>(ID);
    public static final PacketCodec<RegistryByteBuf, ToggleRPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOLEAN, ToggleRPayload::value, ToggleRPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}
