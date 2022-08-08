package dev.mrsterner.eyesofender.common.item;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.common.ability.Ability;
import dev.mrsterner.eyesofender.common.registry.EOEAbilities;
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
			AbilityUser.of(player).ifPresent(user -> {
				user.getAbilities().clear();
				ArrayList<Ability> ability = new ArrayList<>();
				ability.add(0, new Ability(EOEAbilities.ACTIVATE, 1));
				ability.add(1, new Ability(EOEAbilities.OVERDRIVE, 1));
				ability.add(2, new Ability(EOEAbilities.AFTERIMAGE, 1));
				ability.add(3, new Ability(EOEAbilities.INDIGO, 1));
				ability.add(4, new Ability(EOEAbilities.ORANGE, 1));

				for(int i = 0; ability.size() > i; i++){
					user.getAbilities().add(i, ability.get(i));
					System.out.println("Ability Added: " + ability.get(i).hamonAbility.getId());
				}
				player.swingHand(hand);
			});
		}else{
			AbilityUser.of(player).ifPresent(user -> {
				user.getAbilities().clear();
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
