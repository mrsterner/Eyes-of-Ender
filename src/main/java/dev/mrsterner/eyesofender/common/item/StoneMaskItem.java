package dev.mrsterner.eyesofender.common.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class StoneMaskItem extends ArmorItem implements IAnimatable {
	private final AnimationFactory factory = new AnimationFactory(this);

	public StoneMaskItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		AnimationBuilder builder = new AnimationBuilder();
		builder.addAnimation("animation.stonemask.idle", true);
		event.getController().setAnimation(builder);
		return PlayState.STOP;
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		animationData.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
