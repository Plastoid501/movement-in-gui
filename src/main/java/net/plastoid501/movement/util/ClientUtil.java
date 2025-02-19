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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;

//#if MC > 11903
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.TelemetryInfoScreen;
//#endif

//#if MC <= 11404
//$$ import net.minecraft.client.gui.screen.options.*;
//#endif

//#if MC > 11603
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
//#elseif MC > 11404
//$$ import net.minecraft.client.gui.screen.options.GameOptionsScreen;
//#endif

//#if MC > 11603
import net.minecraft.client.gui.screen.option.OptionsScreen;
//#elseif MC > 11502
//$$ import net.minecraft.client.gui.screen.options.OptionsScreen;
//#endif

//#if MC > 11601
import net.minecraft.client.gui.screen.pack.PackScreen;
//#elseif MC > 11502
//$$ import net.minecraft.client.gui.screen.pack.ResourcePackScreen;
//#else
//$$ import net.minecraft.client.gui.screen.resourcepack.ResourcePackOptionsScreen;
//#endif

//#if MC > 11603
import net.minecraft.client.option.KeyBinding;
//#else
//$$ import net.minecraft.client.options.KeyBinding;
//#endif

import net.minecraft.client.util.InputUtil;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.mixin.IKeyBindingMixin;
import org.lwjgl.glfw.GLFW;

public class ClientUtil {
    public static boolean test(KeyBinding instance) {
        if (instance.isPressed()) {
            return true;
        }
        if (!Configs.modEnable.isEnable()) {
            return false;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null ||
                client.currentScreen instanceof BookEditScreen ||
                client.currentScreen instanceof SignEditScreen ||
                (client.currentScreen instanceof AnvilScreen && !Configs.isAnvil.isEnable()) ||
                client.currentScreen instanceof ChatScreen ||
                client.currentScreen instanceof CreditsScreen ||
                client.currentScreen instanceof GameMenuScreen ||
                client.currentScreen instanceof StatsScreen ||
                client.currentScreen instanceof OpenToLanScreen ||

                //#if MC > 11904
                client.currentScreen instanceof TelemetryInfoScreen ||
                client.currentScreen instanceof CreditsAndAttributionScreen ||
                //#endif

                //#if MC > 11404
                client.currentScreen instanceof GameOptionsScreen ||
                //#else
                //$$ client.currentScreen instanceof AccessibilityScreen ||
                //$$ client.currentScreen instanceof ChatOptionsScreen ||
                //$$ client.currentScreen instanceof ControlsOptionsScreen ||
                //$$ client.currentScreen instanceof LanguageOptionsScreen ||
                //$$ client.currentScreen instanceof MouseOptionsScreen ||
                //$$ client.currentScreen instanceof SkinOptionsScreen ||
                //$$ client.currentScreen instanceof SoundOptionsScreen ||
                //#endif

                //#if MC > 11502
                client.currentScreen instanceof OptionsScreen ||
                //#else
                //$$ client.currentScreen instanceof SettingsScreen ||
                //#endif

                //#if MC > 11802
                client.currentScreen instanceof ConfirmLinkScreen ||
                //#else
                //$$ client.currentScreen instanceof ConfirmScreen ||
                //#endif

                //#if MC > 11601
                client.currentScreen instanceof PackScreen ||
                //#elseif MC > 11502
                //$$ client.currentScreen instanceof ResourcePackScreen ||
                //#else
                //$$ client.currentScreen instanceof ResourcePackOptionsScreen ||
                //#endif

                //#if MC > 11501
                client.currentScreen instanceof ModsScreen
                //#else
                //$$ client.currentScreen instanceof ModListScreen
                //#endif
        ) {
            return false;
        }
        if (!Configs.inCreative.isEnable() && client.player != null && client.player.isCreative()) {
            return false;
        }
        if (!Configs.isMultiplayer.isEnable() && !client.isInSingleplayer()) {
            return false;
        }
        if (checkKey(instance)) {
            return false;
        }
        InputUtil.Key key = ((IKeyBindingMixin) instance).getBoundKey();
        if (
                !InputUtil.isKeyPressed(
                        //#if MC > 11404
                        client.getWindow().getHandle(),
                        //#else
                        //$$ client.window.getHandle(),
                        //#endif
                        //#if MC > 11502
                        key.getCode()
                        //#else
                        //$$ key.getKeyCode()
                        //#endif
                )
        ) {
            return false;
        }
        //#if MC > 12002
        KeyBinding.setKeyPressed(key, true);
        KeyBinding.onKeyPressed(key);
        //#endif

        return true;
    }

    public static boolean checkKey(KeyBinding instance) {
        switch (instance.getDefaultKey().getCode()) {
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
