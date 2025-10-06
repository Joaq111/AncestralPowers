package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.server.network.ServerPlayerEntity;

public class FlyPower extends PowerBase {

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific"); // <--- "Specific"
    }

    @Override
    protected float staminaCost() {
        return 0.5f;
    }

    @Override
    protected String ActivationType() {
        return "TOGGLE";
    }

    @Override
    protected void disablePowerSpecific(ServerPlayerEntity player) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        player.getAbilities().allowFlying = false;
        player.getAbilities().flying = false;
        player.sendAbilitiesUpdate();
        traits.setActPower_secondary(false);
    }

    @Override
    protected boolean executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        player.getAbilities().allowFlying = true;
        player.sendAbilitiesUpdate();
        return true;
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        player.getAbilities().allowFlying = false;
        player.getAbilities().flying = false;
        player.sendAbilitiesUpdate();
    }
}

