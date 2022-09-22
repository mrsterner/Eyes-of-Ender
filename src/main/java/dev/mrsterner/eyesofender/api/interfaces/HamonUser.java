package dev.mrsterner.eyesofender.api.interfaces;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public interface HamonUser {
    int hamonTimer = 0;

    List<HamonAbility> getHamonAbilities();

    Set<HamonKnowledge> getLearnedHamonKnowledge();

    void learnHamonKnowledge(HamonKnowledge effect);

    int getHamonBreath();

    void setHamonBreath(int hamonBreath);

    Hamon getHamonLevel();

    void setHamonLevel(Hamon h);

    void setHamonTimer(int amount);

    default int getHamonTimer(){
        return this.hamonTimer;
    }


    @Nullable
    void setStoredChargedHamon(HamonKnowledge hamonAbility);

    HamonKnowledge getStoredChargedHamon();
}
