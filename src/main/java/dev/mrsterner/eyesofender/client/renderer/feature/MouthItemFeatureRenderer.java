package dev.mrsterner.eyesofender.client.renderer.feature;

import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class MouthItemFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private final HeldItemRenderer itemRenderer;

    public MouthItemFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.itemRenderer = heldItemRenderer;
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!player.isInvisible() && !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.RIGHTARM)) {
            matrices.push();
            getContextModel().head.rotate(matrices);
            matrices.translate(0, -0.2, -0.5);
            ItemStack itemStack = player.getEquippedStack(EquipmentSlot.MAINHAND);
            itemRenderer.renderItem(player, itemStack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, light);
            matrices.pop();
        }
        if (!player.isInvisible() && !EOEComponents.BODY_COMPONENT.get(player).hasBodyPart(BodyPart.LEFTARM)) {
            matrices.push();
            getContextModel().head.rotate(matrices);
            matrices.translate(0, -0.2, -0.5);
            ItemStack itemStack = player.getEquippedStack(EquipmentSlot.OFFHAND);
            itemRenderer.renderItem(player, itemStack, ModelTransformation.Mode.GROUND, false, matrices, vertexConsumers, light);
            matrices.pop();
        }
    }
}