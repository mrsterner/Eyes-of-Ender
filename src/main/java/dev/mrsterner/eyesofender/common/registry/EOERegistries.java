package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.passive.CatType;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class EOERegistries {

	public static final RegistryKey<Registry<HamonKnowledge>> HAMON_ABILITY_TYPE = createRegistryKey("hamon_ability_type");
	public static final Registry<HamonKnowledge> HAMON_ABILITY = FabricRegistryBuilder.createSimple(HamonKnowledge.class, EyesOfEnder.id("hamon_abilities")).buildAndRegister();
	public static final TagKey<HamonKnowledge> HAMON_KNOWLEDGE_TAG = register("hamon_knowledge_tag");


	private static TagKey<HamonKnowledge> register(String string) {
		return TagKey.of(HAMON_ABILITY_TYPE, new Identifier(string));
	}

	private static <T> RegistryKey<Registry<T>> createRegistryKey(String registryId) {
		return RegistryKey.ofRegistry(new Identifier(registryId));
	}

	public static void init(){

	}
}
