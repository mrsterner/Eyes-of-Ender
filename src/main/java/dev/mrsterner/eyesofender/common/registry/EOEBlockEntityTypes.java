package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.block.entity.CoffinBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class EOEBlockEntityTypes {
	private static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

	public static final BlockEntityType<CoffinBlockEntity> COFFIN_BLOCK_ENTITY = register("coffin_block_entity", FabricBlockEntityTypeBuilder.create(CoffinBlockEntity::new, EOEObjects.COFFIN).build(null));


	private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
		BLOCK_ENTITY_TYPES.put(type, EyesOfEnder.id(name));
		return type;
	}



	public static void init() {
		BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registry.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
	}
}
