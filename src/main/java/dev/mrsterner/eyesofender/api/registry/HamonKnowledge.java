package dev.mrsterner.eyesofender.api.registry;

import dev.mrsterner.eyesofender.api.enums.Hamon;
import dev.mrsterner.eyesofender.api.enums.HamonAbilityType;
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
	private final HamonAbilityType hamonAbilityType;
	private final Hamon hamonTier;
	private final int hamonTimer;

	public HamonKnowledge(Identifier id, Hamon hamonTier, @Nullable Predicate<LivingEntity> abilityPredicate, int color, Identifier overlay, int hamonDrain, HamonAbilityType hamonAbilityType, int hamonTimer) {
		this.id = id;
		this.abilityPredicate = abilityPredicate == null ? (living) -> true : abilityPredicate;
		this.color = color;
		this.overlay = overlay;
		this.hamonDrain = hamonDrain;
		this.hamonAbilityType = hamonAbilityType;
		this.hamonTier = hamonTier;
		this.hamonTimer = hamonTimer;
	}

	public boolean canUse(LivingEntity user) {
		return abilityPredicate.test(user);
	}

	public int getColor() {
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

	public Hamon getHamonTier(){
		return hamonTier;
	}

	public HamonAbilityType getHamonAbilityType(){
		return hamonAbilityType;
	}

	public int getHamonDrain() {
		return hamonDrain;
	}

	public int getHamonTimer() {
		return hamonTimer;
	}

	/**
	 * Runs on Passive abilities
	 * @param user
	 */
	public void tickPassiveAbility(LivingEntity user) {

	}

	/**
	 * Runs when keybind is pressed, also runs onAbilityRemoved so remember to put the super at the bottom of the overriden method
	 * @param world
	 * @param user
	 * @param pos
	 */
	public void useAbility(World world, LivingEntity user, @Nullable Vec3d pos) {
		onAbilityRemoved(user);
	}

	/**
	 * Runs when hamon breath is 0 or timer removed
	 * @param user
	 */
	public void onAbilityRemoved(LivingEntity user){

	}


	public void onChargedAbilityUsed(LivingEntity livingEntity, LivingEntity target) {
	}
}
