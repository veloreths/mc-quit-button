package org.veloreths.quitbutton.mixin;

import net.minecraft.client.option.Option;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AccessibilityOptionsScreen.class)
public interface AccessibilityOptionsScreenAccessor {
    @Accessor("OPTIONS")
    @Mutable
    static Option[] getOptions() {
        throw new AssertionError();
    }

    @Accessor("OPTIONS")
    @Mutable
    static void setOptions(Option[] options) {
        throw new AssertionError();
    }
}