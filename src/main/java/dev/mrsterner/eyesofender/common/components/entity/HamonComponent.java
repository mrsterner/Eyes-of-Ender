package dev.mrsterner.eyesofender.common.components.entity;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class HamonComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final LivingEntity entity;

	public HamonComponent(LivingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void serverTick() {

	}

	@Override
	public void readFromNbt(NbtCompound tag) {

	}

	@Override
	public void writeToNbt(NbtCompound tag) {

	}
}
