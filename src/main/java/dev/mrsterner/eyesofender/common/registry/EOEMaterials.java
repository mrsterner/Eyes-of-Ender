package dev.mrsterner.eyesofender.common.registry;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class EOEMaterials {
	public static final ArmorMaterial VAMPIRE_ARMOR = new ArmorMaterial() {
		public int getDurability(EquipmentSlot slot) {
			return ArmorMaterials.CHAIN.getDurability(slot);
		}

		public int getProtectionAmount(EquipmentSlot slot) {
			return ArmorMaterials.CHAIN.getProtectionAmount(slot);
		}

		public int getEnchantability() {
			return ArmorMaterials.GOLD.getEnchantability();
		}

		public SoundEvent getEquipSound() {
			return ArmorMaterials.LEATHER.getEquipSound();
		}

		public Ingredient getRepairIngredient() {
			return null;
		}



		public String getName() {
			return "vampirearmor";
		}

		public float getToughness() {
			return ArmorMaterials.CHAIN.getToughness();
		}

		public float getKnockbackResistance() {
			return ArmorMaterials.CHAIN.getKnockbackResistance();
		}
	};
}
