package dev.mrsterner.eyesofender.api.registry;

import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOERegistries;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StandAbility {
    private final Identifier id;


    public StandAbility(Identifier id){
        this.id = id;
    }

    public static StandAbility fromTag(NbtCompound tag) {
        return tag.isEmpty() ? null : new StandAbility(EOERegistries.STAND_ABILITY.get(new Identifier(tag.getString(EOEUtils.Nbt.STAND_ABILITY))).getId());
    }


    public NbtCompound toTag(NbtCompound tag) {
        tag.putString(EOEUtils.Nbt.STAND_ABILITY, this.getId().toString());
        return tag;
    }

    public Identifier getId() {
        return id;
    }

    //Put extended super at end for proper effect
    public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
        onAbilityRemoved(user);
    }

    public void tickAbility(LivingEntity user) {

    }

    public void onAbilityRemoved(LivingEntity user){

    }
}
