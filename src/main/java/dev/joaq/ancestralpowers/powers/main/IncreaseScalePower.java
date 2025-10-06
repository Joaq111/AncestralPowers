package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class IncreaseScalePower extends PowerBase {

    private static final Identifier SCALE_ID = Identifier.of("ancestralpowers", "scale");
    private static final Identifier MOVEMENT_SPEED_ID = Identifier.of("ancestralpowers", "move_speed");
    private static final Identifier JUMP_STRENGTH_ID = Identifier.of("ancestralpowers", "jump_strength");
    private static final double INCREMENT = 0.25;

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
        EntityAttributeInstance scaleAttr = player.getAttributeInstance(EntityAttributes.SCALE);
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance jumpAttr = player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);

        removeModifier(scaleAttr, SCALE_ID);
        removeModifier(speedAttr, MOVEMENT_SPEED_ID);
        removeModifier(jumpAttr, JUMP_STRENGTH_ID);

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        traits.setScaleMultiplier(1.0);
        traits.setActPower_main(false);
        traits.setActPower_secondary(false);
    }

    @Override
    protected boolean executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance scaleAttr = player.getAttributeInstance(EntityAttributes.SCALE);
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance jumpAttr = player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);

        if (scaleAttr == null || speedAttr == null || jumpAttr == null) return false;

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        double currentScale = traits.getScaleMultiplier();
        currentScale += INCREMENT;

        if (currentScale > 16) currentScale = 16;
        traits.setScaleMultiplier(currentScale);

        // Scale
        scaleAttr.removeModifier(SCALE_ID);
        scaleAttr.addPersistentModifier(new EntityAttributeModifier(
                SCALE_ID,
                currentScale - 1,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));

        // Speed
        speedAttr.removeModifier(MOVEMENT_SPEED_ID);
        speedAttr.addPersistentModifier(new EntityAttributeModifier(
                MOVEMENT_SPEED_ID,
                currentScale / 10 - 0.1,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));

        // Jump Strength
        jumpAttr.removeModifier(JUMP_STRENGTH_ID);
        jumpAttr.addPersistentModifier(new EntityAttributeModifier(
                JUMP_STRENGTH_ID,
                currentScale - 1,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));
        return true;
    }

    protected boolean customIsActive(ServerPlayerEntity player) {
        EntityAttributeInstance scaleAttr = player.getAttributeInstance(EntityAttributes.SCALE);
        return scaleAttr != null && scaleAttr.getModifier(SCALE_ID) != null;
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific", customIsActive(player));
    }
}
