package dev.mrsterner.eyesofender.common.ability.hamon;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class HamonRepellingAbility extends HamonKnowledge {
	public HamonRepellingAbility() {
		super(
				EyesOfEnder.id("hamon_repelling"),
				Hamon.INTERMEDIATE,
				HamonRepellingAbility::walkingOnWater,
				0,
				null,
				20,
				HamonAbilityType.PASSIVE,
				0);
	}
	private static boolean walkingOnWater(LivingEntity livingEntity) {
		World world = livingEntity.getWorld();
		BlockPos blockPos = new BlockPos(livingEntity.getEyePos());
		return livingEntity.isTouchingWater() && world.getBlockState(blockPos).isOf(Blocks.AIR);
	}

}
