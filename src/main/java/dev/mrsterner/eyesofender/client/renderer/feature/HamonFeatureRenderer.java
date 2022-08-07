package dev.mrsterner.eyesofender.client.renderer.feature;

import dev.mrsterner.eyesofender.client.vertexconsumer.HamonVertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;


public class HamonFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private final FeatureRenderer<T, M>[] otherFeatureRenderers;

	@SafeVarargs
	public HamonFeatureRenderer(FeatureRendererContext<T, M> context, FeatureRenderer<T, M>... otherFeatureRenderers) {
		super(context);
		this.otherFeatureRenderers = otherFeatureRenderers;
	}

	public static int[] getRgbF(int colour) {
		return new int[]{(colour >> 16 & 255), (colour >> 8 & 255), (colour & 255)};
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float aura = 10;//TODO 20;
		if(aura > 0)
			renderAura(matrices, vertexConsumers, entity, light, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch, (int) (aura * 255), getRgbF(0xfff32a));
	}

	private void renderAura(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, int light, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, int aura, int[] auraColour) {
		float scale = 1F;
		var auraConsumerProvider = new HamonVertexConsumerProvider(vertexConsumers, auraColour[0], auraColour[1], auraColour[2], aura);
		matrices.push();
		matrices.scale(scale, scale, scale);
		for(FeatureRenderer<T, M> renderer : otherFeatureRenderers)
			renderer.render(matrices, auraConsumerProvider, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);

		matrices.translate(0D, -((entity.getHeight() * scale) * 0.5D - entity.getHeight() * 0.5D), 0D);
		getContextModel().render(matrices, auraConsumerProvider.getBuffer(this.getTexture(entity)), light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
		matrices.pop();
	}
}
