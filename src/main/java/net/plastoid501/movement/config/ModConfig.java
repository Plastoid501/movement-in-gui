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

import java.util.Map;

public class ModConfig {
    private Map<String, JToggleConfig> Toggles;

    public ModConfig(Map<String, JToggleConfig> Toggles) {
        this.Toggles = Toggles;
    }

    public Map<String, JToggleConfig> getToggles() {
        return Toggles;
    }

    public void setToggles(Map<String, JToggleConfig> toggles) {
        Toggles = toggles;
    }
}

