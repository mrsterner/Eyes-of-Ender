package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class HealingAbility extends HamonKnowledge {
	public HealingAbility() {
		super(EyesOfEnder.id("healing"),
				Hamon.BASIC,
				null,
				0,
				null,
				20,
				HamonAbilityType.PASSIVE);
	}

	@Override
	public void tickAbility(LivingEntity entity) {
		//TODO maybe change this to a playerability for resistance
		if(entity.canHaveStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE))){
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 2));
		}
		//TODO add particle effect of green plus signs and regen health
	}
}
