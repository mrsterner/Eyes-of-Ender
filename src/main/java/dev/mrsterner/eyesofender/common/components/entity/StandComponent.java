package dev.mrsterner.eyesofender.common.components.entity;

import dev.mrsterner.eyesofender.api.registry.StandAbility;
import dev.mrsterner.eyesofender.common.ability.HamonAbility;
import dev.mrsterner.eyesofender.common.entity.stand.BaseStandEntity;
import dev.mrsterner.eyesofender.common.entity.stand.bound.HierophantGreenEntity;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.registry.EOEEntityTypes;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StandComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity user;
	private BaseStandEntity baseStandEntity = null;
	private UUID standUUID = null;

	private List<StandAbility> standAbilityList = new ArrayList<>();

	public StandComponent(LivingEntity user) {
		this.user = user;
	}

	@Override
	public void serverTick() {
		if(standUUID != null && baseStandEntity == null){
			Entity entity = ((ServerWorld) user.world).getEntity(getStandUuid());
			if(entity instanceof BaseStandEntity stand){
				setStandEntity(stand);
			}
		}
	}

	public void updateStandAbilities(){
		if(baseStandEntity != null){
			setStandAbilityList(baseStandEntity.getStandAbilities());
		}
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		if(tag.contains(EOEUtils.Nbt.STAND_UUID)){
			setStandUuid(tag.getUuid(EOEUtils.Nbt.STAND_UUID));
		}
		getStandAbilityList().clear();
		for (int i = 0; i < tag.getList(EOEUtils.Nbt.STAND_ABILITY_LIST, 10).size(); i++) {
			getStandAbilityList().add(i, StandAbility.fromTag((NbtCompound) tag.getList(EOEUtils.Nbt.STAND_ABILITY, 10).get(i)));
		}

	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		tag.putUuid(EOEUtils.Nbt.STAND_UUID, standUUID);

		NbtList abilities = new NbtList();
		getStandAbilityList().forEach((ability) -> abilities.add(ability == null ? new NbtCompound() : ability.toTag(new NbtCompound())));
		tag.put(EOEUtils.Nbt.STAND_ABILITY, abilities);
	}

	public List<StandAbility> getStandAbilityList() {
		return standAbilityList;
	}

	public void setStandAbilityList(List<StandAbility> standAbilityList) {
		this.standAbilityList = standAbilityList;
		EOEComponents.STAND_COMPONENT.sync(user);
	}

	public UUID getStandUuid(){
		return standUUID;
	}

	public void setStandUuid(UUID standUUID){
		this.standUUID = standUUID;
		EOEComponents.STAND_COMPONENT.sync(user);
	}

	public BaseStandEntity getStandEntity(){
		return baseStandEntity;
	}

	public void setStandEntity(BaseStandEntity baseStandEntity){
		this.baseStandEntity = baseStandEntity;
		EOEComponents.STAND_COMPONENT.sync(user);
	}


}
