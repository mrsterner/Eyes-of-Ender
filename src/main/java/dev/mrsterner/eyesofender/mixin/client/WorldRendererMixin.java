package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.common.utils.TimeStopUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow @Final private ClientWorld world;


    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;renderParticles(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/client/render/Camera;F)V"))
    private float eyesOfEnder$doNotInterpolateParticles (float tickDelta) {
        ClientWorld world = MinecraftClient.getInstance().world;
        return world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world)) ? 0 : tickDelta;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V"))
    private float eyesOfEnder$doNotInterpolateWeather (float tickDelta) {
        ClientWorld world = MinecraftClient.getInstance().world;
        return world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world)) ? 0 : tickDelta;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderEffect;render(F)V"))
    private float eyesOfEnder$doNotInterpolateWeatherShader (float tickDelta) {
        ClientWorld world = MinecraftClient.getInstance().world;
        return world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world)) ? 0 : tickDelta;
    }

    @Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
    private void eyesOfEnder$doNotMakeRainSplashes (Camera camera, CallbackInfo ci) {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(TimeStopUtils.getTimeStopper(world))){
            ci.cancel();
        }
    }

    @ModifyArgs(method = "renderEntity(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;render(Lnet/minecraft/entity/Entity;DDDFFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void eyesOfEnder$doNotDeltaTickEntityWhenTimeIsStopped(Args args) {
        Entity entity = args.get(0);
        if(world != null && TimeStopUtils.getTimeStoppedTicks(world) > 0 && TimeStopUtils.isInRangeOfTimeStop(entity)){
            if(entity instanceof PlayerEntity){
                /*
                if(StandUtils.getStand((PlayerEntity) entity) != Stand.THE_WORLD && StandUtils.getStand((PlayerEntity) entity) != Stand.STAR_PLATINUM){
                    args.set(5, 0.0F);
                }
                 */
            }else {
                args.set(5, 0.0F);
            }
        }
    }
}