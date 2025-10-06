package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class VelocidadePower extends PowerBase {

    private static final Identifier MOVEMENT_SPEED_ID = Identifier.of("ancestralpowers", "super_speed");
    private static final Identifier ATTACK_SPEED_ID = Identifier.of("ancestralpowers", "super_attack_speed");
    private static final Identifier SUBMERGED_SPEED_ID = Identifier.of("ancestralpowers", "super_submerged_speed");
    private static final Identifier BLOCK_SPEED_ID = Identifier.of("ancestralpowers", "super_block_speed");
    private static final Identifier SNEAKING_SPEED_ID = Identifier.of("ancestralpowers", "super_sneaking_speed");

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
        // Nada específico além do reset padrão
    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance movementSpeedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(EntityAttributes.ATTACK_SPEED);
        EntityAttributeInstance submergedSpeedAttr = player.getAttributeInstance(EntityAttributes.SUBMERGED_MINING_SPEED);
        EntityAttributeInstance blockSpeedAttr = player.getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);
        EntityAttributeInstance sneakingSpeedAttr = player.getAttributeInstance(EntityAttributes.SNEAKING_SPEED);

        // Sempre remove os modifiers antes
        removeModifier(movementSpeedAttr, MOVEMENT_SPEED_ID);
        removeModifier(attackSpeedAttr, ATTACK_SPEED_ID);
        removeModifier(submergedSpeedAttr, SUBMERGED_SPEED_ID);
        removeModifier(blockSpeedAttr, BLOCK_SPEED_ID);
        removeModifier(sneakingSpeedAttr, SNEAKING_SPEED_ID);

        if (!activate) return;

        addModifier(movementSpeedAttr, MOVEMENT_SPEED_ID, 0.1);
        addModifier(attackSpeedAttr, ATTACK_SPEED_ID, 1.0);
        addModifier(submergedSpeedAttr, SUBMERGED_SPEED_ID, 0.25);
        addModifier(blockSpeedAttr, BLOCK_SPEED_ID, 0.5);
        addModifier(sneakingSpeedAttr, SNEAKING_SPEED_ID, 0.15);
    }

    private void removeModifier(EntityAttributeInstance attr, Identifier id) {
        if (attr == null) return;
        EntityAttributeModifier existing = attr.getModifier(id);
        if (existing != null) attr.removeModifier(existing);
    }

    private void addModifier(EntityAttributeInstance attr, Identifier id, double value) {
        if (attr == null) return;
        removeModifier(attr, id);
        attr.addPersistentModifier(new EntityAttributeModifier(id, value, EntityAttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Secondary");
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        EntityAttributeInstance movementSpeedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(EntityAttributes.ATTACK_SPEED);
        EntityAttributeInstance submergedSpeedAttr = player.getAttributeInstance(EntityAttributes.SUBMERGED_MINING_SPEED);
        EntityAttributeInstance blockSpeedAttr = player.getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);
        EntityAttributeInstance sneakingSpeedAttr = player.getAttributeInstance(EntityAttributes.SNEAKING_SPEED);

        removeModifier(movementSpeedAttr, MOVEMENT_SPEED_ID);
        removeModifier(attackSpeedAttr, ATTACK_SPEED_ID);
        removeModifier(submergedSpeedAttr, SUBMERGED_SPEED_ID);
        removeModifier(blockSpeedAttr, BLOCK_SPEED_ID);
        removeModifier(sneakingSpeedAttr, SNEAKING_SPEED_ID);
    }
}
