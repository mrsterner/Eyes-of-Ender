package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class EOETags {
	public static final TagKey<EntityType<?>> HUMANOIDS = TagKey.of(Registry.ENTITY_TYPE_KEY, EyesOfEnder.id("humanoid"));

}
