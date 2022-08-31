package dev.mrsterner.eyesofender.client.vertexconsumer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;

public class HamonVertexConsumerProvider implements VertexConsumerProvider {
    private final VertexConsumerProvider provider;

    public HamonVertexConsumerProvider(VertexConsumerProvider provider) {
        this.provider = provider;
    }


    @Override
    public VertexConsumer getBuffer(RenderLayer renderLayer) {
        return null;
    }
}
