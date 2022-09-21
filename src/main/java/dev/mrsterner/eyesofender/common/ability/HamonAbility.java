package dev.mrsterner.eyesofender.common.ability;

import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;


public class HamonAbility {
	public HamonKnowledge hamonKnowledge;
	public int hamonLevel;


	public HamonAbility(HamonKnowledge hamonAbility, int hamonLevel) {
		this.hamonKnowledge = hamonAbility;
		this.hamonLevel = hamonLevel;
	}

	public boolean use(LivingEntity user) {
		if (!user.world.isClient && EOEComponents.HAMON_COMPONENT.maybeGet(user).isPresent()) {
			HamonAbilityPacket.send(user, toTag(new NbtCompound()));
		}
		return hamonKnowledge.canUse(user);
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
