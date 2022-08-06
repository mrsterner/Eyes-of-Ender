package dev.mrsterner.eyesofender.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import org.jetbrains.annotations.Nullable;

public class EOEItemTagProvider extends FabricTagProvider.ItemTagProvider{
	public EOEItemTagProvider(FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
		super(dataGenerator, blockTagProvider);
	}

	@Override
	protected void generateTags() {

	}
}
