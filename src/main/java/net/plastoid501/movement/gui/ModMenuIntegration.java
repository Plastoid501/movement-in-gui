package net.plastoid501.movement.gui;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import net.plastoid501.movement.util.FileUtil;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        FileUtil.updateConfigs();
        return ConfigScreen::new;
    }
}
