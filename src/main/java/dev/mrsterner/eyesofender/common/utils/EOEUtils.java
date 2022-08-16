package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class EOEUtils {
	public static class DataTrackers{
		public static final TrackedData<Integer> MAX_ABILITIES = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
		public static final TrackedData<Integer> ABILITY_COOLDOWN = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}
	public static class Tags {
		public static final TagKey<EntityType<?>> HUMANOIDS = TagKey.of(Registry.ENTITY_TYPE_KEY, EyesOfEnder.id("humanoid"));

	}
	public static class Nbt {
		public static final String MAX_ABILITIES = "MaxAbilities";
		public static final String ABILITY_COOLDOWN = "AbilityCooldown";
		public static final String HAMON_BREATH = "HamonBreath";
		public static final String ABILITY_LIST = "AbilityList";

	}


	public static boolean canHamonBreath(LivingEntity livingEntity){
		int y = livingEntity.getMaxAir();
		int z = Math.min(livingEntity.getAir(), y);
		return !(livingEntity.isSubmergedIn(FluidTags.WATER) || z < y || livingEntity.isFreezing());
	}

	public static NbtCompound getTagCompoundSafe(ItemStack stack) {
		NbtCompound tagCompound = stack.getNbt();
		if (tagCompound == null) {
			tagCompound = new NbtCompound();
			stack.setNbt(tagCompound);
		}
		return tagCompound;
	}
}
