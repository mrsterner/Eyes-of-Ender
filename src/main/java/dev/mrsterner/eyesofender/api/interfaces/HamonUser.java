package dev.mrsterner.eyesofender.api.interfaces;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HamonUser {
	static Optional<HamonUser> of(Object context) {
		if (context instanceof HamonUser) {
			return Optional.of(((HamonUser) context));
		}
		return Optional.empty();
	}


	int getMaxHamonAbilities();

	void setMaxHamonAbilities(int amount);

	List<HamonAbility> getHamonAbilities();

	Set<HamonKnowledge> getLearnedHamonKnowledge();

	void learnHamonKnowledge(HamonKnowledge effect);

	int getHamonAbilityCooldown();

	void setHamonAbilityCooldown(int ticks);

	void syncHamonAbilityData();

	int getHamonBreath();

	void setHamonBreath(int amount);

	Hamon getHamonLevel();

	void setHamonLevel(Hamon hamonLevel);

}
