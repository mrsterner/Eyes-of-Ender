package dev.mrsterner.eyesofender.client.vertexconsumer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.client.AuraEffectManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.Identifier;

public class HamonVertexConsumerProvider implements VertexConsumerProvider {
	private final int r;
	private final int g;
	private final int b;
	private final int a;
	private final VertexConsumerProvider provider;

	public HamonVertexConsumerProvider(VertexConsumerProvider provider, int r, int g, int b, int a) {
		this.provider = provider;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	@Override
	public VertexConsumer getBuffer(RenderLayer layer) {
		if (layer.getAffectedOutline().isPresent()) {
			return new HamonVertexConsumer(provider.getBuffer(AuraEffectManager.getRenderLayer(layer)), r, g, b, a);
		} else {
			return new EmptyVertexConsumer();
		}
	}

	public VertexConsumer getBuffer() {
		return new HamonVertexConsumer(provider.getBuffer(AuraEffectManager.getRenderLayer()), r, g, b, a);
	}


	public VertexConsumer getBuffer(Identifier texture) {
		return new HamonVertexConsumer(provider.getBuffer(AuraEffectManager.getRenderLayer(texture)), r, g, b, a);
	}
}
