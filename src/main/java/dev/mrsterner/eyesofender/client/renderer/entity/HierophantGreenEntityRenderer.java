package dev.mrsterner.eyesofender.client.renderer.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.model.entity.HierophantGreenEntityModel;
import dev.mrsterner.eyesofender.common.entity.stand.bound.HierophantGreenEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HierophantGreenEntityRenderer extends GeoEntityRenderer<HierophantGreenEntity> {
    public HierophantGreenEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HierophantGreenEntityModel());
    }



    @Override
    public RenderLayer getRenderType(HierophantGreenEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
    }


}
