package org.veloreths.quitbutton.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuitButtonConfig {
    private boolean quitButton = true;
    private boolean quitButtonConfirm = true;
    private double quitButtonConfirmTime = 2.0;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String MOD_CONFIG_DIR = "quitbutton";
    private static final String CONFIG_FILE = "config.json";

    public boolean isQuitButton() { return quitButton; }
    public boolean isQuitButtonConfirm() { return quitButtonConfirm; }
    public double getQuitButtonConfirmTime() { return quitButtonConfirmTime; }

    public void setQuitButton(boolean quitButton) { this.quitButton = quitButton; }
    public void setQuitButtonConfirm(boolean quitButtonConfirm) { this.quitButtonConfirm = quitButtonConfirm; }
    public void setQuitButtonConfirmTime(double seconds) { this.quitButtonConfirmTime = seconds; }

    public static QuitButtonConfig loadConfig(File gameDir) {
        File modConfigDir = new File(gameDir, "config/" + MOD_CONFIG_DIR);
        File configFile = new File(modConfigDir, CONFIG_FILE);

        if (!configFile.exists()) {
            return new QuitButtonConfig().saveAndReturn(modConfigDir);
        }

        try (FileReader reader = new FileReader(configFile)) {
            QuitButtonConfig config = gson.fromJson(reader, QuitButtonConfig.class);
            return config != null ? config : new QuitButtonConfig();
        } catch (Exception e) {
            e.printStackTrace();
            return new QuitButtonConfig();
        }
    }

    public void saveConfig(File modConfigDir) {
        try {
            if (!modConfigDir.exists()) {
                modConfigDir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(new File(modConfigDir, CONFIG_FILE))) {
                gson.toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private QuitButtonConfig saveAndReturn(File modConfigDir) {
        saveConfig(modConfigDir);
        return this;
    }
}