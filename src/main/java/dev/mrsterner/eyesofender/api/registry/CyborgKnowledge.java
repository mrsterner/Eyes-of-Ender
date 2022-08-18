package dev.mrsterner.eyesofender.api.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class CyborgKnowledge {
	private final Identifier id;
	private final Predicate<LivingEntity> abilityPredicate;
	private final int color;

	public CyborgKnowledge(Identifier id, @Nullable Predicate<LivingEntity> abilityPredicate, int color) {
		this.id = id;
		this.abilityPredicate = abilityPredicate == null ? (living) -> true : abilityPredicate;
		this.color = color;
	}

	public boolean canUse(LivingEntity user) {
		return abilityPredicate.test(user);
	}

	public int getColor(@Nullable LivingEntity user) {
		return color;
	}

	public Identifier getId() {
		return id;
	}

	public Identifier getTextureLocation() {
		return new Identifier(id.getNamespace(), "textures/gui/ability_widgets/cyborg/" + id.getPath() + ".png");
	}
}
