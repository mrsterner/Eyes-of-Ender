package dev.mrsterner.eyesofender.client.model.entity;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.entity.stand.bound.HierophantGreenEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HierophantGreenEntityModel extends AnimatedGeoModel<HierophantGreenEntity> {
    @Override
    public Identifier getModelResource(HierophantGreenEntity object) {
        return EyesOfEnder.id("geo/hierophant_green.geo.json");
    }

    @Override
    public Identifier getTextureResource(HierophantGreenEntity object) {
        return EyesOfEnder.id("textures/entity/stand/hierophant_green_0.png");
    }

    @Override
    public Identifier getAnimationResource(HierophantGreenEntity animatable) {
        return EyesOfEnder.id("animations/hierophant_green.animation.json");
    }
}
