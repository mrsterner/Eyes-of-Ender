package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class EOEEntityTypes {
	private static final Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();




	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		ENTITY_TYPES.put(type, EyesOfEnder.id(name));
		return type;
	}

	public static void init() {
		ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registry.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
	}
}
