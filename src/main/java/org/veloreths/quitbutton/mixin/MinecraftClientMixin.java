package org.veloreths.quitbutton.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.veloreths.quitbutton.client.QuitButtonClient;

import java.io.File;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private File runDirectory;

    @Inject(method = "run", at = @At("HEAD"))
    private void onRunStart(CallbackInfo ci) {
        QuitButtonClient.initialize();
    }

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void onDisconnect(CallbackInfo ci) {
        if (QuitButtonClient.CONFIG != null) {
            QuitButtonClient.CONFIG.saveConfig(this.runDirectory);
        }
    }

    @Inject(method = "scheduleStop", at = @At("HEAD"))
    private void onScheduleStop(CallbackInfo ci) {
        if (QuitButtonClient.CONFIG != null) {
            QuitButtonClient.CONFIG.saveConfig(this.runDirectory);
        }
    }
}