package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.PowerBase;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class StrengthPower extends PowerBase {

    private static final Identifier ATTACK_DAMAGE_ID = Identifier.of("ancestralpowers", "attack_damage");


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
        EntityAttributeInstance attackDamageAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);

        removeModifier(attackDamageAttr, ATTACK_DAMAGE_ID);

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        traits.setActPower_main(false);
    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        EntityAttributeInstance attackDamageAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE);

        if (attackDamageAttr == null ) return;


        removeModifier(attackDamageAttr, ATTACK_DAMAGE_ID);


        addModifier(attackDamageAttr, ATTACK_DAMAGE_ID, 3);

    }

    private void removeModifier(EntityAttributeInstance attr, Identifier id) {
        if (attr == null) return;
        EntityAttributeModifier existing = attr.getModifier(id);
        if (existing != null) attr.removeModifier(existing);
    }

    private void addModifier(EntityAttributeInstance attr, Identifier id, double value) {
        attr.addPersistentModifier(new EntityAttributeModifier(id, value, EntityAttributeModifier.Operation.ADD_VALUE));
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific");
    }

    @Override
    public void reset(ServerPlayerEntity player) {

        EntityAttributeInstance attackDamageAttr = player.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE   );


        removeModifier(attackDamageAttr, ATTACK_DAMAGE_ID);

    }
}
