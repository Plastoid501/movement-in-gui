package net.plastoid501.movement.config.json;

public class JToggleConfig {
    private boolean enable;
    
    public JToggleConfig(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
