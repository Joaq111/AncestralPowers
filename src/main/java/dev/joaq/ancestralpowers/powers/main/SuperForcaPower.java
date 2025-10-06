package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SuperForcaPower extends PowerBase {

    private static final Identifier SUPER_FORCA_ID = Identifier.of("ancestralpowers", "super_forca");

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

    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr == null) return;

        EntityAttributeModifier existing = attackAttr.getModifier(SUPER_FORCA_ID);
        if (existing != null) attackAttr.removeModifier(existing);

        attackAttr.addPersistentModifier(new EntityAttributeModifier(
                SUPER_FORCA_ID,
                10.0,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Main");
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr == null) return;

        EntityAttributeModifier existing = attackAttr.getModifier(SUPER_FORCA_ID);
        if (existing != null) attackAttr.removeModifier(existing);
    }
}
