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

//#if MC > 260000
import net.minecraft.client.gui.GuiGraphicsExtractor;
//#elseif MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11502
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

//#if MC > 11502
import net.minecraft.network.chat.CommonComponents;
//#else
//$$ import net.minecraft.client.resources.language.I18n;
//#endif

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;

//#if MC > 11502
import net.minecraft.network.chat.Component;
//#else
//$$ import net.minecraft.network.chat.TextComponent;
//#endif

import net.plastoid501.movement.gui.widget.ConfigWidget;

public class ConfigScreen extends Screen {
    private ConfigWidget configList;
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        //#if MC > 11601
        super(Component.nullToEmpty("Movement In GUI"));
        //#else
        //$$ super(new TextComponent("Movement In GUI"));
        //#endif

        this.parent = parent;
    }

    @Override
    protected void init() {
        this.configList = new ConfigWidget(this, this.minecraft);

        //#if MC > 11605
        this.addWidget(this.configList);
        //#else
        //$$ this.children.add(this.configList);
        //#endif

        //#if MC > 11902
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> {
            this.onClose();
        }).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
        //#elseif MC > 11605
        //$$ this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.DONE, button -> {
        //$$     this.close();
        //$$ }));
        //#elseif MC > 11502
        //$$ this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.DONE, button -> {
        //$$     this.onClose();
        //$$ }));
        //#else
        //$$ this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, I18n.translate("gui.done", new Object[0]), button -> {
        //$$     this.onClose();
        //$$ }));
        //#endif
    }

    @Override
    //#if MC > 260000
    public void extractRenderState(
    //#else
    //$$ public void render(
    //#endif
            //#if MC > 260000
            GuiGraphicsExtractor context,
            //#elseif MC > 11904
            //$$ GuiGraphics context,
            //#elseif MC > 11502
            //$$ PoseStack context,
            //#endif
            int mouseX, int mouseY, float delta
    ) {
        //#if MC > 260000
        super.extractRenderState(context, mouseX, mouseY, delta);
        //#elseif MC > 12001
        //$$ super.render(context, mouseX, mouseY, delta);
        //#endif

        //#if MC > 260000
        this.configList.extractRenderState(context, mouseX, mouseY, delta);
        //#elseif MC > 11502
        //$$ this.configList.render(context, mouseX, mouseY, delta);
        //#else
        //$$ this.configList.render(mouseX, mouseY, delta);
        //#endif

        //#if MC > 260000
        context.centeredText(this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11904
        //$$ context.drawCenteredString(this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11903
        //$$ drawCenteredString(context, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11605
        //$$ drawCenteredString(context, this.font, this.title.getVisualOrderText(), this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11502
        //$$ drawCenteredText(context, this.font, this.title, this.width / 2, 8, 0xFFFFFF);
        //#else
        //$$ drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 8, 0xFFFFFF);
        //#endif

        //#if MC > 12001
        //#elseif MC > 11502
        //$$ super.render(context, mouseX, mouseY, delta);
        //#else
        //$$ super.render(mouseX, mouseY, delta);
        //#endif
    }

    //#if MC > 260000
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    //#else
    //$$ @Override
    //$$ public boolean isPauseScreen() {
    //$$     return false;
    //$$ }
    //#endif

    //#if MC > 11502
    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }
    //#else
    //$$ @Override
    //$$ public void onClose() {
    //$$     if (this.minecraft != null) {
    //$$         this.minecraft.openScreen(this.parent);
    //$$     }
    //$$ }
    //#endif
}