package dev.mrsterner.eyesofender.common.ability.stand.theworld;

import dev.mrsterner.eyesofender.api.registry.StandAbility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TimeStopStandAbility extends StandAbility {
    public TimeStopStandAbility(Identifier id) {
        super(id);
    }

    @Override
    public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
        //Code
        super.useAbility(world, user, pos);
    }



    @Override
    public void onAbilityRemoved(LivingEntity user) {
        super.onAbilityRemoved(user);
    }
}
