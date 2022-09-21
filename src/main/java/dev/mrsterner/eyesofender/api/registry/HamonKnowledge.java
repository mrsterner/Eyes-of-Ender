package dev.mrsterner.eyesofender.api.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class HamonKnowledge {
	private final Identifier id;
	private final Predicate<LivingEntity> abilityPredicate;
	private final int color;
	private final Identifier overlay;
	private final int hamonDrain;
	private final boolean isPassive;

	public HamonKnowledge(Identifier id, @Nullable Predicate<LivingEntity> abilityPredicate, int color, Identifier overlay, int hamonDrain, boolean isPassive) {
		this.id = id;
		this.abilityPredicate = abilityPredicate == null ? (living) -> true : abilityPredicate;
		this.color = color;
		this.overlay = overlay;
		this.hamonDrain = hamonDrain;
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

	public boolean isPassive(){
		return isPassive;
	}

	public int getHamonDrain() {
		return hamonDrain;
	}

	public void tickAbility(LivingEntity entity) {

	}

	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {

	}
}
