package dev.mrsterner.eyesofender.common.registry;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;

public class EOEWorldGenerators extends ConfiguredFeatureUtil {


	private static <C extends FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
		return Registry.register(Registry.FEATURE, name, feature);
	}


	public static void init() {

	}
}
