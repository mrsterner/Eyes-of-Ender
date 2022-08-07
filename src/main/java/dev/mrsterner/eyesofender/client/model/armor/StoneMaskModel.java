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
		return EyesOfEnder.id("textures/item/stone_mask.png");
	}

	@Override
	public Identifier getAnimationResource(StoneMaskItem animatable) {
		return EyesOfEnder.id("animations/stone_mask.animation.json");
	}
}
