package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.api.registry.HamonAbility;
import dev.mrsterner.eyesofender.common.ability.hamon.*;
import net.minecraft.util.registry.Registry;

public class EOEAbilities {
	//Basic
	public static final HamonAbility ACTIVATE = new ActivateHamonAbility();
	public static final HamonAbility IMBUE = new ImbueAbility();

	public static final HamonAbility OVERDRIVE = new OverdriveAbility();
	public static final HamonAbility OVERDRIVE_SCARLET = new OverdriveScarletAbility();
	public static final HamonAbility OVERDRIVE_TURQUOISE = new OverdriveTurquoiseAbility();
	public static final HamonAbility OVERDRIVE_YELLOW = new OverdriveYellowAbility();
	public static final HamonAbility OVERDRIVE_BARRAGE = new OverdriveBarrageAbility();

	public static final HamonAbility HEALING = new HealingAbility();

	//Intermediate
	public static final HamonAbility FORCED = new ForcedHamonAbility();
	public static final HamonAbility ATTRACTING = new HamonAttractionAbility();
	public static final HamonAbility REPELLING = new HamonRepellingAbility();
	public static final HamonAbility METALSILVER = new MetalSilverOverdriveAbility();
	public static final HamonAbility SENDO_OVERDRIVE = new SendoOverdriveAbility();
	public static final HamonAbility SENDO_WAVE_KICK = new SendoWaveKickAbility();
	public static final HamonAbility ZOOM_PUNCH = new ZoomPunchAbility();

	//Advanced
	public static final HamonAbility HAMON_BEAT = new HamonBeatOverdriveAbility();
	public static final HamonAbility LIFE_MAGNET = new LifeMagnetAbility();
	public static final HamonAbility TORNADO = new TornadoOverdiveAbility();
	public static final HamonAbility REBUFF = new RebuffOverdriveAbility();
	public static final HamonAbility INDIGO = new IndigoOverdriveAbility();
	public static final HamonAbility THUNDER = new ThunderSplitAbility();
	public static final HamonAbility THUNDER_CROSS = new ThunderCrossSplitAbility();

	//Master
	public static final HamonAbility AFTERIMAGE = new HamonAfterimagesAbility();
	public static final HamonAbility PRECOGNITION = new PrecognitionAbility();
	public static final HamonAbility SLOWED_AGE = new SlowedAgeingAbility();
	public static final HamonAbility HYPNOSIS = new HamonHypnosisAbility();
	public static final HamonAbility ORANGE = new OrangeOverdriveAbility();
	public static final HamonAbility CUTTER = new HamonCutterAbility();
	public static final HamonAbility DETECTOR = new HamonDetectorAbility();

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
		register(METALSILVER);
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

	private static void register(HamonAbility ability) {
		Registry.register(EOERegistries.HAMON_ABILITY, ability.getId(), ability);
	}
}
