package dev.mrsterner.eyesofender.common.registry;

import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.common.components.entity.HamonComponent;
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
	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, HAMON_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(HamonComponent::new);
	}

	@Override
	public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {

	}

	private static <T extends Component> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(EyesOfEnder.id(name), component);
	}
}
