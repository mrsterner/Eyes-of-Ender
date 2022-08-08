package dev.mrsterner.eyesofender;

import dev.mrsterner.eyesofender.common.networking.packet.AbilityPacket;
import dev.mrsterner.eyesofender.common.networking.packet.SyncAbilityUserDataPacket;
import dev.mrsterner.eyesofender.common.registry.*;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;

public class EyesOfEnder implements ModInitializer {
	public static final String MODID = "eyesofender";
	public static final QuiltItemGroup EOE_GROUP = QuiltItemGroup.builder(EyesOfEnder.id("items")).icon(() -> new ItemStack(EOEObjects.STAND_ARROW)).build();
	public static final Logger LOGGER = LoggerFactory.getLogger("Eyes of Ender");

    public static Identifier id(String name) {
		return new Identifier(EyesOfEnder.MODID, name);
	}


	@Override
	public void onInitialize(ModContainer mod) {
		GeckoLibMod.DISABLE_IN_DEV = true;
		MidnightConfig.init(MODID, EyesOfEnderConfig.class);
		EOEObjects.init();
		EOEBlockEntityTypes.init();
		EOEEntityTypes.init();
		EOEAbilities.init();
		EOEWorldGenerators.init();

		ServerPlayNetworking.registerGlobalReceiver(SyncAbilityUserDataPacket.ID, SyncAbilityUserDataPacket::handleFromClient);
		ServerPlayNetworking.registerGlobalReceiver(AbilityPacket.ID, AbilityPacket::handleFromClient);

		AttackEntityCallback.EVENT.register(this::stainStoneMask);
	}

	private ActionResult stainStoneMask(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		if(player.getEquippedStack(EquipmentSlot.HEAD) == EOEObjects.STONE_MASK.getDefaultStack() && player.getMainHandStack() == EOEObjects.DAGGER.getDefaultStack()){
			ItemStack itemStack = player.getMainHandStack();
			var nbt = itemStack.getNbt();
			if (entityHitResult != null && nbt != null && !nbt.contains("Bloody")) {
				Entity hit = entityHitResult.getEntity();
				if(hit.getType().isIn(EOEUtils.Tags.HUMANOIDS)){
					NbtCompound compound = new NbtCompound();
					compound.putBoolean("Bloody", true);
					itemStack.getOrCreateNbt().put("State", compound);
					return ActionResult.SUCCESS;
				}
			}
		}
		return ActionResult.PASS;
	}
}
