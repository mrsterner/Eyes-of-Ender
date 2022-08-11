package dev.mrsterner.eyesofender.common.ability;

import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class HamonAbility {
	public HamonKnowledge hamonKnowledge;
	public int hamonLevel;


	public HamonAbility(HamonKnowledge hamonAbility, int hamonLevel) {
		this.hamonKnowledge = hamonAbility;
		this.hamonLevel = hamonLevel;
	}

	public boolean use(LivingEntity user) {
		Optional<HamonUser> abilityUserOptional = HamonUser.of(user);
		if (!user.world.isClient && abilityUserOptional.isPresent()) {
			HamonAbilityPacket.send(user, toTag(new NbtCompound()));
		}
		return hamonKnowledge.canCast(user);
	}

	public NbtCompound toTag(NbtCompound tag) {
		tag.putString("HamonKnowledge", hamonKnowledge.getId().toString());
		tag.putInt("HamonLevel", hamonLevel);
		return tag;
	}

	public static HamonAbility fromTag(NbtCompound tag) {
		return tag.isEmpty() ? null : new HamonAbility(
				EOERegistries.HAMON_ABILITY.get(new Identifier(tag.getString("HamonKnowledge"))),
				tag.getInt("HamonLevel"));
	}
}
