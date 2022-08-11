package dev.mrsterner.eyesofender.client.renderer.feature;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.registry.EOERenderLayers;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;


public class HamonFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private final FeatureRenderer<T, M>[] otherFeatureRenderers;

	@SafeVarargs
	public HamonFeatureRenderer(FeatureRendererContext<T, M> context, FeatureRenderer<T, M>... otherFeatureRenderers) {
		super(context);
		this.otherFeatureRenderers = otherFeatureRenderers;
	}



	private static final Identifier SKIN = EyesOfEnder.id("textures/entity/hamon/yellow.png");

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		float f = (float)entity.age + tickDelta;
		matrices.push();
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(SKIN, f * 0.01F, f * 0.01F % 1.0F));
		this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 0.25F);
		matrices.pop();
	}
}
