package net.plastoid501.movement.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.plastoid501.movement.util.FileUtil;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        FileUtil.updateConfigs();
        return ConfigScreen::new;
    }
}
