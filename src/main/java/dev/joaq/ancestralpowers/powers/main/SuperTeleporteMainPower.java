package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class SuperTeleporteMainPower implements Power {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        if (!activate) return;
        if(!traits.getActPower_secondary()) {
            traits.setActPower_main(false);
            return;
        }
        if (stamina < 25) {
            traits.setActPower_main(false);
            return;
        }

        Vec3d targetPos = traits.getTeleportTarget();
        if (targetPos == null) {
            player.sendMessage(Text.literal("⚠ Defina um alvo com o poder secundário antes de teleportar!"), true);
            traits.setActPower_main(false);
            return;
        }

        player.teleport(targetPos.x, targetPos.y, targetPos.z, true);
        player.fallDistance = 0;

        traits.setStamina(traits.getStamina() - 25);
        traits.setActPower_main(false);
        traits.setActPower_secondary(false);
        traits.clearTeleportTarget();
    }

    @Override
    public void reset(ServerPlayerEntity player) {}
}
