package dev.mrsterner.eyesofender.api.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class HamonKnowledge {
	private final Identifier id;
	private final Predicate<LivingEntity> abilityPredicate;
	private final int color;
	private final Identifier overlay;
	private final int abilityDuration;
	private final boolean isPassive;

	public HamonKnowledge(Identifier id, @Nullable Predicate<LivingEntity> abilityPredicate, int color, Identifier overlay, int abilityDuration, boolean isPassive) {
		this.id = id;
		this.abilityPredicate = abilityPredicate == null ? (living) -> true : abilityPredicate;
		this.color = color;
		this.overlay = overlay;
		this.abilityDuration = abilityDuration;
		this.isPassive = isPassive;
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

	public Identifier getOverlay(){
		return overlay;
	}

	public Identifier getTextureLocation() {
		return new Identifier(id.getNamespace(), "textures/gui/ability_widgets/ability/" + id.getPath() + ".png");
	}

	public int getAbilityDuration() {
		return abilityDuration;
	}

	public boolean getIsPassive() {
		return isPassive;
	}

	public void tickPassive(LivingEntity entity) {

	}

	public void tickAbility(LivingEntity entity) {

	}


}
