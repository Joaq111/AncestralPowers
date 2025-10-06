package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SuperSpeedPowerDecrease extends PowerBase {

    private static final Identifier MOVEMENT_SPEED_ID = Identifier.of("ancestralpowers", "move_speed");
    private static final Identifier ATTACK_SPEED_ID = Identifier.of("ancestralpowers", "super_attack_speed");
    private static final Identifier SUBMERGED_SPEED_ID = Identifier.of("ancestralpowers", "super_submerged_speed");
    private static final Identifier BLOCK_SPEED_ID = Identifier.of("ancestralpowers", "super_block_speed");

    private static final double INCREMENT = -0.25;

    private void removeModifier(EntityAttributeInstance attr, Identifier id) {
        if (attr == null) return;
        EntityAttributeModifier existing = attr.getModifier(id);
        if (existing != null) attr.removeModifier(existing);
    }

    @Override
    protected float staminaCost() {
        return 0.5f;
    }

    @Override
    protected String ActivationType() {
        return "PRESS-PERSISTENT";
    }

    @Override
    protected void disablePowerSpecific(ServerPlayerEntity player) {
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        removeModifier(speedAttr, MOVEMENT_SPEED_ID);

        EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(EntityAttributes.ATTACK_SPEED);
        removeModifier(attackSpeedAttr, ATTACK_SPEED_ID);

        EntityAttributeInstance submergedSpeedAttr = player.getAttributeInstance(EntityAttributes.SUBMERGED_MINING_SPEED);
        removeModifier(submergedSpeedAttr, SUBMERGED_SPEED_ID);

        EntityAttributeInstance blockSpeedAttr = player.getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);
        removeModifier(blockSpeedAttr, BLOCK_SPEED_ID);

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        traits.setScaleMultiplier(1.0);
        traits.setActPower_main(false);
        traits.setActPower_secondary(false);
    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance attackSpeedAttr = player.getAttributeInstance(EntityAttributes.ATTACK_SPEED);
        EntityAttributeInstance submergedSpeedAttr = player.getAttributeInstance(EntityAttributes.SUBMERGED_MINING_SPEED);
        EntityAttributeInstance blockSpeedAttr = player.getAttributeInstance(EntityAttributes.BLOCK_BREAK_SPEED);

        if (speedAttr == null || attackSpeedAttr == null || submergedSpeedAttr == null || blockSpeedAttr == null) return;

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        double currentScale = traits.getScaleMultiplier();
        currentScale += INCREMENT;

        if (currentScale > 1) currentScale = 1;
        traits.setScaleMultiplier(currentScale);

        speedAttr.removeModifier(MOVEMENT_SPEED_ID);
        speedAttr.addPersistentModifier(new EntityAttributeModifier(
                MOVEMENT_SPEED_ID,
                currentScale / 10 - 0.1,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));

        attackSpeedAttr.removeModifier(ATTACK_SPEED_ID);
        attackSpeedAttr.addPersistentModifier(new EntityAttributeModifier(
                ATTACK_SPEED_ID,
                currentScale / 4,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));

        submergedSpeedAttr.removeModifier(SUBMERGED_SPEED_ID);
        submergedSpeedAttr.addPersistentModifier(new EntityAttributeModifier(
                SUBMERGED_SPEED_ID,
                currentScale,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));

        blockSpeedAttr.removeModifier(BLOCK_SPEED_ID);
        blockSpeedAttr.addPersistentModifier(new EntityAttributeModifier(
                BLOCK_SPEED_ID,
                currentScale * 2,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));
    }

    protected boolean customIsActive(ServerPlayerEntity player) {
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        return speedAttr != null && speedAttr.getModifier(MOVEMENT_SPEED_ID) != null;
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific", customIsActive(player));
    }
}