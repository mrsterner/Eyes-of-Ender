package dev.mrsterner.eyesofender.common.ability;

import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;

public class ActivateHamonAbility extends HamonAbility{
    public ActivateHamonAbility(HamonKnowledge hamonAbility, int hamonLevel) {
        super(hamonAbility, hamonLevel);
    }

    @Override
    public boolean use(LivingEntity user) {
        return super.use(user);
    }
}
