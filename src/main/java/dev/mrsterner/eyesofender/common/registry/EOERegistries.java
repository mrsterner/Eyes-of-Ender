package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.CyborgKnowledge;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

public class EOERegistries {

	public static final Registry<HamonKnowledge> HAMON_ABILITY = FabricRegistryBuilder.createSimple(HamonKnowledge.class, EyesOfEnder.id("hamon_abilities")).buildAndRegister();
	public static final Registry<CyborgKnowledge> CYBORG_ABILITY = FabricRegistryBuilder.createSimple(CyborgKnowledge.class, EyesOfEnder.id("cyborg_abilities")).buildAndRegister();
}
