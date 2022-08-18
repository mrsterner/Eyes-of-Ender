package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.api.registry.CyborgKnowledge;
import dev.mrsterner.eyesofender.common.ability.cyborg.ChargeCyborgAbility;
import net.minecraft.util.registry.Registry;

public class EOECyborgAbilities {
    //Basic
    public static final CyborgKnowledge CHARGE = new ChargeCyborgAbility();

    public static void init(){
        register(CHARGE);
    }

    private static void register(CyborgKnowledge ability) {
        Registry.register(EOERegistries.CYBORG_ABILITY, ability.getId(), ability);
    }
}
