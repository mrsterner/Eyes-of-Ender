package dev.mrsterner.eyesofender.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EOEDataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(EOEBlockTagProvider::new);
		fabricDataGenerator.addProvider(EOERecipeProvider::new);
		fabricDataGenerator.addProvider(EOEBlockLootTableProvider::new);
		fabricDataGenerator.addProvider(EOEModelProvider::new);
		fabricDataGenerator.addProvider(
				new EOEItemTagProvider(fabricDataGenerator,
						fabricDataGenerator.addProvider(EOEBlockTagProvider::new)));
	}
}
