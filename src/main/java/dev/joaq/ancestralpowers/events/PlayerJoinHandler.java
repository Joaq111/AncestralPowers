package dev.joaq.ancestralpowers.events;
import dev.joaq.ancestralpowers.data.PlayerPowerData;
import dev.joaq.ancestralpowers.powerMechanics.AncestralPower;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class PlayerJoinHandler {
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            if (!PlayerPowerData.hasPower(player)) {
                AncestralPower randomPower = AncestralPower.values()[player.getRandom().nextInt(AncestralPower.values().length)];
                PlayerPowerData.setPower(player, randomPower);

                player.sendMessage(Text.literal("You were born with the power of: " + randomPower.name()), false);
            }
        });
    }
}