package dev.joaq.ancestralpowers.powers;

import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.registry.ModEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Objects;

public abstract class PowerBase implements Power {

    protected abstract float staminaCost();

    protected abstract String ActivationType();


    protected void disablePower(PlayerTraits traits, String powerType) {
        switch (powerType) {
            case "SuperPower" -> {
                traits.setActPower_main(false);
                traits.setActPower_secondary(false);
            }
            case "Main" -> traits.setActPower_main(false);
            case "Secondary" -> traits.setActPower_secondary(false);
        }
    }

    protected void spendStamina(PlayerTraits traits, float cost) {
        traits.setStamina(Math.max(0, traits.getStamina() - cost));
    }

    /**
     * Checa condições antes de ativar o poder
     */
    protected boolean canActivate(ServerPlayerEntity player, boolean activate,
                                  PlayerTraits traits, String activateType, String powerType) {
        // PRESS -> só executa no clique
        if (Objects.equals(activateType, "PRESS") && !activate) {
            return false;
        }

        // Efeito de supressão
        if (player.hasStatusEffect(ModEffects.POWER_SUPPRESSION)) {
            player.sendMessage(Text.literal("§cSeus poderes foram suprimidos!"), true);
            disablePower(traits, powerType);
            return false;
        }

        // Stamina insuficiente
        if (traits.getStamina() < staminaCost()) {
            player.sendMessage(Text.literal("§eVocê está cansado demais para usar esse poder!"), true);
            disablePower(traits, powerType);
            return false;
        }

        return true; // passou nos checks
    }

    protected abstract void executeLogic(ServerPlayerEntity player, boolean activate, float stamina);

    @Override
    public void reset(ServerPlayerEntity player) {}


    public final void execute(ServerPlayerEntity player, boolean activate,
                              String activateType, PlayerTraits traits, String powerType) {

        if (!canActivate(player, activate, traits, activateType, powerType)) return;

        switch (activateType) {
            case "PRESS" -> {
                executeLogic(player, activate, traits.getStamina());
                spendStamina(traits, staminaCost());
                disablePower(traits, powerType);
            }
            case "TOGGLE" -> {
                if (activate) {
                    executeLogic(player, true, traits.getStamina());
                    spendStamina(traits, staminaCost());
                } else {
                    disablePower(traits, powerType);
                }
            }
            case "HOLD" -> {
                //a
                if (activate) {
                    executeLogic(player, true, traits.getStamina());
                    spendStamina(traits, staminaCost());
                } else {
                    disablePower(traits, powerType);
                }
            }
        }
    }
}
