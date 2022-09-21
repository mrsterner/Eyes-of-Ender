package dev.mrsterner.eyesofender.mixin.entity;

import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.api.interfaces.HamonUser;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}


	@Inject(at = @At("RETURN"), method = "getDimensions", cancellable = true)
	private void onGetDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> info) {
		PlayerEntity player = (PlayerEntity)(Object)this;
		if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.HEAD)){
			info.setReturnValue(EntityDimensions.changing(0.6F, 1.45F));
		} else if(EOEUtils.ifMissingArmsLegsTorso(player)){
			info.setReturnValue(EntityDimensions.changing(0.5F, 0.5F));

		}else if(EOEUtils.ifMissingLegs(player)) {
			info.setReturnValue(EntityDimensions.changing(0.6F, 1.15F));
		}
	}

	@Inject(method = "getActiveEyeHeight", at = @At("HEAD"), cancellable = true)
	private void onGetActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
		PlayerEntity player = (PlayerEntity)(Object)this;
		try {
			if(EOEUtils.ifMissingArmsLegsTorso(player)){
				cir.setReturnValue(0.25F);

			}else if(EOEUtils.ifMissingLegs(player)) {
				cir.setReturnValue(0.85F);
			}
		} catch (NullPointerException ignored) {}
	}
}
