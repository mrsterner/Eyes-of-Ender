package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.AbilityEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

public class EOERegistries {

	public static final Registry<AbilityEffect> HAMON_ABILITY = FabricRegistryBuilder.createSimple(AbilityEffect.class, EyesOfEnder.id("hamon_abilities")).buildAndRegister();
}
