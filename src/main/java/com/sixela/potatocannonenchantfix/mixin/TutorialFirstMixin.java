package com.sixela.potatocannonenchantfix.mixin;

import com.sixela.potatocannonenchantfix.PotatoCannonEnchantFix;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class TutorialFirstMixin {

    @Inject(method = "loadLevel", at = @At(value = "HEAD"))
    private void logOnWorldLoad(CallbackInfo ci) {
        PotatoCannonEnchantFix.LOGGER.info("MinecraftServer$loadLevel has started!");

    }
}
