package dev.mrsterner.eyesofender.data;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.Models;

public class EOEModelProvider extends FabricModelProvider {
	public EOEModelProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerBuiltin(EyesOfEnder.id("block/coffin"), Blocks.BLACKSTONE).includeWithoutItem(EOEObjects.COFFIN);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(EOEObjects.STAND_ARROW, Models.GENERATED);
		itemModelGenerator.register(EOEObjects.STONE_OF_AJA, Models.GENERATED);
		itemModelGenerator.register(EOEObjects.DAGGER, Models.HANDHELD);
		itemModelGenerator.register(EOEObjects.DEBUG_ITEM, Models.GENERATED);
		itemModelGenerator.register(EOEObjects.COFFIN.asItem(), Models.GENERATED);
	}


}
