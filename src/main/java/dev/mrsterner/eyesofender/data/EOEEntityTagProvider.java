package dev.mrsterner.eyesofender.data;

import dev.mrsterner.eyesofender.common.registry.EOETags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;

public class EOEEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public EOEEntityTagProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(EOETags.HUMANOIDS).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.WITCH);
	}
}
