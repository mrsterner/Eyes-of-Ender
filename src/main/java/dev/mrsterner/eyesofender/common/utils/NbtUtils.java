package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.common.ability.Ability;
import dev.mrsterner.eyesofender.common.registry.EOEAbilities;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

public class NbtUtils {
	public static NbtCompound writeAbilityData(AbilityUser user, NbtCompound tag) {
		NbtList abilities = new NbtList();
		user.getAbilities().forEach((ability) -> abilities.add(ability == null ? new NbtCompound() : ability.toTag(new NbtCompound())));
		tag.put("AbilityList", abilities);
		NbtList effects = new NbtList();
		user.getLearnedEffects().forEach((effect) -> effects.add(NbtString.of(effect.getId().toString())));
		tag.put("Effect", effects);
		return tag;
	}

	public static void readAbilityData(AbilityUser user, NbtCompound tag) {
		user.getAbilities().clear();
		for (int i = 0; i < tag.getList("AbilityList", 10).size(); i++) {
			user.getAbilities().add(i, Ability.fromTag((NbtCompound) tag.getList("AbilityList", 10).get(i)));
		}
		addDefaultAbilities(user);
	}
	private static void addDefaultAbilities(AbilityUser user) {
		user.getLearnedEffects().add(EOEAbilities.ACTIVATE);
		user.getLearnedEffects().add(EOEAbilities.OVERDRIVE);

	}
}
