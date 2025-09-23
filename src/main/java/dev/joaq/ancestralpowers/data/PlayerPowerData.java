package dev.joaq.ancestralpowers.data;
import dev.joaq.ancestralpowers.powerMechanics.AncestralPower;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class PlayerPowerData {

    private static final Map<ServerPlayerEntity, String> powers = new HashMap<>();

    public static void setPower(ServerPlayerEntity player, AncestralPower power) {
        powers.put(player, power.name());
    }

    public static AncestralPower getPower(ServerPlayerEntity player) {
        String powerName = powers.get(player);
        if (powerName == null) return null;
        return AncestralPower.valueOf(powerName);
    }

    public static boolean hasPower(ServerPlayerEntity player) {
        return powers.containsKey(player);
    }
}
