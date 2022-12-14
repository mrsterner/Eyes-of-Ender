package dev.mrsterner.eyesofender.common.components.entity;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;


public class HamonComponent implements ServerTickingComponent, AutoSyncedComponent, HamonUser {
    private final LivingEntity entity;
    private final World world;

    private final List<HamonAbility> abilities = new ArrayList<>();
    private final Set<HamonKnowledge> learnedKnowledge = new HashSet<>();
    private Hamon hamonLevel = Hamon.BASIC; //TODO change to EMPTY
    private HamonKnowledge storedHamonCharge = null;

    private int MAX_HAMON_ABILITIES = 10;
    private int MAX_ABILITY_COOLDOWN = 20 * 2;
    public final int MAX_HAMON_BREATH = 20 * 100;
    private int hamonBreath = MAX_HAMON_BREATH;
    private int hamonTimer = 0;

    public HamonComponent(LivingEntity entity) {
        this.entity = entity;
        this.world = entity.getWorld();
    }


    @Override
    public void serverTick() {
            if(!world.isClient()){
                if(HamonAbilityClientHandler.selectedHamonAbility != null){
                    //Tick the passive hamon ability if it is a passive ability and decrease the breath for that ability
                    if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonAbilityType() == HamonAbilityType.PASSIVE && getHamonBreath() > HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonDrain()){
                        HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.tickPassiveAbility(entity);
                        decreaseHamonBreath(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonDrain());
                    }

                    //Runs onAbilityRemoved when timer hits 0 for TIMED abilities
                    if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonAbilityType() == HamonAbilityType.TIMED){
                        if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonTimer() > hamonTimer){
                            setHamonTimer(getHamonTimer() + 1);
                        }else {
                            HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.onAbilityRemoved(entity);
                            HamonAbilityClientHandler.selectedHamonAbility = null;
                            setHamonTimer(0);
                        }
                        //Runs onAbilityRemoved when timer hits 0 for CROUCH abilities, entity has to be sneaking
                    }else if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonAbilityType() == HamonAbilityType.CROUCH && entity.isSneaking()){
                        if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonTimer() > hamonTimer){
                            setHamonTimer(getHamonTimer() + 1);
                        }else {
                            HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.onAbilityRemoved(entity);
                            HamonAbilityClientHandler.selectedHamonAbility = null;
                            setHamonTimer(0);
                        }
                    }

                    //Decrease HamonBreath if the user cant breath
                    if(!EOEUtils.canHamonBreath(entity)){
                        decreaseHamonBreath(20);
                    }

                    //TODO remove just for testing
                    if(entity.isSneaking()){
                        increaseHamonBreath(10);
                    }else{
                        decreaseHamonBreath(10);
                    }


                    //On removed
                    if(getHamonBreath() == 0){
                        HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.onAbilityRemoved(entity);
                        HamonAbilityClientHandler.selectedHamonAbility = null;
                    }
                }
            }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setMaxHamonAbilities(tag.getInt(EOEUtils.Nbt.MAX_ABILITIES));
        setHamonAbilityCooldown(tag.getInt(EOEUtils.Nbt.ABILITY_COOLDOWN));
        setHamonBreath(tag.getInt(EOEUtils.Nbt.HAMON_BREATH));
        setHamonTimer(tag.getInt(EOEUtils.Nbt.HAMON_TIMER));


        getHamonAbilities().clear();
        for (int i = 0; i < tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).size(); i++) {
            getHamonAbilities().add(i, HamonAbility.fromTag((NbtCompound) tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).get(i)));
        }

        if(tag.getString(EOEUtils.Nbt.STORED_CHARGED_HAMON) != null){
            setStoredChargedHamon(new HamonAbility(EOERegistries.HAMON_ABILITY.get(new Identifier(tag.getString(EOEUtils.Nbt.STORED_CHARGED_HAMON)))).hamonKnowledge);
        }
        if(tag.getString(EOEUtils.Nbt.HAMON_LEVEL) != null){
            setHamonLevel(Hamon.valueOf(tag.getString(EOEUtils.Nbt.HAMON_LEVEL)));
        }

        learnedKnowledge.clear();
        for (int i = 0; i < tag.getList(EOEUtils.Nbt.HAMON_KNOWLEDGE, 10).size(); i++) {
            HamonAbility hamonAbility = HamonAbility.fromTag((NbtCompound) tag.getList(EOEUtils.Nbt.HAMON_KNOWLEDGE, 10).get(i));
            if(hamonAbility != null){
                learnHamonKnowledge(hamonAbility.hamonKnowledge);
            }
        }

    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(EOEUtils.Nbt.MAX_ABILITIES, getMaxHamonAbilities());
        tag.putInt(EOEUtils.Nbt.ABILITY_COOLDOWN, getHamonAbilityCooldown());
        tag.putInt(EOEUtils.Nbt.HAMON_BREATH, getHamonBreath());
        tag.putInt(EOEUtils.Nbt.HAMON_TIMER, getHamonTimer());

        NbtList abilities = new NbtList();
        getHamonAbilities().forEach((ability) -> abilities.add(ability == null ? new NbtCompound() : ability.toTag(new NbtCompound())));
        tag.put(EOEUtils.Nbt.ABILITY_LIST, abilities);

        NbtList knowledgeList = new NbtList();
        getLearnedHamonKnowledge().forEach(hamonKnowledge -> knowledgeList.add(hamonKnowledge == null ? new NbtCompound() : hamonKnowledge.toTag(new NbtCompound())));
        tag.put(EOEUtils.Nbt.HAMON_KNOWLEDGE, knowledgeList);

        tag.putString(EOEUtils.Nbt.HAMON_LEVEL, hamonLevel.toString());
        tag.putString(EOEUtils.Nbt.STORED_CHARGED_HAMON, storedHamonCharge.getId().toString());
    }



    public int getMaxHamonAbilities() {
        return this.MAX_HAMON_ABILITIES;
    }

    public void setMaxHamonAbilities(int amount) {
        if(amount >= 0 && amount <= EOERegistries.HAMON_ABILITY.size()){
            this.MAX_HAMON_ABILITIES = amount;
            EOEComponents.HAMON_COMPONENT.sync(entity);
        }
    }

    public List<HamonAbility> getHamonAbilities() {
        return abilities;
    }

    public Set<HamonKnowledge> getLearnedHamonKnowledge() {
        return learnedKnowledge;
    }

    public void learnHamonKnowledge(HamonKnowledge effect) {
        if(effect.getHamonTier().getValue() >= getHamonLevel().getValue()){
            learnedKnowledge.add(effect);
            EOEComponents.HAMON_COMPONENT.sync(entity);
        }
    }

    public int getHamonAbilityCooldown() {
        return this.MAX_ABILITY_COOLDOWN;
    }

    public void setHamonAbilityCooldown(int ticks) {
        this.MAX_ABILITY_COOLDOWN = ticks;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }


    public int getHamonBreath() {
        return hamonBreath;
    }

    public void setHamonBreath(int hamonBreath) {
        this.hamonBreath = hamonBreath;
    }


    public void increaseHamonBreath(int increaseAmount){
        for(int buffer = increaseAmount; getHamonBreath() < MAX_HAMON_BREATH && buffer > 0; buffer--){
            setHamonBreath(getHamonBreath() + 1);
        }
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }

    public void decreaseHamonBreath(int decreaseAmount){
        for(int buffer = decreaseAmount; getHamonBreath() > 0 && buffer > 0; buffer--){
            setHamonBreath(getHamonBreath() - 1);
        }
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }


    public Hamon getHamonLevel() {
        return hamonLevel;
    }

    public void setHamonLevel(Hamon h) {
        if(hamonLevel.getNext(hamonLevel) == h){
            this.hamonLevel = h;
            EOEComponents.HAMON_COMPONENT.sync(entity);
        }
    }

    public void setHamonTimer(int amount){
        this.hamonTimer = amount;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }

    public int getHamonTimer(){
       return this.hamonTimer;
    }

    @Nullable
    public void setStoredChargedHamon(HamonKnowledge hamonAbility){
        this.storedHamonCharge = hamonAbility;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }

    public HamonKnowledge getStoredChargedHamon(){
        return storedHamonCharge;
    }
}
