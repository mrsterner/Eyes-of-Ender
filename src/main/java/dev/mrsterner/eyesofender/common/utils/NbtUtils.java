package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOEHamonAbilities;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

public class NbtUtils {
	public static NbtCompound writeAbilityData(HamonUser user, NbtCompound tag) {
		NbtList abilities = new NbtList();
		user.getHamonAbilities().forEach((ability) -> abilities.add(ability == null ? new NbtCompound() : ability.toTag(new NbtCompound())));
		tag.put(EOEUtils.Nbt.ABILITY_LIST, abilities);
		NbtList effects = new NbtList();
		user.getLearnedHamonKnowledge().forEach((effect) -> effects.add(NbtString.of(effect.getId().toString())));
		tag.put("Effect", effects);
		return tag;
	}

	public static void readAbilityData(HamonUser user, NbtCompound tag) {
		user.getHamonAbilities().clear();
		for (int i = 0; i < tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).size(); i++) {
			user.getHamonAbilities().add(i, HamonAbility.fromTag((NbtCompound) tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).get(i)));
		}
		addDefaultAbilities(user);
	}
	private static void addDefaultAbilities(HamonUser user) {
		user.getLearnedHamonKnowledge().add(EOEHamonAbilities.ACTIVATE);
		user.getLearnedHamonKnowledge().add(EOEHamonAbilities.OVERDRIVE);
	}
}
