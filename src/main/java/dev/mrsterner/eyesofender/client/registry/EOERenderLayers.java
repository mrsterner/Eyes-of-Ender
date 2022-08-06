package dev.mrsterner.eyesofender.client.registry;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import dev.mrsterner.eyesofender.EyesOfEnder;
import ladysnake.satin.mixin.client.render.RenderLayerAccessor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class EOERenderLayers extends RenderLayer {
	public EOERenderLayers(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
		super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
	}

	private static RenderLayer makeLayer(String name, VertexFormat format, VertexFormat.DrawMode mode, int bufSize, boolean hasCrumbling, boolean sortOnUpload, RenderLayer.MultiPhaseParameters glState) {
		return RenderLayerAccessor.satin$of(name, format, mode, bufSize, hasCrumbling, sortOnUpload, glState);
	}

	public static final Function<Identifier, RenderLayer> DUMMY_LAYER = Util.memoize(texture -> {
		RenderLayer.MultiPhaseParameters glState = RenderLayer.MultiPhaseParameters.builder()
				.shader(new RenderPhase.Shader(EOEShaders::dummy))
				.texture(new RenderPhase.Texture(texture, false, false))
				.transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
				.cull(DISABLE_CULLING)
				.lightmap(ENABLE_LIGHTMAP)
				.build(true);
		return makeLayer(EyesOfEnder.MODID + "dummy", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, glState);
	});
}
