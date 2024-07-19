package net.plastoid501.movement.gui;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;
import net.plastoid501.movement.MovementInGUI;
import net.plastoid501.movement.util.FileUtil;

import java.util.function.Function;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public String getModId() {
        return MovementInGUI.MOD_ID;
    }

    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        FileUtil.updateConfigs();
        return ConfigScreen::new;
    }
}
