package dev.mrsterner.eyesofender.common.utils;

import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;

public class EOEUtils {
	public static final TrackedData<Integer> MAX_ABILITIES = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final TrackedData<Integer> ABILITY_COOLDOWN = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	public static final int MIN_ABILITIES = 3;
}
