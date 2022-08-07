package dev.mrsterner.eyesofender.api.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class AbilityEffect {
	private final Identifier id;
	private final Predicate<LivingEntity> abilityPredicate;
	private final int color;

	public AbilityEffect(Identifier id, @Nullable Predicate<LivingEntity> abilityPredicate, int color) {
		this.id = id;
		this.abilityPredicate = abilityPredicate == null ? (living) -> true : abilityPredicate;
		this.color = color;
	}

	public boolean canCast(LivingEntity caster) {
		return abilityPredicate.test(caster);
	}

	public int getColor(@Nullable LivingEntity caster) {
		return color;
	}

	public Identifier getId() {
		return id;
	}

	public Identifier getTextureLocation() {
		return new Identifier(id.getNamespace(), "textures/gui/ability_widgets/ability/" + id.getPath() + ".png");
	}


}