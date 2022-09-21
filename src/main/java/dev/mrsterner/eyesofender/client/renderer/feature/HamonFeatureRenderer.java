package dev.mrsterner.eyesofender.client.renderer.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.registry.EOERenderLayers;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.common.utils.EOEUtils;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class HamonFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

	public HamonFeatureRenderer(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(EOEUtils.shouldHamonGlow(entity) != null){
			float[] color = EOEUtils.shouldHamonGlow(entity);

			matrices.push();
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(EOERenderLayers.HAMON.apply(this.getTexture(entity)));
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			float alpha = 1F;
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
			ShaderProgram shader = EOEShaders.HAMON.getInstance().get();
			shader.getUniformOrDefault("Speed").setFloat(1500f);
			shader.getUniformOrDefault("Color").setVec3(color[0] ,color[1] ,color[2]);
			var model = this.getContextModel();
			if(model instanceof PlayerEntityModel<?> playerEntityModel){
				playerEntityModel.hat.visible = false;
				playerEntityModel.jacket.visible = false;
				playerEntityModel.leftPants.visible = false;
				playerEntityModel.rightPants.visible = false;
				playerEntityModel.leftSleeve.visible = false;
				playerEntityModel.rightSleeve.visible = false;
			}
			model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, alpha);
			matrices.scale(1.05f,1.05f,1.05f);
			matrices.translate(0,-0.01,0);
			model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, alpha);
			RenderSystem.disableBlend();
			matrices.pop();
		}
	}
}
