package dev.mrsterner.eyesofender.common.entity.hamon;

import com.mojang.serialization.Dynamic;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.entity.ai.HamonUserBrain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Set;

public class HamonUserEntity extends PathAwareEntity implements HamonUser, IAnimatable {
    final AnimationFactory factory = new AnimationFactory(this);
    public HamonUserEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("hamonUserBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        HamonUserBrain.updateActivities(this);
        super.mobTick();
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return HamonUserBrain.create(this, dynamic);
    }

    public Brain<HamonUserEntity> getBrain() {
        return (Brain<HamonUserEntity>) super.getBrain();
    }

    @Override
    public List<HamonAbility> getHamonAbilities() {
        return null;
    }

    @Override
    public Set<HamonKnowledge> getLearnedHamonKnowledge() {
        return null;
    }

    @Override
    public void learnHamonKnowledge(HamonKnowledge effect) {

    }

    @Override
    public int getHamonBreath() {
        return 0;
    }

    @Override
    public void setHamonBreath(int hamonBreath) {

    }

    @Override
    public Hamon getHamonLevel() {
        return null;
    }

    @Override
    public void setHamonLevel(Hamon h) {

    }

    @Override
    public void setHamonTimer(int amount) {

    }

    @Nullable
    @Override
    public void setStoredChargedHamon(HamonKnowledge hamonAbility) {

    }

    @Override
    public HamonKnowledge getStoredChargedHamon() {
        return null;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
