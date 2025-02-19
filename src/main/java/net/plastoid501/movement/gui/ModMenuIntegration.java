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

package net.plastoid501.movement.gui;

//#if MC > 11603
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
//#elseif MC > 11501
//$$ import io.github.prospector.modmenu.api.ConfigScreenFactory;
//$$ import io.github.prospector.modmenu.api.ModMenuApi;
//#else
//$$ import io.github.prospector.modmenu.api.ModMenuApi;
//$$ import net.minecraft.client.gui.screen.Screen;
//$$ import net.plastoid501.movement.MovementInGUI;
//$$ import java.util.function.Function;
//#endif
import net.plastoid501.movement.util.FileUtil;

public class ModMenuIntegration implements ModMenuApi {
    //#if MC > 11501
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        FileUtil.updateConfigs();
        return ConfigScreen::new;
    }
    //#else
    //$$ @Override
    //$$ public String getModId() {
    //$$     return MovementInGUI.MOD_ID;
    //$$ }
    //$$
    //$$ @Override
    //$$ public Function<Screen, ? extends Screen> getConfigScreenFactory() {
    //$$     FileUtil.updateConfigs();
    //$$     return ConfigScreen::new;
    //$$ }
    //#endif
}
