package dev.joaq.ancestralpowers.powers;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.main.*;
import dev.joaq.ancestralpowers.powers.secondary.*;
import net.minecraft.server.network.ServerPlayerEntity;

public class PowersManager {


    private static Power getPower(String power) {
        return switch (power) {
            case "Super Força" -> new SuperForcaPower();
            case "SuperTeleporte" -> new SuperTeleporteMainPower();
            case "Suppressor" -> new SupressionPower();
            case "SuperSpeed" -> new SuperSpeedPower();
            case "Scale" -> new IncreaseScalePower();
            case "Fireball" -> new FireballPower();
            case "Imortalidade" -> new ImortalPower();
            default -> null;
        };
    }
    private static Power getPowerSecondary(String power) {
        return switch (power) {
            case "SuperTeleporte" -> new SuperTeleporteSecondaryPower();
            case "Scale" -> new DecreaseScalePower();
            case "SuperSpeed" -> new SuperSpeedPowerDecrease();
            case "Voo" -> new VooPower();
            case "Velocidade" -> new VelocidadePower();
            case "Teleporte" -> new TelepotePower();

            default -> null;
        };
    }

    public static void applyMainPower(ServerPlayerEntity player, String power, boolean activate, float stamina) {
        Power p = getPower(power);
        if (p != null) p.apply(player, activate, stamina);
    }


    public static void applyMovementPower(ServerPlayerEntity player, String power, boolean activate, float stamina) {
        Power p = getPowerSecondary(power);
        if (p != null) p.apply(player, activate, stamina);
    }

    public static void applyIntelligence(ServerPlayerEntity player, String power) {
        switch(power) {
            case "Burro":
                // nada especial
                break;
            case "Inteligente":
                // mais XP, exemplo
                break;
            case "Gênio":
                // XP ainda mais, talvez loot bonus
                break;
        }
    }


    public static void resetAll(ServerPlayerEntity player) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        traits.setActPower_main(false);
        traits.setActPower_secondary(false);

        Power[] powers = {
                new SuperForcaPower(),
                new SuperSpeedPower(),
                new SuperSpeedPowerDecrease(),
                new SuperTeleporteMainPower(),
                new SuperTeleporteSecondaryPower(),
                new SupressionPower(),
                new FireballPower(),
                new VooPower(),
                new VelocidadePower(),
                new IncreaseScalePower(),
                new DecreaseScalePower(),
                new TelepotePower()

        };
        for (Power p : powers) {
            if (p != null) p.reset(player);
        }

    }


}
