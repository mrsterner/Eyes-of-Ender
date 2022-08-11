package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.common.entity.BaseStandEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
	protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
		super(ctx);
	}



	@Inject(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/Entity;FFFFFF)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
	public void eoe$hamon(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info, float h, float j, float k, float m, float l, float n, float o, MinecraftClient minecraftClient, boolean bl, boolean bl2, boolean bl3, RenderLayer renderLayer, Iterator<FeatureRenderer<T, M>> var19, FeatureRenderer<T, M> featureRenderer) {

	}

	@Inject(method = "render*", at = @At("HEAD"))
	private void render(T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo) {
		BaseStandEntity stand = null;//TODO add stand getter
		if(stand != null){
			stand.motionCalc = new Vec3d(livingEntity.getX() - livingEntity.prevX, livingEntity.getY() - livingEntity.prevY,livingEntity.getZ() - livingEntity.prevZ);
			MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(stand).render(stand, yaw, tickDelta, matrixStack, vertexConsumerProvider, i);
		}
	}
}
