package dev.mrsterner.eyesofender;

import com.mojang.datafixers.util.Pair;
import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.api.events.AttackLivingEntityCallback;
import dev.mrsterner.eyesofender.api.events.SwingHandEntityCallback;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.stand.EOEStandAbilitiesCallback;
import dev.mrsterner.eyesofender.common.block.CoffinBlock;
import dev.mrsterner.eyesofender.common.networking.packet.HamonAbilityPacket;
import dev.mrsterner.eyesofender.common.registry.*;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldTickEvents;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.GeckoLibMod;

import java.util.HashMap;

public class EyesOfEnder implements ModInitializer {
	public static final String MODID = "eyesofender";
	public static final QuiltItemGroup EOE_GROUP = QuiltItemGroup.builder(EyesOfEnder.id("items")).icon(() -> new ItemStack(EOEObjects.STAND_ARROW)).build();
	public static final Logger LOGGER = LoggerFactory.getLogger("Eyes of Ender");
	private HashMap<Entity, Pair<PlayerEntity, Float>> damages = new HashMap<>();

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
		EOEHamonAbilities.init();
		EOEWorldGenerators.init();
		EOEStandAbilitiesCallback.init();

		ServerPlayNetworking.registerGlobalReceiver(HamonAbilityPacket.ID, HamonAbilityPacket::handleFromClient);

		AttackEntityCallback.EVENT.register(this::stainStoneMask);
		AttackEntityCallback.EVENT.register(this::damageInTimeStop);
		AttackLivingEntityCallback.EVENT.register(this::hamonAttack);
		UseItemCallback.EVENT.register(this::hamonAttack);
		SwingHandEntityCallback.EVENT.register(this::hamonAttack);

