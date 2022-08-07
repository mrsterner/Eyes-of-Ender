package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.client.renderer.feature.HamonFeatureRenderer;
import dev.mrsterner.eyesofender.client.vertexconsumer.HamonVertexConsumerProvider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
	@Shadow protected abstract void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);
	@Shadow private ItemStack mainHand;
	@Shadow private ItemStack offHand;

	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	private void eoe$renderAuraForMainHandItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci, float f, Hand hand, float g, @Coerce Object handRenderType, float h, float i, float j, float k) {
		float aura = 10;
		if(aura > 0) {
			int[] auraColour = HamonFeatureRenderer.getRgbF(0xfff32a);
			this.renderFirstPersonItem(player, tickDelta, g, Hand.MAIN_HAND, j, this.mainHand, k, matrices, new HamonVertexConsumerProvider(vertexConsumers, auraColour[0], auraColour[1], auraColour[2], (int) (aura * 255)), light);
		}
	}

	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
	private void eoe$renderAuraForOffHandItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci, float f, Hand hand, float g, @Coerce Object handRenderType, float h, float i, float j, float k) {
		float aura = 10;
		if(aura > 0) {
			int[] auraColour = HamonFeatureRenderer.getRgbF(0xfff32a);
			this.renderFirstPersonItem(player, tickDelta, g, Hand.OFF_HAND, j, this.offHand, k, matrices, new HamonVertexConsumerProvider(vertexConsumers, auraColour[0], auraColour[1], auraColour[2], (int) (aura * 255)), light);
		}
	}
}
