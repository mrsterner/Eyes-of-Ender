package dev.mrsterner.eyesofender.mixin.client;

import com.mojang.blaze3d.shader.ShaderStage;
import com.mojang.datafixers.util.Pair;
import dev.mrsterner.eyesofender.client.registry.EOEShaders;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {



	@Inject(method = "loadShaders", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", remap = false), locals = LocalCapture.CAPTURE_FAILHARD)
	private void loadShaders(ResourceManager resourceManager, CallbackInfo ci, List<ShaderStage> _programsToClose, List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shadersToLoad)
			throws IOException {
		EOEShaders.init(resourceManager, shadersToLoad);
	}
}
