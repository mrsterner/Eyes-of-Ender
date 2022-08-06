package dev.mrsterner.eyesofender.client.registry;

import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceManager;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import static com.mojang.blaze3d.vertex.VertexFormats.*;


public class EOEShaders {
	private static ShaderProgram dummy;

	public static void init(ResourceManager resourceManager, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> registrations) throws IOException {
		registrations.add(Pair.of(
				new ShaderProgram(resourceManager, "eoe__dummy", POSITION_COLOR_TEXTURE_LIGHT_NORMAL),
				inst -> dummy = inst)
		);
	}
	public static ShaderProgram dummy() {
		return dummy;
	}
}
