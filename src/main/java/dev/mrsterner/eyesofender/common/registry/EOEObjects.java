package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class EOEObjects {
	public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Item STAND_ARROW = register("stand_arrow", new Item(gen()));

	private static Item.Settings gen() {
		return new Item.Settings().group(EyesOfEnder.EOE_GROUP);
	}

	private static <T extends Item> T register(String name, T item) {
		ITEMS.put(item, EyesOfEnder.id(name));
		return item;
	}

	private static <T extends Block> T register(String name, T block, boolean createItem, Item.Settings settings) {
		BLOCKS.put(block, EyesOfEnder.id(name));
		if (createItem) {
			ITEMS.put(new BlockItem(block, settings), BLOCKS.get(block));
		}
		return block;
	}

	public static void init() {
		BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
