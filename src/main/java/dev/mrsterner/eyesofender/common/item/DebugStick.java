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
		if(true){
			AbilityUser.of(player).ifPresent(user -> {
				Ability ability = new Ability(EOEAbilities.ACTIVATE, 1);
				user.getAbilities().add(0, ability);
				System.out.println(ability);
			});

		}
		return super.use(world, player, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable(EyesOfEnder.MODID + ".debug.entry1").formatted(Formatting.GRAY));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
