package dev.mrsterner.eyesofender.api.interfaces;

import dev.mrsterner.eyesofender.api.registry.AbilityEffect;
import dev.mrsterner.eyesofender.common.ability.Ability;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AbilityUser {
	static Optional<AbilityUser> of(Object context) {
		if (context instanceof AbilityUser) {
			return Optional.of(((AbilityUser) context));
		}
		return Optional.empty();
	}


	int getMaxAbilities();

	void setMaxAbilities(int amount);

	List<Ability> getAbilities();

	Set<AbilityEffect> getLearnedEffects();

	void learnEffect(AbilityEffect effect);

	int getAbilityCooldown();

	void setAbilityCooldown(int ticks);

	void syncAbilityData();
}
