package dev.joaq.ancestralpowers.powers;

import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.registry.ModEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

// PowerBase.java
public abstract class PowerBase implements Power {

    protected abstract float staminaCost();
    protected abstract String ActivationType();
    protected abstract void disablePowerSpecific(ServerPlayerEntity player);

    protected void disablePower(PlayerTraits traits, String powerType, ServerPlayerEntity player) {
        switch (powerType) {
            case "SuperPower" -> {
                traits.setActPower_main(false);
                traits.setActPower_secondary(false);
            }
            case "Main" -> traits.setActPower_main(false);
            case "Secondary" -> traits.setActPower_secondary(false);
            case "Specific" -> disablePowerSpecific(player);
        }
    }

    protected void spendStamina(PlayerTraits traits, float cost) {
        traits.setStamina(Math.max(0, traits.getStamina() - cost));
    }

    protected boolean canActivate(ServerPlayerEntity player, boolean activate,
                                  PlayerTraits traits, String activateType, String powerType) {
        if ("PRESS".equals(activateType) && !activate) return false;

        if (player.hasStatusEffect(ModEffects.POWER_SUPPRESSION)) {
            player.sendMessage(Text.literal("§cSeus poderes foram suprimidos!"), true);
            disablePower(traits, powerType, player);
            return false;
        }

        if (traits.getStamina() < staminaCost()) {
            player.sendMessage(Text.literal("§eVocê está cansado demais para usar esse poder!"), true);
            disablePower(traits, powerType, player);
            return false;
        }

        return true;
    }

    protected abstract void executeLogic(ServerPlayerEntity player, boolean activate, float stamina);

    @Override
    public void reset(ServerPlayerEntity player) {}

    public final void execute(ServerPlayerEntity player, boolean activate,
                              String activateType, PlayerTraits traits, String powerType, boolean customActivate) {

        if (!"PRESS-PERSISTENT".equals(activateType)) return;

        // Verifica se pode ativar (stamina, efeitos, etc)
        if (!canActivate(player, activate, traits, activateType, powerType)) {
            traits.setActPower_main(false);
            traits.setActPower_secondary(false);
            return;
        }

        if (activate) {
            executeLogic(player, true, traits.getStamina());

            if (customActivate && traits.getStamina() >= staminaCost()) {
                spendStamina(traits, staminaCost());
            } else if (!customActivate) {
                return;
            } else if (traits.getStamina() < staminaCost()) {
                player.sendMessage(Text.literal("§eVocê está cansado demais para usar esse poder!"), true);
                disablePower(traits, powerType, player);
            }

            // Reset dos flags para permitir novo clique
            traits.setActPower_main(false);
            traits.setActPower_secondary(false);
        } else {
            if (customActivate && traits.getStamina() >= staminaCost()) {
                spendStamina(traits, staminaCost());
            } else if (!customActivate) {
                return;
            } else if (traits.getStamina() < staminaCost()) {
                player.sendMessage(Text.literal("§eVocê está cansado demais para usar esse poder!"), true);
                disablePower(traits, powerType, player);
            }

        }
    }

    public final void execute(ServerPlayerEntity player, boolean activate,
                              String activateType, PlayerTraits traits, String powerType) {

        if (!canActivate(player, activate, traits, activateType, powerType)) return;

        switch (activateType) {
            case "PRESS" -> {
                executeLogic(player, activate, traits.getStamina());
                spendStamina(traits, staminaCost());
                disablePower(traits, powerType, player);
            }
            case "TOGGLE", "HOLD" -> {
                if (activate) {
                    executeLogic(player, true, traits.getStamina());
                    spendStamina(traits, staminaCost());
                } else {
                    disablePower(traits, powerType, player);
                }
            }
        }
    }
}
