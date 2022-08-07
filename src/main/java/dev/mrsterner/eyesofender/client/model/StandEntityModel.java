package dev.mrsterner.eyesofender.client.model;

import dev.mrsterner.eyesofender.common.entity.StandEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class StandEntityModel extends AnimatedGeoModel<StandEntity> {
	public static Identifier MODEL = null;
	public static Identifier TEXTURE = null;
	public static Identifier ANIMATION = null;


	@Override
	public Identifier getModelResource(StandEntity object) {
		return MODEL;
	}

	@Override
	public Identifier getTextureResource(StandEntity object) {
		return TEXTURE;
	}

	@Override
	public Identifier getAnimationResource(StandEntity animatable) {
		return ANIMATION;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setLivingAnimations(StandEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationZ(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}
