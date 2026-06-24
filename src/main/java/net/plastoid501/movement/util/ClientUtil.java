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
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;

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
import net.plastoid501.movement.mixin.IAbstractRecipeBookScreenMixin;
import net.plastoid501.movement.mixin.ICreativeModeInventoryScreen;
import net.plastoid501.movement.mixin.IKeyBindingMixin;
import net.plastoid501.movement.mixin.IRecipeBookComponentMixin;
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
        Screen screen =
                //#if MC > 260102
                client.gui.screen();
                //#else
                //$$ client.screen;
                //#endif
        if (screen == null ||
                screen instanceof BookEditScreen ||
                screen instanceof SignEditScreen ||
                (screen instanceof AnvilScreen && !Configs.isAnvil.isEnable()) ||
                screen instanceof ChatScreen ||
                screen instanceof WinScreen ||
                screen instanceof PauseScreen ||
                screen instanceof StatsScreen ||
                //#if MC > 260102
                screen instanceof MultiplayerOptionsScreen ||
                //#else
                //$$ screen instanceof ShareToLanScreen ||
                //#endif

                //#if MC > 11904
                screen instanceof TelemetryInfoScreen ||
                screen instanceof CreditsAndAttributionScreen ||
                //#endif

                //#if MC > 11404
                screen instanceof OptionsSubScreen ||
                //#else
                //$$ screen instanceof AccessibilityOptionsScreen ||
                //$$ screen instanceof ChatOptionsScreen ||
                //$$ screen instanceof ControlsScreen ||
                //$$ screen instanceof LanguageSelectScreen ||
                //$$ screen instanceof MouseSettingsScreen ||
                //$$ screen instanceof SkinCustomizationScreen ||
                //$$ screen instanceof SoundOptionsScreen ||
                //#endif

                screen instanceof OptionsScreen ||

                screen instanceof ConfirmLinkScreen ||

                //#if MC > 11601
                screen instanceof PackSelectionScreen ||
                //#else
                //$$ screen instanceof ResourcePackSelectScreen ||
                //#endif

                //#if MC > 11501
                screen instanceof ModsScreen
                //#else
                //$$ screen instanceof ModListScreen
                //#endif
        ) {
            return false;
        }
        //#if MC > 12106
        if (screen instanceof AbstractRecipeBookScreen abstractRecipeBookScreen) {
            RecipeBookComponent<?> recipeBookComponent = ((IAbstractRecipeBookScreenMixin) abstractRecipeBookScreen).getRecipeBookComponent();
            EditBox searchBox = ((IRecipeBookComponentMixin) recipeBookComponent).getSearchBox();
            if (searchBox != null && searchBox.isActive() && searchBox.isFocused()) {
                return false;
            }
        }
        //#else
        //$$ if (screen instanceof InventoryScreen inventoryScreen) {
        //$$     RecipeBookComponent recipeBookComponent = ((IAbstractRecipeBookScreenMixin) inventoryScreen).getRecipeBookComponent();
        //$$     EditBox searchBox = ((IRecipeBookComponentMixin) recipeBookComponent).getSearchBox();
        //$$     if (searchBox != null && searchBox.isActive() && searchBox.isFocused()) {
        //$$         return false;
        //$$     }
        //$$ }
        //#endif

        if (screen instanceof CreativeModeInventoryScreen creativeModeInventoryScreen) {
            EditBox searchBox = ((ICreativeModeInventoryScreen) creativeModeInventoryScreen).getSearchBox();
            if (searchBox != null && searchBox.isActive() && searchBox.isFocused()) {
                return false;
            }
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
