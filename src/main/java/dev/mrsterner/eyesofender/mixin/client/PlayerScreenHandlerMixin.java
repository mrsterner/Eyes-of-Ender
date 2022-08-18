package dev.mrsterner.eyesofender.mixin.client;

import dev.mrsterner.eyesofender.api.enums.BodyPart;
import dev.mrsterner.eyesofender.common.registry.EOEComponents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends ScreenHandler {
    protected PlayerScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Shadow private PlayerEntity owner;

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Inject(method = "canInsertIntoSlot(Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;)Z", at = @At("RETURN"), cancellable = true)
    private void eyesOfEnder$canInsertSlot(ItemStack stack, Slot slot, CallbackInfoReturnable<Boolean> cir){
        EOEComponents.BODY_COMPONENT.maybeGet(owner).filter(bodyComponent -> !bodyComponent.hasBodyPart(BodyPart.LEFTARM) && bodyComponent.getBodyPartLevel(BodyPart.LEFTARM) == 0).ifPresent(bodyComponent -> {
            cir.setReturnValue(false);
            cir.cancel();
        });
    }
}

