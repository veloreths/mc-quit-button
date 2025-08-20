package org.veloreths.quitbutton.client;

import net.minecraft.client.MinecraftClient;
import java.io.File;
import org.veloreths.quitbutton.client.config.QuitButtonConfig;

public class QuitButtonClient {
    public static QuitButtonConfig CONFIG;

    public static void initialize() {
        File gameDir = MinecraftClient.getInstance().runDirectory;
        CONFIG = QuitButtonConfig.loadConfig(gameDir);
    }

    public static void saveConfig() {
        if (CONFIG != null) {
            File gameDir = MinecraftClient.getInstance().runDirectory;
            File modConfigDir = new File(gameDir, "config/quitbutton");
            CONFIG.saveConfig(modConfigDir);
        }
    }
}