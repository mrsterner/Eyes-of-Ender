package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.common.components.entity.BodyComponent;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashSet;

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
	public static class Identifiers {
		public static final Identifier OVERLAY_OVERDRIVE = EyesOfEnder.id("textures/gui/ability_widgets/ability/overlay_overdrive.png");
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

	public static boolean ifMissingArmsLegsTorso(PlayerEntity player){
		return !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.TORSO) &&
				!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTARM) &&
				!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTARM) &&
				!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTLEG) &&
				!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTLEG);
	}

	public static boolean ifMissingLegs(PlayerEntity player){
		return !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTLEG) &&
				!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTLEG);
	}

	private static LinkedHashSet<Integer> disabledSlots = new LinkedHashSet<>();

	public static boolean isDisabled(int slot) {
		return disabledSlots.contains(slot);
	}

	public static void lockSlot(int slot) {
		disabledSlots.add(slot);
	}

	public static void unlockSlot(int slot) {
		disabledSlots.remove(slot);
	}

	public static void removeBodyPart(PlayerEntity player, BodyPart bodyParts){
		BodyComponent bodyComponent = EOEComponents.BODY_COMPONENT.get(player);
		bodyComponent.setBodyPart(bodyParts, false, bodyComponent.getBodyPartLevel(bodyParts));
		switch (bodyParts){
			case EYES -> {}
			case LEFTLEG -> {}
			case RIGHTLEG -> {}
			case LEFTARM -> {
				player.dropItem(player.getOffHandStack(), false);
				lockSlot(40);
			}
			case RIGHTARM -> {player.dropItem(player.getMainHandStack(), false);}
			case TORSO -> {}
			case HEAD -> {}
		}
	}
}
