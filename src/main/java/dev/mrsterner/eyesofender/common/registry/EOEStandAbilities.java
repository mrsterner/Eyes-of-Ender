package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.StandAbility;
import dev.mrsterner.eyesofender.common.ability.stand.theworld.TimeStopStandAbility;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class EOEStandAbilities {
    private static final List<StandAbility> STAND_ABILITIES = new ArrayList<>();

    //The World
    public static final StandAbility TIME_STOP = register(new TimeStopStandAbility(EyesOfEnder.id("time_stop")));

    private static StandAbility register(StandAbility effect) {
        STAND_ABILITIES.add(effect);
        return effect;
    }

    public static void init(){
        STAND_ABILITIES.forEach(ability -> Registry.register(EOERegistries.STAND_ABILITY, ability.getId(), ability));
    }
}
