package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonAbility;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

public class EOERegistries {

	public static final Registry<HamonAbility> HAMON_ABILITY = FabricRegistryBuilder.createSimple(HamonAbility.class, EyesOfEnder.id("hamon_abilities")).buildAndRegister();
}
