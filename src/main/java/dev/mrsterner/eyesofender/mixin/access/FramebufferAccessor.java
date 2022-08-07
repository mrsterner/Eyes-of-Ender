package dev.mrsterner.eyesofender.mixin.access;


import com.mojang.blaze3d.framebuffer.Framebuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Framebuffer.class)
public interface FramebufferAccessor {
	@Accessor
	float[] getClearColor();
}
