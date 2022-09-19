package dev.mrsterner.eyesofender.client.renderer.feature;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.registry.EOERenderLayers;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import dev.mrsterner.eyesofender.common.entity.bound.HierophantGreenEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.GeoModelProvider;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class LiquidEffectFeatureRenderer extends GeoLayerRenderer<HierophantGreenEntity> {
    private final IGeoRenderer<HierophantGreenEntity> hierophantGreenEntityIGeoRenderer;

    public LiquidEffectFeatureRenderer(IGeoRenderer<HierophantGreenEntity> entityRendererIn) {
        super(entityRendererIn);
        hierophantGreenEntityIGeoRenderer = entityRendererIn;
    }

    @Override
    public RenderLayer getRenderType(Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider bufferIn, int packedLightIn, HierophantGreenEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        GeoModel model = this.getEntityModel().getModel(this.getEntityModel().getModelResource(entity));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        RenderLayer renderType = RenderLayer.getEntityTranslucent(this.getEntityTexture(entity));
        VertexConsumer vertexConsumer = bufferIn.getBuffer(EOERenderLayers.HAMON.apply(this.getEntityTexture(entity)));
        ShaderProgram shader = EOEShaders.HAMON.getInstance().get();
        shader.getUniformOrDefault("Speed").setFloat(1500f);
        hierophantGreenEntityIGeoRenderer.render(model, entity, partialTicks, renderType, matrices, bufferIn, vertexConsumer, packedLightIn, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();

        /*
        matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        float alpha = 1F;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        matrices.scale(2,2,2);
        matrices.translate(0,2,0);
        ShaderProgram shader = EOEShaders.HAMON.getInstance().get();
        shader.getUniformOrDefault("Speed").setFloat(1500f);
        RenderLayer renderType = RenderLayer.getEntityTranslucent(this.getEntityTexture(entity));
        renderModel(m, this.getEntityTexture(entity), matrices, bufferIn, packedLightIn, entity, partialTicks,1,1,1);
        //this.getRenderer().render(model, entity, partialTicks, renderType, matrices, bufferIn, vertexConsumer, packedLightIn, LivingEntityRenderer.getOverlay(entity, 0.0F), 1, 1, 1, alpha);
        RenderSystem.disableBlend();
        matrices.pop();

         */
    }


}
