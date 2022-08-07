package dev.mrsterner.eyesofender.mixin.entity;

import dev.mrsterner.eyesofender.api.interfaces.AbilityUser;
import dev.mrsterner.eyesofender.api.registry.AbilityEffect;
import dev.mrsterner.eyesofender.common.ability.Ability;
import dev.mrsterner.eyesofender.common.networking.packet.SyncAbilityUserDataPacket;
import dev.mrsterner.eyesofender.common.registry.EOEAbilities;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dev.mrsterner.eyesofender.common.utils.EOEUtils.ABILITY_COOLDOWN;
import static dev.mrsterner.eyesofender.common.utils.EOEUtils.MAX_ABILITIES;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements AbilityUser {


	@Unique
	private final List<Ability> abilities = new ArrayList<>();
	@Unique
	private final Set<AbilityEffect> learnedEffects = new HashSet<>();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker()V", at = @At("TAIL"))
	private void addDataTrackers(CallbackInfo info) {
		dataTracker.startTracking(ABILITY_COOLDOWN, 0);
		dataTracker.startTracking(MAX_ABILITIES, 9);
	}

	@Inject(method = "tick()V", at = @At("TAIL"))
	private void tickAbilityCooldown(CallbackInfo info) {
		if (dataTracker != null && getAbilityCooldown() > 0) {
			setAbilityCooldown(getAbilityCooldown() - 1);
		}
		Ability[] abilities = new Ability[this.getMaxAbilities()];
		if(abilities[0] == null){
			abilities[0] = new Ability(EOEAbilities.ACTIVATE, 1);
			this.getAbilities().add(0, abilities[0]);
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeEOEData(NbtCompound compoundTag, CallbackInfo info) {
		NbtCompound tag = new NbtCompound();
		tag.putInt("MaxAbilities", getMaxAbilities());
		tag.putInt("AbilityCooldown", getAbilityCooldown());
		NbtUtils.writeAbilityData(this, tag);
		compoundTag.put("Data", tag);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	public void readEOEData(NbtCompound compoundTag, CallbackInfo info) {
		NbtCompound tag = (NbtCompound) compoundTag.get("Data");
		if (tag != null) {
			setMaxAbilities(tag.getInt("MaxAbilities"));
			setAbilityCooldown(tag.getInt("AbilityCooldown"));
			NbtUtils.readAbilityData(this, tag);
		}
	}

	@Override
	public int getMaxAbilities() {
		return dataTracker.get(MAX_ABILITIES);
	}

	@Override
	public void setMaxAbilities(int amount) {
		dataTracker.set(MAX_ABILITIES, amount);
	}

	@Override
	public List<Ability> getAbilities() {
		return abilities;
	}

	@Override
	public Set<AbilityEffect> getLearnedEffects() {
		return learnedEffects;
	}

	@Override
	public void learnEffect(AbilityEffect effect) {
		learnedEffects.add(effect);
	}

	@Override
	public int getAbilityCooldown() {
		return dataTracker.get(ABILITY_COOLDOWN);
	}

	@Override
	public void setAbilityCooldown(int ticks) {
		this.dataTracker.set(ABILITY_COOLDOWN, ticks);
	}

	@Override
	public void syncAbilityData() {
		if (!world.isClient) {
			SyncAbilityUserDataPacket.send(false, (PlayerEntity) (Object) this, this);
		}
	}


}
