package dev.joaq.ancestralpowers.powers;

import dev.joaq.ancestralpowers.AncestralPowers;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class PowersManager {

    private static final UUID SUPER_FORCA_UUID = UUID.fromString("5a9b4f8f-6b7d-4f1e-9bde-123456789abc");

    public static void applyMovementPower(ServerPlayerEntity player, String power) {
        switch(power) {
            case "Voo":
                player.getAbilities().allowFlying = true;
                player.sendAbilitiesUpdate();
                break;
            case "Teleporte":
                // aqui você pode dar um item de teleport ou um comando
                break;
            case "Velocidade":
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, 1, false, false));
                break;
        }


    }

    public static void applyMainPower(ServerPlayerEntity player, String power) {
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr == null) return;

        // Identificador do modifier
        Identifier superForcaId = Identifier.of("ancestralpowers", "super_forca");

        // Remove modifier antigo se existir
        EntityAttributeModifier existing = attackAttr.getModifier(superForcaId);
        if (existing != null) {
            attackAttr.removeModifier(existing);
        }

        switch (power) {
            case "Super Força":
                // Só adiciona se ainda não existir
                if (attackAttr.getModifier(superForcaId) == null) {
                    attackAttr.addPersistentModifier(new EntityAttributeModifier(
                            superForcaId, 100.0, EntityAttributeModifier.Operation.ADD_VALUE
                    ));
                }
                break;

            case "Imortalidade":
                // Aqui você pode adicionar lógica de imortalidade
                break;

            case "Fireball":
                // Aqui você pode adicionar lógica de fireball
                break;

            default:
                break;
        }
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

    public static void applyAll(ServerPlayerEntity player, PlayerTraits traits) {
        applyMovementPower(player, traits.getMovementPower());
        applyMainPower(player, traits.getMainPower());
        applyIntelligence(player, traits.getIntelligence());
    }
    public static void resetAll(ServerPlayerEntity player) {
        // --- Reset movimento ---
        player.getAbilities().allowFlying = false; // remove voo
        player.sendAbilitiesUpdate();

        player.removeStatusEffect(StatusEffects.SPEED); // remove velocidade

        // --- Reset ataque (remover super força) ---
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr != null) {
            Identifier superForcaId = Identifier.of("ancestralpowers", "super_forca");
            EntityAttributeModifier existing = attackAttr.getModifier(superForcaId);
            if (existing != null) {
                attackAttr.removeModifier(existing);
            }
        }

        // --- Reset inteligência ---
        // Aqui você pode remover efeitos/buffs relacionados a XP ou loot bonus se quiser.

        // Agora reaplica os poderes atuais do jogador com base nos trait
    }
}
