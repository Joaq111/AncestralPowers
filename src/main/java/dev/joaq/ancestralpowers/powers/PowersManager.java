package dev.joaq.ancestralpowers.powers;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.main.*;
import dev.joaq.ancestralpowers.powers.secondary.SuperTeleporteSecondaryPower;
import dev.joaq.ancestralpowers.powers.secondary.VelocidadePower;
import dev.joaq.ancestralpowers.powers.secondary.VooPower;
import dev.joaq.ancestralpowers.powers.teleport.SuperTeleportePower;
import net.minecraft.server.network.ServerPlayerEntity;

public class PowersManager {


    private static Power getPower(String power) {
        return switch (power) {
            case "Super Força" -> new SuperForcaPower();
            case "SuperTeleporte" -> new SuperTeleportePower();
            case "Fireball" -> new FireballPower();
            default -> null;
        };
    }
    private static Power getPowerSecondary(String power) {
        return switch (power) {
            case "SuperTeleporte" -> new SuperTeleporteSecondaryPower();
            case "Voo" -> new VooPower();
            case "Velocidade" -> new VelocidadePower();
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
                new SuperTeleportePower(),
                new FireballPower(),
                new VooPower(),
                new VelocidadePower()
        };
        for (Power p : powers) {
            if (p != null) p.reset(player);
        }

    }


}
