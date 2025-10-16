package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.dimensions.PersonalDimensionStructure;
import dev.joaq.ancestralpowers.powers.PowerBase;
import dev.joaq.ancestralpowers.util.PlayerUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PersonalDimensionPower extends PowerBase {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Secondary");
    }

    @Override
    protected float staminaCost() {
        return 60;
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
        ServerPlayerEntity target = null;

        if (player.isSneaking()) {
            target = PlayerUtils.getPlayerLookedAt(player, 20.0D);
            if (target != null) {
                player.sendMessage(Text.literal("§aVocê e " + target.getName().getString() + " serão teleportados juntos!"), false);
            } else {
                player.sendMessage(Text.literal("§eNenhum jogador na linha de visão. Apenas você será teleportado."), false);
            }
        }

        PersonalDimensionStructure.teleportToPersonalDimension(player, target);
    }
}
