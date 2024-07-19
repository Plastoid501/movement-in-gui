package net.plastoid501.movement.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.plastoid501.movement.MovementInGUI;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.config.ModConfig;
import net.plastoid501.movement.config.ToggleConfig;
import net.plastoid501.movement.config.json.JToggleConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileUtil {
    public static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void generateClientModConfig() {
        Path path = FileUtil.getConfigPath().resolve(MovementInGUI.MOD_ID + ".json");
        if (Files.exists(path)){
            return;
        }

        Map<String, JToggleConfig> toggles = Configs.getJToggles();

        ModConfig config = new ModConfig(toggles);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(config);


        try (FileWriter writer = new FileWriter(path.toString())) {
            writer.write(json);
        } catch (IOException e) {
            MovementInGUI.LOGGER.error(e.getMessage());
        }
    }

    public static void generateClientModConfig(ModConfig config) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(config);

        Path path = FileUtil.getConfigPath().resolve(MovementInGUI.MOD_ID + ".json");

        if (Files.notExists(path)){
            try (FileWriter writer = new FileWriter(path.toString())) {
                writer.write(json);
            } catch (IOException e) {
                MovementInGUI.LOGGER.error(e.getMessage());
            }
        }
    }

    public static ModConfig readConfig() {
        Path path = FileUtil.getConfigPath().resolve(MovementInGUI.MOD_ID + ".json");
        Gson gson = new Gson();
        ModConfig config;

        try {
            String jsonContent = Files.readString(path);
            config = gson.fromJson(jsonContent, ModConfig.class);
        } catch (IOException | JsonSyntaxException e) {
            MovementInGUI.LOGGER.error(e.getMessage());
            return Configs.config;
        }

        if (config == null) {
            return Configs.config;
        }

        return config;
    }

    public static void saveConfig(ModConfig config) {
        Path path = FileUtil.getConfigPath().resolve(MovementInGUI.MOD_ID + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            MovementInGUI.LOGGER.error(e.getMessage());
        }

        FileUtil.updateConfigs();
    }

    public static void updateToggleConfig(String target, JToggleConfig toggle) {
        ModConfig config = readConfig();
        Map<String, JToggleConfig> toggleConfigMap = config.getToggles();
        toggleConfigMap.replace(target, toggle);
        saveConfig(config);
    }

    public static void updateConfigs() {
        ModConfig config = FileUtil.readConfig();
        if (config != null) {
            if (config.getToggles() == null) {
                return;
            }

            boolean flag = false;

            JToggleConfig toggleConfig = config.getToggles().get(Configs.modEnable.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.modEnable.isEnable());
                config.getToggles().put(Configs.modEnable.getId(), toggleConfig);
            }
            Configs.modEnable = new ToggleConfig(Configs.modEnable.getId(), Configs.modEnable.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.inCreative.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.inCreative.isEnable());
                config.getToggles().put(Configs.inCreative.getId(), toggleConfig);
            }
            Configs.inCreative = new ToggleConfig(Configs.inCreative.getId(), Configs.inCreative.getNarrator(), toggleConfig.isEnable());

            if (flag) {
                return;
            }
            Configs.config = config;

        }
    }
}
