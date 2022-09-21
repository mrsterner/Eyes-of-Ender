package dev.mrsterner.eyesofender.common.components.entity;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.client.gui.HamonAbilityClientHandler;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HamonComponent implements ServerTickingComponent, AutoSyncedComponent {
    private final LivingEntity entity;
    private final World world;
    private int MAX_HAMON_ABILITIES = 10;
    private int MAX_ABILITY_COOLDOWN = 20 * 2;
    private List<HamonAbility> abilities = new ArrayList<>();
    private Set<HamonKnowledge> learnedKnowledge = new HashSet<>();
    private int hamonBreath = 20 * 100; //TODO change to 0
    private Hamon hamonLevel = Hamon.BASIC; //TODO change to EMPTY

    public HamonComponent(LivingEntity entity) {
        this.entity = entity;
        this.world = entity.getWorld();
    }


    @Override
    public void serverTick() {
        if(!world.isClient()){
            if(HamonAbilityClientHandler.selectedHamonAbility != null){
                if(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.isPassive() && getHamonBreath() > HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonDrain()){
                    HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.tickAbility(entity);
                    decreaseHamonBreath(HamonAbilityClientHandler.selectedHamonAbility.hamonKnowledge.getHamonDrain());
                }
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        setMaxHamonAbilities(tag.getInt(EOEUtils.Nbt.MAX_ABILITIES));
        setHamonAbilityCooldown(tag.getInt(EOEUtils.Nbt.ABILITY_COOLDOWN));
        setHamonBreath(tag.getInt(EOEUtils.Nbt.HAMON_BREATH));

        getHamonAbilities().clear();
        for (int i = 0; i < tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).size(); i++) {
            getHamonAbilities().add(i, HamonAbility.fromTag((NbtCompound) tag.getList(EOEUtils.Nbt.ABILITY_LIST, 10).get(i)));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(EOEUtils.Nbt.MAX_ABILITIES, getMaxHamonAbilities());
        tag.putInt(EOEUtils.Nbt.ABILITY_COOLDOWN, getHamonAbilityCooldown());
        tag.putInt(EOEUtils.Nbt.HAMON_BREATH, getHamonBreath());

        NbtList abilities = new NbtList();
        getHamonAbilities().forEach((ability) -> abilities.add(ability == null ? new NbtCompound() : ability.toTag(new NbtCompound())));
        tag.put(EOEUtils.Nbt.ABILITY_LIST, abilities);
    }

    public int getMaxHamonAbilities() {
        return this.MAX_HAMON_ABILITIES;
    }

    public void setMaxHamonAbilities(int amount) {
        this.MAX_HAMON_ABILITIES = amount;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }

    public List<HamonAbility> getHamonAbilities() {
        return abilities;
    }

    public Set<HamonKnowledge> getLearnedHamonKnowledge() {
        return learnedKnowledge;
    }

    public void learnHamonKnowledge(HamonKnowledge effect) {
        learnedKnowledge.add(effect);
        EOEComponents.HAMON_COMPONENT.sync(entity);
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

    public void setHamonBreath(int amount) {
        this.hamonBreath = getHamonBreath() + amount;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }

    public void increaseHamonBreath(int amount){
        setHamonBreath(getHamonBreath() + amount);
    }

    public void decreaseHamonBreath(int amount){
        setHamonBreath(getHamonBreath() - amount);
    }

    public Hamon getHamonLevel() {
        return hamonLevel;
    }

    public void setHamonLevel(Hamon hamonLevel) {
        this.hamonLevel = hamonLevel;
        EOEComponents.HAMON_COMPONENT.sync(entity);
    }
}
