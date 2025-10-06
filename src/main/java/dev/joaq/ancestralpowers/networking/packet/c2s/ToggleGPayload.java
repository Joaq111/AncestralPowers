package dev.joaq.ancestralpowers.networking.packet.c2s;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ToggleGPayload(boolean value) implements CustomPayload {
    public static final Identifier ID = Identifier.of("ancestralpowers", "toggle_g");
    public static final CustomPayload.Id<ToggleGPayload> PAYLOAD_ID = new CustomPayload.Id<>(ID);
    public static final PacketCodec<RegistryByteBuf, ToggleGPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.BOOLEAN, ToggleGPayload::value, ToggleGPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return PAYLOAD_ID;
    }
}

