package dev.mrsterner.eyesofender.api.interfaces.stand;

import dev.mrsterner.eyesofender.api.enums.Part;
import dev.mrsterner.eyesofender.api.registry.StandAbility;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public interface IStand {
    LivingEntity getOwner();

    void setOwner(LivingEntity livingEntity);

    default int getAct(){
        return 0;
    }

    default void setAct(){

    }

    List<StandAbility> getStandAbilities();

    void setStandAbilities();

    Part getPart();
}
