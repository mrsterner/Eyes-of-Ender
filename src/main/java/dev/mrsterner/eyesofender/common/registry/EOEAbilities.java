package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.api.registry.HamonAbility;
import dev.mrsterner.eyesofender.common.ability.*;
import net.minecraft.util.registry.Registry;

public class EOEAbilities {
	public static final HamonAbility ACTIVATE = new ActivateHamonAbility();
	public static final HamonAbility IMBUE = new ImbueAbility();

	public static final HamonAbility OVERDRIVE = new OverdriveAbility();
	public static final HamonAbility OVERDRIVE_SCARLET = new OverdriveScarletAbility();
	public static final HamonAbility OVERDRIVE_TURQUOISE = new OverdriveTurquoiseAbility();
	public static final HamonAbility OVERDRIVE_YELLOW = new OverdriveYellowAbility();
	public static final HamonAbility OVERDRIVE_BARRAGE = new OverdriveBarrageAbility();

	public static final HamonAbility HEALING = new HealingAbility();

	public static void init(){

	}

	private static void register(HamonAbility ability) {
		Registry.register(EOERegistries.HAMON_ABILITY, ability.getId(), ability);
	}
}
