package org.veloreths.quitbutton.mixin;

import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.veloreths.quitbutton.client.QuitButtonClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsScreenMixin {
    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void addCustomOptionsToStaticArray(CallbackInfo ci) {
        Option[] original = AccessibilityOptionsScreenAccessor.getOptions();
        List<Option> optionsList = new ArrayList<>(Arrays.asList(original));

        optionsList.add(new BooleanOption(
                "option.quitbutton.quitButton",
                gameOptions -> QuitButtonClient.CONFIG.isQuitButton(),
                (gameOptions, value) -> {
                    QuitButtonClient.CONFIG.setQuitButton(value);
                    QuitButtonClient.saveConfig();
                }
        ));

        optionsList.add(new BooleanOption(
                "option.quitbutton.quitConfirm",
                gameOptions -> QuitButtonClient.CONFIG.isQuitButtonConfirm(),
                (gameOptions, value) -> {
                    QuitButtonClient.CONFIG.setQuitButtonConfirm(value);
                    QuitButtonClient.saveConfig();
                }
        ));

        optionsList.add(new DoubleOption(
                "option.quitbutton.quitConfirmTime",
                0.5, 64.0, 0.1f,
                gameOptions -> QuitButtonClient.CONFIG.getQuitButtonConfirmTime(),
                (gameOptions, value) -> {
                    QuitButtonClient.CONFIG.setQuitButtonConfirmTime(value);
                    QuitButtonClient.saveConfig();
                },
                (opts, option) -> new TranslatableText("option.quitbutton.quitConfirmTime")
                        .append(": ")
                        .append(new LiteralText(String.format("%.1f s", option.get(opts))))
        ));

        AccessibilityOptionsScreenAccessor.setOptions(optionsList.toArray(new Option[0]));
    }
}