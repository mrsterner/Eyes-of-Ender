package dev.mrsterner.eyesofender.data;

import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;

public class EOEEntityTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public EOEEntityTagProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(EOEUtils.Tags.HUMANOIDS).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.WITCH);



		//Stands TODO maybe remove
		getOrCreateTagBuilder(EOEUtils.Tags.HUMANOID_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.BOUND_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.REMOTE_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.COLONY_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.AUTOMATIC_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.POSTHUMOUS_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.STRUCTURAL_STAND);
		getOrCreateTagBuilder(EOEUtils.Tags.SPECIAL_STAND);
	}
}
