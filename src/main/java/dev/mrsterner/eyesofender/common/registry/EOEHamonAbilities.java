package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.hamon.*;
import net.minecraft.util.registry.Registry;

public class EOEHamonAbilities {
	//Basic
	public static final HamonKnowledge ACTIVATE = new ActivateHamonAbility();
	public static final HamonKnowledge IMBUE = new ImbueAbility();
	public static final HamonKnowledge OVERDRIVE = new OverdriveAbility();
	public static final HamonKnowledge OVERDRIVE_SCARLET = new OverdriveScarletAbility();
	public static final HamonKnowledge OVERDRIVE_TURQUOISE = new OverdriveTurquoiseAbility();
	public static final HamonKnowledge OVERDRIVE_YELLOW = new OverdriveYellowAbility();
	public static final HamonKnowledge OVERDRIVE_BARRAGE = new OverdriveBarrageAbility();
	public static final HamonKnowledge HEALING = new HealingAbility();

	//Intermediate
	public static final HamonKnowledge FORCED = new ForcedHamonAbility();
	public static final HamonKnowledge ATTRACTING = new HamonAttractionAbility();
	public static final HamonKnowledge REPELLING = new HamonRepellingAbility();
	public static final HamonKnowledge METAL_SILVER = new MetalSilverOverdriveAbility();
	public static final HamonKnowledge SENDO_OVERDRIVE = new SendoOverdriveAbility();
	public static final HamonKnowledge SENDO_WAVE_KICK = new SendoWaveKickAbility();
	public static final HamonKnowledge ZOOM_PUNCH = new ZoomPunchAbility();

	//Advanced
	public static final HamonKnowledge HAMON_BEAT = new HamonBeatOverdriveAbility();
	public static final HamonKnowledge LIFE_MAGNET = new LifeMagnetAbility();
	public static final HamonKnowledge TORNADO = new TornadoOverdiveAbility();
	public static final HamonKnowledge REBUFF = new RebuffOverdriveAbility();
	public static final HamonKnowledge INDIGO = new IndigoOverdriveAbility();
	public static final HamonKnowledge THUNDER = new ThunderSplitAbility();
	public static final HamonKnowledge THUNDER_CROSS = new ThunderCrossSplitAbility();

	//Master
	public static final HamonKnowledge AFTERIMAGE = new HamonAfterimagesAbility();
	public static final HamonKnowledge PRECOGNITION = new PrecognitionAbility();
	public static final HamonKnowledge SLOWED_AGE = new SlowedAgeingAbility();
	public static final HamonKnowledge HYPNOSIS = new HamonHypnosisAbility();
	public static final HamonKnowledge ORANGE = new OrangeOverdriveAbility();
	public static final HamonKnowledge CUTTER = new HamonCutterAbility();
	public static final HamonKnowledge DETECTOR = new HamonDetectorAbility();

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

	private static void register(HamonKnowledge ability) {
		Registry.register(EOERegistries.HAMON_ABILITY, ability.getId(), ability);
	}
}
