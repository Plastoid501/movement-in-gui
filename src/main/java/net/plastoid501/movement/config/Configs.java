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

package net.plastoid501.movement.config;

import net.plastoid501.movement.config.json.JToggleConfig;

import java.util.*;

public class Configs {
    public static Map<String, ToggleConfig> toggles = new LinkedHashMap<>();
    public static Map<String, JToggleConfig> jToggles = new LinkedHashMap<>();

    public static ToggleConfig modEnable = new ToggleConfig("modEnable", "If true, this mod is enable", true);
    public static ToggleConfig inCreative = new ToggleConfig("inCreative", "If true, this mod is enable when creative mode.", true);
    public static ToggleConfig isAnvil = new ToggleConfig("isAnvil", "If true, this mod is enable when open anvil screen.", true);
    public static ToggleConfig isMultiplayer = new ToggleConfig("isMultiplayer", "If true, this mod is enable in multiplayer server.", true);

    public static ToggleConfig forwardKey = new ToggleConfig("forwardKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig backwardKey = new ToggleConfig("backwardKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig leftKey = new ToggleConfig("leftKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig rightKey = new ToggleConfig("rightKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig jumpKey = new ToggleConfig("jumpKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig sneakKey = new ToggleConfig("sneakKey", "If true, this mod is enable this key while open gui.", true);
    public static ToggleConfig sprintKey = new ToggleConfig("sprintKey", "If true, this mod is enable this key while open gui.", true);




    public static ModConfig config;

    static {
        toggles.put(modEnable.getId(), modEnable);
        toggles.put(inCreative.getId(), inCreative);
        toggles.put(isAnvil.getId(), isAnvil);
        toggles.put(isMultiplayer.getId(), isMultiplayer);
        toggles.put(forwardKey.getId(), forwardKey);
        toggles.put(backwardKey.getId(), backwardKey);
        toggles.put(leftKey.getId(), leftKey);
        toggles.put(rightKey.getId(), rightKey);
        toggles.put(jumpKey.getId(), jumpKey);
        toggles.put(sneakKey.getId(), sneakKey);
        toggles.put(sprintKey.getId(), sprintKey);

        jToggles.put(modEnable.getId(), new JToggleConfig(modEnable.isEnable()));
        jToggles.put(inCreative.getId(), new JToggleConfig(inCreative.isEnable()));
        jToggles.put(isAnvil.getId(), new JToggleConfig(isAnvil.isEnable()));
        jToggles.put(isMultiplayer.getId(), new JToggleConfig(isMultiplayer.isEnable()));
        jToggles.put(forwardKey.getId(), new JToggleConfig(forwardKey.isEnable()));
        jToggles.put(backwardKey.getId(), new JToggleConfig(backwardKey.isEnable()));
        jToggles.put(leftKey.getId(), new JToggleConfig(leftKey.isEnable()));
        jToggles.put(rightKey.getId(), new JToggleConfig(rightKey.isEnable()));
        jToggles.put(jumpKey.getId(), new JToggleConfig(jumpKey.isEnable()));
        jToggles.put(sneakKey.getId(), new JToggleConfig(sneakKey.isEnable()));
        jToggles.put(sprintKey.getId(), new JToggleConfig(sprintKey.isEnable()));

        config = new ModConfig(jToggles);
    }


    public static Map<String, ToggleConfig> getToggles() {
        return toggles;
    }

    public static Map<String, JToggleConfig> getJToggles() {
        return jToggles;
    }
}