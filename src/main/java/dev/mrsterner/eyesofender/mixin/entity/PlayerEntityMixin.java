package dev.mrsterner.eyesofender.mixin.entity;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.networking.packet.SyncHamonUserDataPacket;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.mrsterner.eyesofender.common.utils.NbtUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
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

import static dev.mrsterner.eyesofender.common.utils.EOEUtils.DataTrackers.ABILITY_COOLDOWN;
import static dev.mrsterner.eyesofender.common.utils.EOEUtils.DataTrackers.MAX_ABILITIES;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements HamonUser {

	@Unique
	private List<HamonAbility> abilities = new ArrayList<>();
	@Unique
	private Set<HamonKnowledge> learnedKnowledge = new HashSet<>();
	@Unique
	private int hamonBreath = 10; //TODO change to 0
	@Unique
	private Hamon hamonLevel = Hamon.BASIC; //TODO change to EMPTY

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "initDataTracker()V", at = @At("TAIL"))
	private void eyesOfEnder$addDataTrackers(CallbackInfo info) {
		dataTracker.startTracking(ABILITY_COOLDOWN, 0);
		dataTracker.startTracking(MAX_ABILITIES, 9);
	}

	@Inject(method = "tick()V", at = @At("TAIL"))
	private void eyesOfEnder$tickAbilityCooldown(CallbackInfo info) {
		if (dataTracker != null && getHamonAbilityCooldown() > 0) {
			setHamonAbilityCooldown(getHamonAbilityCooldown() - 1);
		}
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void eyesOfEnder$writeEOEData(NbtCompound compoundTag, CallbackInfo info) {
		NbtCompound tag = new NbtCompound();
		tag.putInt(EOEUtils.Nbt.MAX_ABILITIES, getMaxHamonAbilities());
		tag.putInt(EOEUtils.Nbt.ABILITY_COOLDOWN, getHamonAbilityCooldown());
		tag.putInt(EOEUtils.Nbt.HAMON_BREATH, getHamonBreath());
		NbtUtils.writeAbilityData(this, tag);
		compoundTag.put("Data", tag);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	public void eyesOfEnder$readEOEData(NbtCompound compoundTag, CallbackInfo info) {
		NbtCompound tag = (NbtCompound) compoundTag.get("Data");
		if (tag != null) {
			setMaxHamonAbilities(tag.getInt(EOEUtils.Nbt.MAX_ABILITIES));
			setHamonAbilityCooldown(tag.getInt(EOEUtils.Nbt.ABILITY_COOLDOWN));
			setHamonBreath(tag.getInt(EOEUtils.Nbt.HAMON_BREATH));
			NbtUtils.readAbilityData(this, tag);
		}
	}

	@Override
	public int getMaxHamonAbilities() {
		return dataTracker.get(MAX_ABILITIES);
	}

	@Override
	public void setMaxHamonAbilities(int amount) {
		dataTracker.set(MAX_ABILITIES, amount);
	}

	@Override
	public List<HamonAbility> getHamonAbilities() {
		return abilities;
	}

	@Override
	public Set<HamonKnowledge> getLearnedHamonKnowledge() {
		return learnedKnowledge;
	}

	@Override
	public void learnHamonKnowledge(HamonKnowledge effect) {
		learnedKnowledge.add(effect);
	}

	@Override
	public int getHamonAbilityCooldown() {
		return dataTracker.get(ABILITY_COOLDOWN);
	}

	@Override
	public void setHamonAbilityCooldown(int ticks) {
		this.dataTracker.set(ABILITY_COOLDOWN, ticks);
	}

	@Override
	public void syncHamonAbilityData() {
		if (!world.isClient) {
			SyncHamonUserDataPacket.send(false, (PlayerEntity) (Object) this, this);
		}
	}

	@Override
	public int getHamonBreath() {
		return hamonBreath;
	}

	@Override
	public void setHamonBreath(int amount) {
		this.hamonBreath = getHamonBreath() + amount;
	}


	@Override
	public Hamon getHamonLevel() {
		return hamonLevel;
	}

	@Override
	public void setHamonLevel(Hamon hamonLevel) {
		this.hamonLevel = hamonLevel;
	}
}
