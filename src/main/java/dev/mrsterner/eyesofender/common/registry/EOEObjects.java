package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.item.DebugStick;
import dev.mrsterner.eyesofender.common.item.StoneMaskItem;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class EOEObjects {
	public static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
	public static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	public static final Item DEBUG_ITEM = register("debug_item", new DebugStick(gen()));

	public static final Item STAND_ARROW = register("stand_arrow", new Item(gen()));
	public static final Item STONE_OF_AJA = register("stone_of_aja", new Item(gen()));
    public static final Item STONE_MASK = register("stone_mask", new StoneMaskItem(EOEMaterials.VAMPIRE_ARMOR , EquipmentSlot.HEAD, gen()));
	public static final Item DAGGER = register("dagger", new SwordItem(ToolMaterials.IRON, 2, -3F, gen()));

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
