package dev.mrsterner.eyesofender.api.interfaces;

import dev.mrsterner.eyesofender.common.ability.Ability;

import java.util.List;
import java.util.Optional;

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

	int getAbilityCooldown();

	void setAbilityCooldown(int ticks);

}
