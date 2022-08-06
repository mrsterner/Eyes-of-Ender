package dev.mrsterner.eyesofender;

import dev.mrsterner.eyesofender.common.registry.*;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EyesOfEnder implements ModInitializer {
	public static final String MODID = "eyesofender";
	public static final QuiltItemGroup EOE_GROUP = QuiltItemGroup.builder(EyesOfEnder.id("items")).icon(() -> new ItemStack(EOEObjects.STAND_ARROW)).build();

	public static Identifier id(String name) {
		return new Identifier(EyesOfEnder.MODID, name);
	}


	@Override
	public void onInitialize(ModContainer mod) {
		MidnightConfig.init(MODID, EyesOfEnderConfig.class);
		EOEObjects.init();
		EOEBlockEntityTypes.init();
		EOEEntityTypes.init();
		EOEWorldGenerators.init();
	}
}
