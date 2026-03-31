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

package net.plastoid501.movement.util;


//#if MC > 11603
import com.terraformersmc.modmenu.gui.ModsScreen;
//#elseif MC > 11501
//$$ import io.github.prospector.modmenu.gui.ModsScreen;
//#else
//$$ import io.github.prospector.modmenu.gui.ModListScreen;
//#endif
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;

//#if MC > 11903
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
//#endif

//#if MC > 11603
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
//#elseif MC > 11404
//$$ import net.minecraft.client.gui.screens.OptionsSubScreen;
//#endif

import net.minecraft.client.gui.screens.options.OptionsScreen;

//#if MC > 11601
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
//#elseif MC > 11502
//$$ import net.minecraft.client.gui.screens.ResourcePackSelectScreen;
//#else
//$$ import net.minecraft.client.gui.screens.resourcepacks.ResourcePackSelectScreen;
//#endif

import net.minecraft.client.KeyMapping;

import com.mojang.blaze3d.platform.InputConstants;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.mixin.IKeyBindingMixin;
import org.lwjgl.glfw.GLFW;

public class ClientUtil {
    public static boolean test(KeyMapping instance) {
        if (instance.isDown()) {
            return true;
        }
        if (!Configs.modEnable.isEnable()) {
            return false;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.screen == null ||
                client.screen instanceof BookEditScreen ||
                client.screen instanceof SignEditScreen ||
                (client.screen instanceof AnvilScreen && !Configs.isAnvil.isEnable()) ||
                client.screen instanceof ChatScreen ||
                client.screen instanceof WinScreen ||
                client.screen instanceof PauseScreen ||
                client.screen instanceof StatsScreen ||
                client.screen instanceof ShareToLanScreen ||

                //#if MC > 11904
                client.screen instanceof TelemetryInfoScreen ||
                client.screen instanceof CreditsAndAttributionScreen ||
                //#endif

                //#if MC > 11404
                client.screen instanceof OptionsSubScreen ||
                //#else
                //$$ client.screen instanceof AccessibilityOptionsScreen ||
                //$$ client.screen instanceof ChatOptionsScreen ||
                //$$ client.screen instanceof ControlsScreen ||
                //$$ client.screen instanceof LanguageSelectScreen ||
                //$$ client.screen instanceof MouseSettingsScreen ||
                //$$ client.screen instanceof SkinCustomizationScreen ||
                //$$ client.screen instanceof SoundOptionsScreen ||
                //#endif

                client.screen instanceof OptionsScreen ||

                client.screen instanceof ConfirmLinkScreen ||

                //#if MC > 11601
                client.screen instanceof PackSelectionScreen ||
                //#else
                //$$ client.screen instanceof ResourcePackSelectScreen ||
                //#endif

                //#if MC > 11501
                client.screen instanceof ModsScreen
                //#else
                //$$ client.screen instanceof ModListScreen
                //#endif
        ) {
            return false;
        }
        if (!Configs.inCreative.isEnable() && client.player != null && client.player.isCreative()) {
            return false;
        }
        if (!Configs.isMultiplayer.isEnable() && !client.isLocalServer()) {
            return false;
        }
        if (checkKey(instance)) {
            return false;
        }
        InputConstants.Key key = ((IKeyBindingMixin) instance).getBoundKey();
        if (
                !InputConstants.isKeyDown(
                        //#if MC > 12108
                        client.getWindow(),
                        //#else
                        //$$ client.getWindow().getWindow(),
                        //#endif
                        key.getValue()
                )
        ) {
            return false;
        }
        //#if MC > 12002
        KeyMapping.set(key, true);
        KeyMapping.click(key);
        //#endif

        return true;
    }

    public static boolean checkKey(KeyMapping instance) {
        switch (instance.getDefaultKey().getValue()) {
            case GLFW.GLFW_KEY_W:
                return !Configs.forwardKey.isEnable();
            case GLFW.GLFW_KEY_S:
                return !Configs.backwardKey.isEnable();
            case GLFW.GLFW_KEY_A:
                return !Configs.leftKey.isEnable();
            case GLFW.GLFW_KEY_D:
                return !Configs.rightKey.isEnable();
            case GLFW.GLFW_KEY_SPACE:
                return !Configs.jumpKey.isEnable();
            case GLFW.GLFW_KEY_LEFT_SHIFT:
                return !Configs.sneakKey.isEnable();
            case GLFW.GLFW_KEY_LEFT_CONTROL:
                return !Configs.sprintKey.isEnable();
            default:
                return false;
        }
    }
}
