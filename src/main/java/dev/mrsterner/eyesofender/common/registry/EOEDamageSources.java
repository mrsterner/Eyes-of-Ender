package dev.mrsterner.eyesofender.common.registry;

import net.minecraft.entity.damage.DamageSource;

public class EOEDamageSources {
	public static final DamageSource DUMMY = new DummyDamageSource("dummy");

	private static class DummyDamageSource extends DamageSource {

		public DummyDamageSource(String name) {
			super(name);
		}
	}
}
