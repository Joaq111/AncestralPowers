package dev.joaq.ancestralpowers.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PowerSuppressionEffect extends StatusEffect {
    public PowerSuppressionEffect() {
        super(StatusEffectCategory.HARMFUL, 0x550088);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
}
