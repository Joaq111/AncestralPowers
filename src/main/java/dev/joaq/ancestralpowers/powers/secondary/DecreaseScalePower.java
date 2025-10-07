package dev.joaq.ancestralpowers.powers.secondary;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class DecreaseScalePower extends PowerBase {

    private static final Identifier SCALE_ID = Identifier.of("ancestralpowers", "scale");
    private static final Identifier MOVEMENT_SPEED_ID = Identifier.of("ancestralpowers", "move_speed");
    private static final Identifier JUMP_STRENGTH_ID = Identifier.of("ancestralpowers", "jump_strength");

    private static final double INCREMENT = -0.25;
    private static final double MIN_SCALE = 0.5;
    private static final double MAX_SCALE = 16.0;
    private static final double EPS = 0.001;

    private void removeModifier(EntityAttributeInstance attr, Identifier id) {
        if (attr == null) return;
        EntityAttributeModifier existing = attr.getModifier(id);
        if (existing != null) attr.removeModifier(existing);
    }

    @Override
    protected float staminaCost() {
        return 0.25f;
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
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance scaleAttr = player.getAttributeInstance(EntityAttributes.SCALE);
        EntityAttributeInstance speedAttr = player.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
        EntityAttributeInstance jumpAttr = player.getAttributeInstance(EntityAttributes.JUMP_STRENGTH);
        if (scaleAttr == null || speedAttr == null || jumpAttr == null) return;

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        double currentScale = traits.getScaleMultiplier();

        double next = currentScale + INCREMENT;
        next = Math.max(MIN_SCALE, Math.min(MAX_SCALE, next));

        if (Math.abs(next - 1.0) < EPS) {
            removeModifier(scaleAttr, SCALE_ID);
            removeModifier(speedAttr, MOVEMENT_SPEED_ID);
            removeModifier(jumpAttr, JUMP_STRENGTH_ID);
            traits.setScaleMultiplier(1.0);
            return;
        }

        scaleAttr.removeModifier(SCALE_ID);
        scaleAttr.addPersistentModifier(new EntityAttributeModifier(
                SCALE_ID, next - 1.0, EntityAttributeModifier.Operation.ADD_VALUE
        ));

        speedAttr.removeModifier(MOVEMENT_SPEED_ID);
        speedAttr.addPersistentModifier(new EntityAttributeModifier(
                MOVEMENT_SPEED_ID, next / 10.0 - 0.1, EntityAttributeModifier.Operation.ADD_VALUE
        ));

        jumpAttr.removeModifier(JUMP_STRENGTH_ID);
        jumpAttr.addPersistentModifier(new EntityAttributeModifier(
                JUMP_STRENGTH_ID, next - 1.0, EntityAttributeModifier.Operation.ADD_VALUE
        ));

        traits.setScaleMultiplier(next);
    }

    protected boolean customIsActive(ServerPlayerEntity player) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        return Math.abs(traits.getScaleMultiplier() - 1.0) > EPS;
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific", customIsActive(player));
    }
    @Override
    public void reset(ServerPlayerEntity player) {
        disablePowerSpecific(player);
    }

}
