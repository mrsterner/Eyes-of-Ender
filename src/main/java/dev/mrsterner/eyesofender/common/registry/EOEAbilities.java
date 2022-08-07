package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.api.registry.AbilityEffect;
import dev.mrsterner.eyesofender.common.ability.hamon.*;
import net.minecraft.util.registry.Registry;

public class EOEAbilities {
	//Basic
	public static final AbilityEffect ACTIVATE = new ActivateHamonAbility();
	public static final AbilityEffect IMBUE = new ImbueAbility();
	public static final AbilityEffect OVERDRIVE = new OverdriveAbility();
	public static final AbilityEffect OVERDRIVE_SCARLET = new OverdriveScarletAbility();
	public static final AbilityEffect OVERDRIVE_TURQUOISE = new OverdriveTurquoiseAbility();
	public static final AbilityEffect OVERDRIVE_YELLOW = new OverdriveYellowAbility();
	public static final AbilityEffect OVERDRIVE_BARRAGE = new OverdriveBarrageAbility();
	public static final AbilityEffect HEALING = new HealingAbility();

	//Intermediate
	public static final AbilityEffect FORCED = new ForcedHamonAbility();
	public static final AbilityEffect ATTRACTING = new HamonAttractionAbility();
	public static final AbilityEffect REPELLING = new HamonRepellingAbility();
	public static final AbilityEffect METAL_SILVER = new MetalSilverOverdriveAbility();
	public static final AbilityEffect SENDO_OVERDRIVE = new SendoOverdriveAbility();
	public static final AbilityEffect SENDO_WAVE_KICK = new SendoWaveKickAbility();
	public static final AbilityEffect ZOOM_PUNCH = new ZoomPunchAbility();

	//Advanced
	public static final AbilityEffect HAMON_BEAT = new HamonBeatOverdriveAbility();
	public static final AbilityEffect LIFE_MAGNET = new LifeMagnetAbility();
	public static final AbilityEffect TORNADO = new TornadoOverdiveAbility();
	public static final AbilityEffect REBUFF = new RebuffOverdriveAbility();
	public static final AbilityEffect INDIGO = new IndigoOverdriveAbility();
	public static final AbilityEffect THUNDER = new ThunderSplitAbility();
	public static final AbilityEffect THUNDER_CROSS = new ThunderCrossSplitAbility();

	//Master
	public static final AbilityEffect AFTERIMAGE = new HamonAfterimagesAbility();
	public static final AbilityEffect PRECOGNITION = new PrecognitionAbility();
	public static final AbilityEffect SLOWED_AGE = new SlowedAgeingAbility();
	public static final AbilityEffect HYPNOSIS = new HamonHypnosisAbility();
	public static final AbilityEffect ORANGE = new OrangeOverdriveAbility();
	public static final AbilityEffect CUTTER = new HamonCutterAbility();
	public static final AbilityEffect DETECTOR = new HamonDetectorAbility();

	public static void init(){
		register(ACTIVATE);
		register(IMBUE);
		register(OVERDRIVE);
		register(OVERDRIVE_SCARLET);
		register(OVERDRIVE_TURQUOISE);
		register(OVERDRIVE_YELLOW);
		register(OVERDRIVE_BARRAGE);
		register(HEALING);
		register(FORCED);
		register(ATTRACTING);
		register(REPELLING);
		register(METAL_SILVER);
		register(SENDO_OVERDRIVE);
		register(SENDO_WAVE_KICK);
		register(HAMON_BEAT);
		register(LIFE_MAGNET);
		register(ZOOM_PUNCH);
		register(TORNADO);
		register(REBUFF);
		register(INDIGO);
		register(THUNDER);
		register(THUNDER_CROSS);
		register(AFTERIMAGE);
		register(PRECOGNITION);
		register(SLOWED_AGE);
		register(HYPNOSIS);
		register(ORANGE);
		register(CUTTER);
		register(DETECTOR);
	}

	private static void register(AbilityEffect ability) {
		Registry.register(EOERegistries.HAMON_ABILITY, ability.getId(), ability);
	}
}
