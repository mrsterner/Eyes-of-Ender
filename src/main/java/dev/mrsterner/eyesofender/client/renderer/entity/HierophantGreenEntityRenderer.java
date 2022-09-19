package dev.mrsterner.eyesofender.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.model.entity.HierophantGreenEntityModel;
import dev.mrsterner.eyesofender.client.registry.EOERenderLayers;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.client.renderer.feature.LiquidEffectFeatureRenderer;
import dev.mrsterner.eyesofender.common.entity.BaseStandEntity;
import dev.mrsterner.eyesofender.common.entity.bound.HierophantGreenEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class HierophantGreenEntityRenderer extends GeoEntityRenderer<HierophantGreenEntity> {
    public HierophantGreenEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new HierophantGreenEntityModel());
        this.addLayer(new LiquidEffectFeatureRenderer(this));
    }



    @Override
    public RenderLayer getRenderType(HierophantGreenEntity animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureResource(animatable));
    }


}
