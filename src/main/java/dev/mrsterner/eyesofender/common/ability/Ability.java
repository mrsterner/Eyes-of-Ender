package dev.mrsterner.eyesofender.common.ability;

import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.api.registry.HamonAbility;
import dev.mrsterner.eyesofender.common.networking.packet.AbilityPacket;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class Ability {
	public HamonAbility hamonAbility;
	public int hamonLevel;

	public Ability(HamonAbility hamonAbility, int hamonLevel) {
		this.hamonAbility = hamonAbility;
		this.hamonLevel = hamonLevel;
	}

	public boolean use(LivingEntity user) {
		Optional<AbilityUser> abilityUserOptional = AbilityUser.of(user);
		if (!user.world.isClient && abilityUserOptional.isPresent()) {
			AbilityPacket.send(user, toTag(new NbtCompound()));
		}
		return hamonAbility.canCast(user);
	}

	public NbtCompound toTag(NbtCompound tag) {
		tag.putString("HamonAbility", hamonAbility.getId().toString());
		tag.putInt("hamonLevel", hamonLevel);
		return tag;
	}

	public static Ability fromTag(NbtCompound tag) {
		return tag.isEmpty() ? null : new Ability(
				EOERegistries.HAMON_ABILITY.get(new Identifier(tag.getString("HamonAbility"))),
				tag.getInt("hamonLevel"));
	}
}
