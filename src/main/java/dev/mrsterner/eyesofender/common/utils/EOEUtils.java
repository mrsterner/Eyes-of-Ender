package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.common.components.entity.BodyComponent;
import dev.mrsterner.eyesofender.common.components.entity.HamonComponent;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import dev.mrsterner.eyesofender.common.registry.EOEStatusEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtType;
import net.minecraft.nbt.scanner.NbtScanner;
import net.minecraft.nbt.visitor.NbtElementVisitor;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedHashSet;

public class EOEUtils {
    public static float[] shouldHamonGlow(LivingEntity livingEntity) {
		boolean bl = livingEntity.hasStatusEffect(EOEStatusEffects.HAMON_IMBUE);

		float[] color = null;
		HamonComponent hamonComponent = EOEComponents.HAMON_COMPONENT.maybeGet(livingEntity).orElse(null);
		if(hamonComponent != null && HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getColor() != 0){
			color = getColorFromInt(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getColor());
		}

		return bl ? new float[]{0.5f ,0.35f ,0} : color;
    }



	public static float[] getColorFromInt(int i){
		return new float[]{i & 255, (i >> 8) & 255, (i >> 16) & 255};
	}

    public static class DataTrackers{

	}
	public static class Tags {
		public static final TagKey<EntityType<?>> HUMANOIDS = TagKey.of(Registry.ENTITY_TYPE_KEY, EyesOfEnder.id("humanoid"));
		public static final TagKey<HamonKnowledge> HAMON_AURA = TagKey.of(EOERegistries.HAMON_ABILITY_TYPE, EyesOfEnder.id("hamon_aura"));//TODO

	}
	public static class Nbt {
		public static final String MAX_ABILITIES = "MaxAbilities";
		public static final String ABILITY_COOLDOWN = "AbilityCooldown";
		public static final String HAMON_BREATH = "HamonBreath";
		public static final String ABILITY_LIST = "AbilityList";
        public static final String STORED_CHARGED_HAMON = "StoredChargedHamon";
		public static final String HAMON_LEVEL = "HamonLevel";
		public static final String HAMON_KNOWLEDGE = "HamonKnowledge";
		public static final String HAMON_TIMER = "HamonTimer";
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

	public static Vec3d getYawRelativePos(Vec3d originPos, double distance, float yaw, float pitch) {
		float yawRadians = yaw * (float) Math.toRadians(1);
		float pitchRadians = pitch * (float) Math.toRadians(1);
		double x = distance * (-MathHelper.sin(yawRadians) * MathHelper.cos(pitchRadians));
		double y = distance * -MathHelper.sin(pitchRadians);
		double z = distance * (MathHelper.cos(yawRadians) * MathHelper.cos(pitchRadians));
		return originPos.add(x, y, z);
	}

	public static Vec3d getYawRelativePosRelatively(double distance, float yaw, float pitch) {
		float yawRadians = yaw * (float) Math.toRadians(1);
		float pitchRadians = pitch * (float) Math.toRadians(1);
		double x = distance * (-MathHelper.sin(yawRadians) * MathHelper.cos(pitchRadians));
		double y = distance * -MathHelper.sin(pitchRadians);
		double z = distance * (MathHelper.cos(yawRadians) * MathHelper.cos(pitchRadians));
		return new Vec3d(x, y, z);
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
