package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.api.registry.HamonKnowledge;
import dev.mrsterner.eyesofender.common.ability.hamon.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EOEHamonAbilities {
	private static final List<HamonKnowledge> HAMON_ABILITIES = new ArrayList<>();

	//Basic
	public static final HamonKnowledge ACTIVATE = register(new ActivateHamonAbility());
	public static final HamonKnowledge IMBUE = register(new ImbueAbility());
	public static final HamonKnowledge OVERDRIVE = register(new OverdriveAbility());
	public static final HamonKnowledge OVERDRIVE_SCARLET = register(new OverdriveScarletAbility());
	public static final HamonKnowledge OVERDRIVE_TURQUOISE = register(new OverdriveTurquoiseAbility());
	public static final HamonKnowledge OVERDRIVE_YELLOW = register(new OverdriveYellowAbility());
	public static final HamonKnowledge OVERDRIVE_BARRAGE = register(new OverdriveBarrageAbility());
	public static final HamonKnowledge HEALING = register(new HealingAbility());

	//Intermediate
	public static final HamonKnowledge FORCED = register(new ForcedHamonAbility());
	public static final HamonKnowledge ATTRACTING = register(new HamonAttractionAbility());
	public static final HamonKnowledge REPELLING = register(new HamonRepellingAbility());
	public static final HamonKnowledge METAL_SILVER = register(new MetalSilverOverdriveAbility());
	public static final HamonKnowledge SENDO_OVERDRIVE = register(new SendoOverdriveAbility());
	public static final HamonKnowledge SENDO_WAVE_KICK = register(new SendoWaveKickAbility());
	public static final HamonKnowledge ZOOM_PUNCH = register(new ZoomPunchAbility());

	//Advanced
	public static final HamonKnowledge HAMON_BEAT = register(new HamonBeatOverdriveAbility());
	public static final HamonKnowledge LIFE_MAGNET = register(new LifeMagnetAbility());
	public static final HamonKnowledge TORNADO = register(new TornadoOverdiveAbility());
	public static final HamonKnowledge REBUFF = register(new RebuffOverdriveAbility());
	public static final HamonKnowledge INDIGO = register(new IndigoOverdriveAbility());
	public static final HamonKnowledge THUNDER = register(new ThunderSplitAbility());
	public static final HamonKnowledge THUNDER_CROSS = register(new ThunderCrossSplitAbility());

	//Master
	public static final HamonKnowledge AFTERIMAGE = register(new HamonAfterimagesAbility());
	public static final HamonKnowledge PRECOGNITION = register(new PrecognitionAbility());
	public static final HamonKnowledge SLOWED_AGE = register(new SlowedAgeingAbility());
	public static final HamonKnowledge HYPNOSIS = register(new HamonHypnosisAbility());
	public static final HamonKnowledge ORANGE = register(new OrangeOverdriveAbility());
	public static final HamonKnowledge CUTTER = register(new HamonCutterAbility());
	public static final HamonKnowledge DETECTOR = register(new HamonDetectorAbility());

	private static HamonKnowledge register(HamonKnowledge effect) {
		HAMON_ABILITIES.add(effect);
		return effect;
	}

	public static void init(){
		HAMON_ABILITIES.forEach(ability -> Registry.register(EOERegistries.HAMON_ABILITY, ability.getId(), ability));
	}
}
