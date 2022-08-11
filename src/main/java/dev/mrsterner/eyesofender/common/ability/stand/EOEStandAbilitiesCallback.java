package dev.mrsterner.eyesofender.common.ability.stand;

import dev.mrsterner.eyesofender.common.ability.stand.killerqueen.KillerQueenBlockTagCallback;
import dev.mrsterner.eyesofender.common.ability.stand.killerqueen.KillerQueenEntityTagCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class EOEStandAbilitiesCallback {
    public static void init(){
        AttackEntityCallback.EVENT.register(KillerQueenEntityTagCallback::tagEntity);
        AttackBlockCallback.EVENT.register(KillerQueenBlockTagCallback::tagBlock);
    }
}
