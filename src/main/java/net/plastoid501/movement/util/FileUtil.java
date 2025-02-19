/*
 * This file is part of the MovementInGUI project, licensed under the
 * GNU Lesser General Public License v3.0
 *
 * Copyright (C) 2025  Plastoid501 and contributors
 *
 * MovementInGUI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MovementInGUI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MovementInGUI.  If not, see <https://www.gnu.org/licenses/>.
 */

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
                saveConfig(Configs.config);
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

            toggleConfig = config.getToggles().get(Configs.isAnvil.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.isAnvil.isEnable());
                config.getToggles().put(Configs.isAnvil.getId(), toggleConfig);
            }
            Configs.isAnvil = new ToggleConfig(Configs.isAnvil.getId(), Configs.isAnvil.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.isMultiplayer.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.isMultiplayer.isEnable());
                config.getToggles().put(Configs.isMultiplayer.getId(), toggleConfig);
            }
            Configs.isMultiplayer = new ToggleConfig(Configs.isMultiplayer.getId(), Configs.isMultiplayer.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.forwardKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.forwardKey.isEnable());
                config.getToggles().put(Configs.forwardKey.getId(), toggleConfig);
            }
            Configs.forwardKey = new ToggleConfig(Configs.forwardKey.getId(), Configs.forwardKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.backwardKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.backwardKey.isEnable());
                config.getToggles().put(Configs.backwardKey.getId(), toggleConfig);
            }
            Configs.backwardKey = new ToggleConfig(Configs.backwardKey.getId(), Configs.backwardKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.leftKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.leftKey.isEnable());
                config.getToggles().put(Configs.leftKey.getId(), toggleConfig);
            }
            Configs.leftKey = new ToggleConfig(Configs.leftKey.getId(), Configs.leftKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.rightKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.rightKey.isEnable());
                config.getToggles().put(Configs.rightKey.getId(), toggleConfig);
            }
            Configs.rightKey = new ToggleConfig(Configs.rightKey.getId(), Configs.rightKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.jumpKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.jumpKey.isEnable());
                config.getToggles().put(Configs.jumpKey.getId(), toggleConfig);
            }
            Configs.jumpKey = new ToggleConfig(Configs.jumpKey.getId(), Configs.jumpKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.sneakKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.sneakKey.isEnable());
                config.getToggles().put(Configs.sneakKey.getId(), toggleConfig);
            }
            Configs.sneakKey = new ToggleConfig(Configs.sneakKey.getId(), Configs.sneakKey.getNarrator(), toggleConfig.isEnable());

            toggleConfig = config.getToggles().get(Configs.sprintKey.getId());
            if (toggleConfig == null) {
                flag = true;
                toggleConfig = new JToggleConfig(Configs.sprintKey.isEnable());
                config.getToggles().put(Configs.sprintKey.getId(), toggleConfig);
            }
            Configs.sprintKey = new ToggleConfig(Configs.sprintKey.getId(), Configs.sprintKey.getNarrator(), toggleConfig.isEnable());

            if (flag) {
                saveConfig(config);
                return;
            }
            Configs.config = config;

        }
    }
}