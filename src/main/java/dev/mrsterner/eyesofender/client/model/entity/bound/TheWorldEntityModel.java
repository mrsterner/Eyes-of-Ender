package dev.mrsterner.eyesofender.client.model.entity.bound;

import dev.mrsterner.eyesofender.client.model.entity.StandEntityModel;
import dev.mrsterner.eyesofender.common.entity.BaseStandEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class TheWorldEntityModel extends StandEntityModel {
    @Override
    public Identifier getModelResource(BaseStandEntity object) {
        return super.getModelResource(object);
    }

    @Override
    public Identifier getTextureResource(BaseStandEntity object) {
        return super.getTextureResource(object);
    }

    @Override
    public void setLivingAnimations(BaseStandEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