		ServerWorldTickEvents.START.register(this::timeStopper);
		EntitySleepEvents.ALLOW_SLEEP_TIME.register(this::coffinSleep);
		EntitySleepEvents.ALLOW_SLEEPING.register(this::coffinSleep);
		ServerWorldTickEvents.START.register(this::dropItems);

	}

	private ActionResult hamonAttack(LivingEntity livingEntity, World world, Hand hand) {
		EOEComponents.HAMON_COMPONENT.maybeGet(livingEntity).ifPresent(hamonUser -> {
			if(hamonUser.getStoredChargedHamon() != null){
				HamonKnowledge hamonKnowledge = hamonUser.getStoredChargedHamon();
				hamonKnowledge.onChargedAbilityUsed(livingEntity ,null);
				hamonUser.setStoredChargedHamon(null);
			}
		});
		return ActionResult.PASS;
	}

	private TypedActionResult<ItemStack> hamonAttack(PlayerEntity player, World world, Hand hand) {
		EOEComponents.HAMON_COMPONENT.maybeGet(player).ifPresent(hamonUser -> {
			if(hamonUser.getStoredChargedHamon() != null && player.getMainHandStack().isEmpty()){
				HamonKnowledge hamonKnowledge = hamonUser.getStoredChargedHamon();
				hamonKnowledge.onChargedAbilityUsed(player ,null);
				hamonUser.setStoredChargedHamon(null);
			}
		});
		return TypedActionResult.pass(player.getMainHandStack());
	}

	private ActionResult hamonAttack(LivingEntity livingEntity, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		EOEComponents.HAMON_COMPONENT.maybeGet(livingEntity).ifPresent(hamonUser -> {
			if(hamonUser.getStoredChargedHamon() != null && entity instanceof LivingEntity target){
				HamonKnowledge hamonKnowledge = hamonUser.getStoredChargedHamon();
				hamonKnowledge.onChargedAbilityUsed(livingEntity,target);
				hamonUser.setStoredChargedHamon(null);
			}
		});
		return ActionResult.PASS;
	}

	/**
	 * If the user looses any body parts any items which would be on that bodypart is dropped
	 * @param minecraftServer
	 * @param serverWorld
	 */
	private void dropItems(MinecraftServer minecraftServer, ServerWorld serverWorld) {
		serverWorld.getPlayers().forEach(serverPlayerEntity -> {
			if(EOEUtils.ifMissingLegs(serverPlayerEntity)){
				serverPlayerEntity.dropItem(serverPlayerEntity.getEquippedStack(EquipmentSlot.LEGS).getItem(), 1);
				serverPlayerEntity.getEquippedStack(EquipmentSlot.LEGS).decrement(1);
				serverPlayerEntity.dropItem(serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET).getItem(), 1);
				serverPlayerEntity.getEquippedStack(EquipmentSlot.FEET).decrement(1);
			}
			if(!EOEComponents.BODY_COMPONENT.get(serverPlayerEntity).hasBodyPart(BodyPart.TORSO)){
				serverPlayerEntity.dropItem(serverPlayerEntity.getEquippedStack(EquipmentSlot.CHEST).getItem(), 1);
				serverPlayerEntity.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
			}
			if(!EOEComponents.BODY_COMPONENT.get(serverPlayerEntity).hasBodyPart(BodyPart.HEAD)){
				serverPlayerEntity.dropItem(serverPlayerEntity.getEquippedStack(EquipmentSlot.HEAD).getItem(), 1);
				serverPlayerEntity.getEquippedStack(EquipmentSlot.HEAD).decrement(1);
			}
		});
	}

	/**
	 * Allow sleeping in a coffin
	 * @param player
	 * @param blockPos
	 * @param vanillaResult
	 * @return
	 */
	private ActionResult coffinSleep(PlayerEntity player, BlockPos blockPos, boolean vanillaResult) {
		if(player.world.getBlockState(blockPos).getBlock() instanceof CoffinBlock && player.world.isDay()){
			return ActionResult.success(player.world.isClient);
		}else{
			return ActionResult.PASS;
		}
	}

	/**
	 * SleepFailureReason for coffin
	 * @param player
	 * @param blockPos
	 * @return
	 */
	private PlayerEntity.SleepFailureReason coffinSleep(PlayerEntity player, BlockPos blockPos) {
		if (player.world.getBlockState(blockPos).getBlock() instanceof CoffinBlock) {
			if (player.world.isNight()) {
				player.sendMessage(Text.translatable("block.minecraft.bed.coffin"), true);
				return PlayerEntity.SleepFailureReason.OTHER_PROBLEM;
			}
			return null;
		}
		return null;
	}

	/**
	 * Stack damage on entities on time-stop
	 * @param player time stopper
	 * @param world
	 * @param hand
	 * @param entity target
	 * @param entityHitResult
	 * @return
	 */
	private ActionResult damageInTimeStop(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		if (!player.isSpectator() && world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && !world.isClient) {
			float damageAlready = damages.getOrDefault(entity, new Pair<>(null, 0f)).getSecond();
			damages.put(entity, new Pair<>(player, (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) + damageAlready));
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	/**
	 *
	 * @param minecraftServer
	 * @param serverWorld
	 */
	private void timeStopper(MinecraftServer minecraftServer, ServerWorld serverWorld) {
		if (serverWorld != null && TimeStopUtils.getTimeStoppedTicks(serverWorld) < 0 && !damages.isEmpty()) {
			damages.forEach((entity, playerAndDamage) -> entity.damage(EntityDamageSource.player(playerAndDamage.getFirst()), playerAndDamage.getSecond()));
			damages.clear();
		}
	}

	/**
	 *
	 * @param player
	 * @param world
	 * @param hand
	 * @param entity
	 * @param entityHitResult
	 * @return
	 */
	private ActionResult stainStoneMask(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
		if(player.getEquippedStack(EquipmentSlot.HEAD) == EOEObjects.STONE_MASK.getDefaultStack() && player.getMainHandStack() == EOEObjects.DAGGER.getDefaultStack()){
			if(entityHitResult != null && entityHitResult.getEntity().getType().isIn(EOEUtils.Tags.HUMANOIDS)){
				ItemStack mask = player.getEquippedStack(EquipmentSlot.HEAD);
				if(mask.getNbt() != null && mask.getNbt().contains("Bloody")){
					return ActionResult.PASS;
				}
				NbtCompound compound = new NbtCompound();
				compound.putBoolean("Bloody", true);
				mask.getOrCreateNbt().put("State", compound);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.PASS;
	}


}
