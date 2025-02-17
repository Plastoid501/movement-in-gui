package net.plastoid501.movement.config;

import net.plastoid501.movement.config.json.JToggleConfig;

import java.util.LinkedHashMap;
import java.util.Map;

public class Configs {
    public static Map<String, ToggleConfig> toggles = new LinkedHashMap<>();
    public static Map<String, JToggleConfig> jToggles = new LinkedHashMap<>();

    public static ToggleConfig modEnable = new ToggleConfig("modEnable", "If true, this mod is enable", true);
    public static ToggleConfig inCreative = new ToggleConfig("inCreative", "If true, this mod is enable when creative mode.", true);
    public static ToggleConfig isAnvil = new ToggleConfig("isAnvil", "If true, this mod is enable when open anvil screen.", true);
    public static ToggleConfig isMultiplayer = new ToggleConfig("isMultiplayer", "If true, this mod is enable in multiplayer server.", true);

    public static ModConfig config;

    static {
        toggles.put(modEnable.getId(), modEnable);
        toggles.put(inCreative.getId(), inCreative);
        toggles.put(isAnvil.getId(), isAnvil);
        toggles.put(isMultiplayer.getId(), isMultiplayer);

        jToggles.put(modEnable.getId(), new JToggleConfig(modEnable.isEnable()));
        jToggles.put(inCreative.getId(), new JToggleConfig(inCreative.isEnable()));
        jToggles.put(isAnvil.getId(), new JToggleConfig(isAnvil.isEnable()));
        jToggles.put(isMultiplayer.getId(), new JToggleConfig(isMultiplayer.isEnable()));
    }


    public static Map<String, ToggleConfig> getToggles() {
        return toggles;
    }

    public static Map<String, JToggleConfig> getJToggles() {
        return jToggles;
    }
}
