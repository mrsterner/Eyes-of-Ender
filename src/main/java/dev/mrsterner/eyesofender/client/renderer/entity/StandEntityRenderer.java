package dev.mrsterner.eyesofender.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.model.entity.StandEntityModel;
import dev.mrsterner.eyesofender.common.entity.BaseStandEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class StandEntityRenderer extends GeoEntityRenderer<BaseStandEntity> {
	public StandEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new StandEntityModel());
	}

	@Override
	public RenderLayer getRenderType(BaseStandEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
	}
}
