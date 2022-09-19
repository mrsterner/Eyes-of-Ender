package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.client.renderer.feature.MouthItemFeatureRenderer;
import dev.mrsterner.eyesofender.common.block.CoffinBlock;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void eoe$init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo callbackInfo) {
        addFeature(new MouthItemFeatureRenderer(this, ctx.getHeldItemRenderer()));
    }


    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo callbackInfo) {
        Optional<BlockPos> pos = player.getSleepingPosition();
        if (pos.isPresent() && player.world.getBlockState(pos.get()).getBlock() instanceof CoffinBlock) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "getPositionOffset*", at = @At("RETURN"), cancellable = true)
    public void noOffset(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, CallbackInfoReturnable<Vec3d> callbackInfo){
        if(!EOEComponents.BODY_COMPONENT.get(abstractClientPlayerEntity).hasBodyPart(BodyPart.LEFTLEG) && !EOEComponents.BODY_COMPONENT.get(abstractClientPlayerEntity).hasBodyPart(BodyPart.RIGHTLEG)){
            callbackInfo.setReturnValue(super.getPositionOffset(abstractClientPlayerEntity, f));
        }
    }

    @Inject(method = "setModelPose", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void eyesOfEnder$setModelPose(AbstractClientPlayerEntity player, CallbackInfo ci, PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel){
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.HEAD)){
            playerEntityModel.head.visible = false;
        }
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTARM)){
            playerEntityModel.leftArm.visible = false;
        }
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTARM)){
            playerEntityModel.rightArm.visible = false;
        }
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTLEG)){
            playerEntityModel.leftLeg.visible = false;
        }
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTLEG)){
            playerEntityModel.rightLeg.visible = false;
        }
        if(!EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.TORSO)){
            playerEntityModel.body.visible = false;
        }
    }
}
