package dev.mrsterner.eyesofender.client.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class EOESoundEvents {

    private static final Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();

	public static final SoundEvent THE_WORLD = register("stand.theworld");
	public static final SoundEvent THE_WORLD_END = register("stand.theworldend");

	private static SoundEvent register(String name) {
		Identifier id = EyesOfEnder.id(name);
		SoundEvent soundEvent = new SoundEvent(id);
		SOUND_EVENTS.put(soundEvent, id);
		return soundEvent;
	}

	public static void init() {
		SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registry.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
	}
}
