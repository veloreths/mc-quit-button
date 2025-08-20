package org.veloreths.quitbutton.client.gui.widget;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;

public class ConfirmButton extends ButtonWidget {
    private final Text normalText;
    private final Text confirmText;
    private final Runnable onConfirm;
    private final int originalWidth;
    private final int originalX;
    private final double confirmationTimeout;
    private boolean waitingForConfirmation = false;
    private long confirmationStartTime = 0L;

    public ConfirmButton(double confirmationTimeout, int x, int y, int width, int height,
                         Text normalText, Text confirmText, Runnable onConfirm) {
        super(x, y, width, height, normalText, button -> {});
        this.normalText = normalText;
        this.confirmText = confirmText;
        this.onConfirm = onConfirm;
        this.originalWidth = width;
        this.originalX = x;
        this.confirmationTimeout = confirmationTimeout;
    }

    @Override
    public void onPress() {
        if (!waitingForConfirmation) {
            waitingForConfirmation = true;
            confirmationStartTime = System.currentTimeMillis();
            setMessage(confirmText);
        } else {
            if (onConfirm != null) onConfirm.run();
            resetButton();
        }
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderButton(matrices, mouseX, mouseY, delta);

        if (waitingForConfirmation) {
            fill(matrices, x, y, x + width, y + height, 0x80FF0000);
            if (System.currentTimeMillis() - confirmationStartTime >= confirmationTimeout) {
                resetButton();
            }
        }
    }

    private void resetButton() {
        waitingForConfirmation = false;
        setMessage(normalText);
        width = originalWidth;
        x = originalX;
        confirmationStartTime = 0L;
    }
}