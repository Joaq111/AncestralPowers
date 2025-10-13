package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class SuperTeleportMainPower extends PowerBase {


    @Override
    protected float staminaCost() {
        return 15;
    }

    @Override
    protected String ActivationType() {
        return "PRESS";
    }

    @Override
    protected void disablePowerSpecific(ServerPlayerEntity player) {

    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        if (!traits.getActPower_secondary()) {
            traits.setActPower_main(false);
            traits.setStamina(traits.getStamina()+15);
            return;
        }
        Vec3d targetPos = traits.getTeleportTarget();
        if (targetPos == null) {
            player.sendMessage(Text.literal("⚠ Defina um alvo com o poder secundário antes de teleportar!"), false);
            traits.setActPower_main(false);
            return;
        }

        player.teleport(targetPos.x, targetPos.y, targetPos.z, true);
        player.fallDistance = 0;
        traits.clearTeleportTarget();
    }


    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "SuperPower");
    }

}
