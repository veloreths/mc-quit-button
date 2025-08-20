package org.veloreths.quitbutton.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.veloreths.quitbutton.client.QuitButtonClient;
import org.veloreths.quitbutton.client.gui.widget.ConfirmButton;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title, boolean showMenu) {
        super(title);
        this.showMenu = showMenu;
    }

    @Shadow private final boolean showMenu;

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        if (QuitButtonClient.CONFIG.isQuitButton()) {
            this.addQuitButton();
            this.children().forEach(child -> {
                if (child instanceof ClickableWidget) {
                    ((ClickableWidget) child).y -= 15;
                }
            });
        }
    }

    private void addQuitButton() {
        int x = this.width / 2 - 102;
        int y = this.height / 4 + 130;

        if (QuitButtonClient.CONFIG.isQuitButtonConfirm()) {
            this.addButton(new ConfirmButton(
                    QuitButtonClient.CONFIG.getQuitButtonConfirmTime() * 1000.0,
                    x, y, 204, 20,
                    new TranslatableText("menu.quit"),
                    new TranslatableText("gui.done").append("?"),
                    this::quitGame
            ));
        } else {
            this.addButton(new ButtonWidget(
                    x, y, 204, 20,
                    new TranslatableText("menu.quit"),
                    button -> this.quitGame()
            ));
        }
    }

    private void quitGame() {
        if (this.client != null && this.client.world != null) {
            this.client.world.disconnect();
            this.client.disconnect();
            this.client.stop();
        }
    }

    /**
     *
     * Renders the game menu and adjusts the title position
     * depending on whether the quit button is enabled in the config.
     *
     * @author Veloreths
     * @reason To customize the rendering of the game menu title and position.
     *
     */
    @Overwrite
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int y = 40;

        if (this.showMenu) {
            if(QuitButtonClient.CONFIG.isQuitButton()) y = 20;
            this.renderBackground(matrices);
            drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, y, 16777215);
        } else {
            drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        }

        super.render(matrices, mouseX, mouseY, delta);
    }
}