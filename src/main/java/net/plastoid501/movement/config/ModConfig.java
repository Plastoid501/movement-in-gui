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

