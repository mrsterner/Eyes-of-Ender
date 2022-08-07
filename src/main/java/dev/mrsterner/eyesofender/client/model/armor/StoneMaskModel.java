package dev.mrsterner.eyesofender.client.model.armor;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.item.StoneMaskItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class StoneMaskModel extends AnimatedGeoModel<StoneMaskItem> {

	@Override
	public Identifier getModelResource(StoneMaskItem object) {
		return EyesOfEnder.id("geo/stone_mask.geo.json");
	}

	@Override
	public Identifier getTextureResource(StoneMaskItem object) {
		var nbt = object.getDefaultStack().getNbt();
		return EyesOfEnder.id(nbt != null && nbt.contains("Bloody") ? "textures/item/stone_mask_bloody.png" : "textures/item/stone_mask.png");
	}

	@Override
	public Identifier getAnimationResource(StoneMaskItem animatable) {
		return EyesOfEnder.id("animations/stone_mask.animation.json");
	}
}
