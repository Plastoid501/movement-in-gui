package net.plastoid501.movement.config;

import net.plastoid501.movement.config.json.JToggleConfig;

public class ToggleConfig extends JToggleConfig {
    private String id;
    private String narrator;

    public ToggleConfig(String id, String narrator, Boolean enable) {
        super(enable);
        this.id = id;
        this.narrator = narrator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }
}
