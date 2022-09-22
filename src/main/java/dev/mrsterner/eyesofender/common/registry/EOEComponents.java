package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.components.entity.BodyComponent;
import dev.mrsterner.eyesofender.common.components.entity.HamonComponent;
import dev.mrsterner.eyesofender.common.components.entity.StandComponent;
import dev.mrsterner.eyesofender.common.components.entity.TimeStopComponent;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import net.minecraft.entity.player.PlayerEntity;

public class EOEComponents implements EntityComponentInitializer, WorldComponentInitializer {
	public static final ComponentKey<HamonComponent> HAMON_COMPONENT = createComponent("hamon", HamonComponent.class);
	public static final ComponentKey<StandComponent> STAND_COMPONENT = createComponent("stand", StandComponent.class);
	public static final ComponentKey<TimeStopComponent> TIME_STOP_COMPONENT = createComponent("time_stop", TimeStopComponent.class);
	public static final ComponentKey<BodyComponent> BODY_COMPONENT = createComponent("body", BodyComponent.class);


	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, HAMON_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(HamonComponent::new);

		registry.beginRegistration(PlayerEntity.class, STAND_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(StandComponent::new);
		registry.beginRegistration(PlayerEntity.class, BODY_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(BodyComponent::new);
	}

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
		registry.register(TIME_STOP_COMPONENT, TimeStopComponent::new);
	}

	private static <T extends Component> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(EyesOfEnder.id(name), component);
	}
}
