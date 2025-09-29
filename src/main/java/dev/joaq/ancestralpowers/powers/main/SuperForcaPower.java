package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SuperForcaPower implements Power {

    private static final Identifier SUPER_FORCA_ID = Identifier.of("ancestralpowers", "super_forca");

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr == null) return;

        // Remove modifier existente antes de aplicar
        EntityAttributeModifier existing = attackAttr.getModifier(SUPER_FORCA_ID);
        if (existing != null) attackAttr.removeModifier(existing);

        if (!activate) return;

        attackAttr.addPersistentModifier(new EntityAttributeModifier(
                SUPER_FORCA_ID,
                100.0,
                EntityAttributeModifier.Operation.ADD_VALUE
        ));
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        EntityAttributeInstance attackAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);
        if (attackAttr == null) return;

        EntityAttributeModifier existing = attackAttr.getModifier(SUPER_FORCA_ID);
        if (existing != null) attackAttr.removeModifier(existing);
    }
}
