package dev.mrsterner.eyesofender.common.item;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOEHamonAbilities;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DebugStick extends Item {
	public DebugStick(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		if(!player.isSneaking()){
			EOEComponents.HAMON_COMPONENT.maybeGet(player).ifPresent(user -> {
				user.getHamonAbilities().clear();
				ArrayList<HamonAbility> hamonAbility = new ArrayList<>();
				hamonAbility.add(0, new HamonAbility(EOEHamonAbilities.HEALING));
				hamonAbility.add(1, new HamonAbility(EOEHamonAbilities.OVERDRIVE));
				hamonAbility.add(2, new HamonAbility(EOEHamonAbilities.AFTERIMAGE));
				hamonAbility.add(3, new HamonAbility(EOEHamonAbilities.INDIGO));
				hamonAbility.add(4, new HamonAbility(EOEHamonAbilities.ORANGE));
				hamonAbility.add(5, new HamonAbility(EOEHamonAbilities.OVERDRIVE_TURQUOISE));

				for(int i = 0; hamonAbility.size() > i; i++){
					user.getHamonAbilities().add(i, hamonAbility.get(i));
					System.out.println("Ability Added: " + hamonAbility.get(i).hamonKnowledge.getId());
				}
				player.swingHand(hand);
			});
		}else{
			EOEComponents.HAMON_COMPONENT.maybeGet(player).ifPresent(user -> {
				user.getHamonAbilities().clear();
				System.out.println("Abilities Cleared");
				player.swingHand(hand);
			});
		}
		return super.use(world, player, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(EyesOfEnder.MODID + ".debug.entry1").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable(EyesOfEnder.MODID + ".debug.entry2").formatted(Formatting.GRAY));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
