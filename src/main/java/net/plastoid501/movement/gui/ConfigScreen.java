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

//#if MC > 11904
import net.minecraft.client.gui.DrawContext;
//#elseif MC > 11502
//$$ import net.minecraft.client.util.math.MatrixStack;
//#endif

//#if MC > 11502
import net.minecraft.screen.ScreenTexts;
//#else
//$$ import net.minecraft.client.resource.language.I18n;
//#endif

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;

//#if MC > 11502
import net.minecraft.text.Text;
//#else
//$$ import net.minecraft.text.LiteralText;
//#endif

import net.plastoid501.movement.gui.widget.ConfigWidget;

public class ConfigScreen extends Screen {
    private ConfigWidget configList;
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        //#if MC > 11601
        super(Text.of("Movement In GUI"));
        //#elseif MC > 11502
        //$$ super(Text.method_30163("Movement In GUI"));
        //#else
        //$$ super(new LiteralText("Movement In GUI"));
        //#endif

        this.parent = parent;
    }

    @Override
    protected void init() {
        //#if MC > 11502
        this.configList = new ConfigWidget(this, this.client);
        //#else
        //$$ this.configList = new ConfigWidget(this, this.minecraft);
        //#endif

        //#if MC > 11605
        this.addSelectableChild(this.configList);
        //#else
        //$$ this.children.add(this.configList);
        //#endif

        //#if MC > 11902
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            this.close();
        }).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
        //#elseif MC > 11605
        //$$ this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> {
        //$$     this.close();
        //$$ }));
        //#elseif MC > 11502
        //$$ this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> {
        //$$     this.onClose();
        //$$ }));
        //#else
        //$$ this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, I18n.translate("gui.done", new Object[0]), button -> {
        //$$     this.onClose();
        //$$ }));
        //#endif
    }

    @Override
    public void render(
            //#if MC > 11904
            DrawContext context,
            //#elseif MC > 11502
            //$$ MatrixStack context,
            //#endif
            int mouseX, int mouseY, float delta
    ) {
        //#if MC > 12001
        super.render(context, mouseX, mouseY, delta);
        //#endif

        //#if MC > 11502
        this.configList.render(context, mouseX, mouseY, delta);
        //#else
        //$$ this.configList.render(mouseX, mouseY, delta);
        //#endif

        //#if MC > 11904
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11903
        //$$ drawCenteredTextWithShadow(context, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11605
        //$$ drawCenteredTextWithShadow(context, this.textRenderer, this.title.asOrderedText(), this.width / 2, 8, 0xFFFFFF);
        //#elseif MC > 11502
        //$$ drawCenteredText(context, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
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

    //#if MC > 11701
    @Override
    public boolean shouldPause() {
        return false;
    }
    //#else
    //$$ @Override
    //$$ public boolean isPauseScreen() {
    //$$     return false;
    //$$ }
    //#endif

    //#if MC > 11701
    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }
    //#elseif MC > 11605
    //$$ @Override
    //$$ public void onClose() {
    //$$     if (this.client != null) {
    //$$         this.client.setScreen(this.parent);
    //$$     }
    //$$ }
    //#elseif MC > 11502
    //$$ @Override
    //$$ public void onClose() {
    //$$     if (this.client != null) {
    //$$         this.client.openScreen(this.parent);
    //$$     }
    //$$ }
    //#else
    //$$ @Override
    //$$ public void onClose() {
    //$$     if (this.minecraft != null) {
    //$$         this.minecraft.openScreen(this.parent);
    //$$     }
    //$$ }
    //#endif
}