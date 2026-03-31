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

package net.plastoid501.movement.gui.widget;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.font.TextRenderer;
//import net.minecraft.client.gui.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
//import net.minecraft.client.gui.widget.*;
//import net.minecraft.text.Style;
//import net.minecraft.text.Text;

//#if MC <= 11502
//$$ import net.minecraft.text.LiteralText;
//#endif

//#if MC > 11605
//#elseif MC > 11502
//$$ import net.minecraft.text.TextColor;
//#else
//$$ import net.minecraft.util.Formatting;
//#endif

//#if MC > 260000
import net.minecraft.client.gui.GuiGraphicsExtractor;
//#elseif MC > 11904
//$$ import net.minecraft.client.gui.GuiGraphics;
//#elseif MC > 11502
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//#endif

//#if MC > 11902
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
//#endif


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.config.ModConfig;
import net.plastoid501.movement.config.ToggleConfig;
import net.plastoid501.movement.config.json.JToggleConfig;
import net.plastoid501.movement.gui.ConfigScreen;
import net.plastoid501.movement.util.FileUtil;

import java.awt.*;
import java.util.List;


@Environment(EnvType.CLIENT)
public class ConfigWidget extends ContainerObjectSelectionList<ConfigWidget.Entry> {
    final ConfigScreen parent;
    private final Minecraft client;
    private final ModConfig CONFIG = Configs.config;

    public ConfigWidget(ConfigScreen parent, Minecraft client) {
        //#if MC > 12002
        super(client, parent.width + 155, parent.height - 52, 20, 23);
        //#else
        //$$ super(client, parent.width + 155, parent.height, 20, parent.height - 32, 23);
        //#endif
        this.parent = parent;
        this.client = client;
        this.initEntries(client);
    }

    private void initEntries(Minecraft client) {
        if (CONFIG != null) {
            //#if MC > 11601
            this.addEntry(new CategoryEntry(Component.nullToEmpty("-- Toggle --"), client.font));
            //#else
            //$$ this.addEntry(new CategoryEntry(new TextComponent("-- Toggle --"), client.font));
            //#endif

            Configs.getToggles().keySet().forEach(key -> this.addEntry(new ToggleEntry(key, client.font, CONFIG)));

            //#if MC > 11601
            this.addEntry(new CategoryEntry(Component.nullToEmpty(""), client.font));
            //#else
            //$$ this.addEntry(new CategoryEntry(new TextComponent(""), client.font));
            //#endif
        }
    }

    //#if MC > 12004
    //#else
    //$$ @Override
    //$$ protected int getScrollbarPosition() {
    //$$     return super.getScrollbarPosition() + 85;
    //$$ }
    //#endif

    public int getRowWidth() {
        return super.getRowWidth() + 150;
    }

    public void update() {
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(Entry::update);
    }

    //#if MC > 12004
    //#elseif MC > 12002
    //$$ @Override
    //$$ public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
    //$$     if (this.client == null || this.client.player == null || this.client.level == null) {
    //$$         this.setRenderBackground(true);
    //$$     } else {
    //$$         this.setRenderBackground(false);
    //$$     }
    //$$     super.renderWidget(context, mouseX, mouseY, delta);
    //$$ }
    //#elseif MC > 11904
    //$$ @Override
    //$$ public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
    //$$     if (this.client == null || this.client.player == null || this.client.level == null) {
    //$$         this.setRenderBackground(true);
    //$$     } else {
    //$$         this.setRenderBackground(false);
    //$$     }
    //$$     super.render(context, mouseX, mouseY, delta);
    //$$ }
    //#elseif MC > 11605
    //$$ @Override
    //$$ public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
    //$$     if (this.client == null || this.client.player == null || this.client.level == null) {
    //$$         this.setRenderBackground(true);
    //$$     } else {
    //$$         this.setRenderBackground(false);
    //$$     }
    //$$     super.render(matrices, mouseX, mouseY, delta);
    //$$ }
    //#elseif MC > 11603
    //$$ @Override
    //$$ public void render(PoseStack matrices, int mouseX, int mouseY, float delta) {
    //$$     if (this.client == null || this.client.player == null || this.client.level == null) {
    //$$         this.method_31322(true);
    //$$     } else {
    //$$         this.method_31322(false);
    //$$     }
    //$$     super.render(matrices, mouseX, mouseY, delta);
    //$$ }
    //#elseif  MC > 11502
    //$$ @Override
    //$$ public void renderBackground(PoseStack matrices) {
    //$$ }
    //#else
    //$$ @Override
    //$$ public void renderBackground() {
    //$$ }
    //#endif

    public class CategoryEntry extends Entry {
        //#if MC > 11902
        private final StringWidget text;
        //#else
        //$$ private final Text text;
        //$$ private final Font font;
        //#endif
        private final int textWidth;

        CategoryEntry(Component CategoryName, Font font) {
            //#if MC > 11902
            this.text = new StringWidget(CategoryName, font);
            this.textWidth = this.text.getWidth();
            //#else
            //$$ this.text = CategoryName;
            //$$ this.font = font;
                //#if MC > 11502
                //$$ this.textWidth = font.width(this.text);
                //#else
                //$$ this.textWidth = font.wordWrapHeight(this.text.asFormattedString());
                //#endif
            //#endif
        }

        @Override
        public List<? extends GuiEventListener> children() {
            //#if MC > 11902
            return ImmutableList.of(this.text);
            //#else
            //$$ return Collections.emptyList();
            //#endif
        }

        //#if MC > 11605
        @Override
        public List<? extends NarratableEntry> narratables() {
            //#if MC > 11902
            return ImmutableList.of(this.text);
            //#else
            //$$ return Collections.emptyList();
            //#endif
        }
        //#endif

        //#if MC > 260000
        @Override
        public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.text.setPosition(ConfigWidget.this.client.screen.width / 2 - this.textWidth / 2, getY() + 5);
            this.text.extractWidgetRenderState(context, mouseX, mouseY, tickDelta);
        }
        //#else
        //$$ @Override
        //$$ public void renderContent(
                     //#if MC > 11904
                     //$$ GuiGraphics context,
                    //#elseif MC > 11502
                    //$$ PoseStack context,
                    //#endif
                     //#if MC > 12108
                     //#else
                     //$$ int index, int y, int x, int entryWidth, int entryHeight,
                     //#endif
        //$$         int mouseX, int mouseY, boolean hovered, float tickDelta
        //$$ ) {
                 //#if MC > 12108
                 //$$ this.text.setPosition(ConfigWidget.this.client.screen.width / 2 - this.textWidth / 2, getY() + 5);
                 //$$ this.text.render(context, mouseX, mouseY, tickDelta);
                 //#elseif MC > 11902
                 //$$ this.text.setPosition(ConfigWidget.this.client.screen.width / 2 - this.textWidth / 2, y + 5);
                 //$$ this.text.render(context, mouseX, mouseY, tickDelta);
                 //#elseif MC > 11502
                 //$$ this.font.draw(context, this.text, ConfigWidget.this.client.screen.width / 2 - this.textWidth / 2, y + 5, Color.WHITE.getRGB());
                 //#else
                 //$$ this.font.draw(this.text.asFormattedString(), ConfigWidget.this.client.screen.width / 2 - this.textWidth / 2, y + 5, Color.WHITE.getRGB());
                 //#endif
        //$$ }
        //#endif

        @Override
        void update() {
        }
    }

    public class ToggleEntry extends Entry {
        private final Font font;
        private final ToggleConfig defaultConfig;
        private boolean enable;
        //#if MC > 11902
        private final StringWidget text;
        //#else
        //$$ private final Component text;
        //#endif
        private final Button enableButton;
        private final Button resetButton;

        ToggleEntry(String key, Font font, ModConfig config) {
            this.font = font;
            this.defaultConfig = Configs.getToggles().get(key);
            this.enable = config.getToggles().get(key).isEnable();

            //#if MC > 11902
            this.text = new StringWidget(Component.literal(key), font);
            this.text.setTooltip(Tooltip.create(Component.literal(this.defaultConfig.getNarrator())));
            //#elseif MC > 11601
            //$$ this.text = Component.literal(key);
            //#else
            //$$ this.text = new TextComponent(key);
            //#endif

            //#if MC > 11902
            this.enableButton = Button.builder(Component.literal(this.enable ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())), button -> {
                this.enable = !this.enable;
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            }).size(60, 20).build();
            this.resetButton = Button.builder(Component.literal("RESET"), button -> {
                this.enable = this.defaultConfig.isEnable();
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            }).size(40, 20).build();
            //#elseif MC > 11802
            //$$ this.enableButton = new ButtonWidget(0, 0, 60, 20, Text.literal(this.enable ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())), button -> {
            //$$     this.enable = !this.enable;
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton = new ButtonWidget(0, 0, 40, 20, Text.literal("RESET"), button -> {
            //$$     this.enable = this.defaultConfig.isEnable();
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //#elseif MC > 11605
            //$$ this.enableButton = new ButtonWidget(0, 0, 60, 20, Text.of(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())), button -> {
            //$$     this.enable = !this.enable;
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton = new ButtonWidget(0, 0, 40, 20, Text.of("RESET"), button -> {
            //$$     this.enable = this.defaultConfig.isEnable();
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //#elseif MC > 11601
            //$$ this.enableButton = new ButtonWidget(0, 0, 60, 20, Text.of(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(TextColor.fromRgb(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB()))), button -> {
            //$$     this.enable = !this.enable;
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton = new ButtonWidget(0, 0, 40, 20, Text.of("RESET"), button -> {
            //$$     this.enable = this.defaultConfig.isEnable();
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton.active = this.defaultConfig.isEnable() != this.enable;
            //#elseif MC > 11502
            //$$ this.enableButton = new ButtonWidget(0, 0, 60, 20, Text.method_30163(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(TextColor.fromRgb(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB()))), button -> {
            //$$     this.enable = !this.enable;
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton = new ButtonWidget(0, 0, 40, 20, Text.method_30163("RESET"), button -> {
            //$$     this.enable = this.defaultConfig.isEnable();
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //#else
            //$$ this.enableButton = new ButtonWidget(0, 0, 60, 20, (new LiteralText(this.enable ? "ON" : "OFF").copy().setStyle((new Style()).setColor(this.enable ? Formatting.GREEN : Formatting.RED))).asFormattedString(), button -> {
            //$$     this.enable = !this.enable;
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //$$ this.resetButton = new ButtonWidget(0, 0, 40, 20, "RESET", button -> {
            //$$     this.enable = this.defaultConfig.isEnable();
            //$$     FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
            //$$     this.update();
            //$$ });
            //#endif

            this.resetButton.active = this.defaultConfig.isEnable() != this.enable;

        }

        @Override
        public List<? extends GuiEventListener> children() {
            //#if MC > 11902
            return ImmutableList.of(this.text, this.enableButton, this.resetButton);
            //#else
            //$$ return ImmutableList.of(this.enableButton, this.resetButton);
            //#endif
        }

        //#if MC > 11605
        @Override
        public List<? extends NarratableEntry> narratables() {
            //#if MC > 11902
            return ImmutableList.of(this.text, this.enableButton, this.resetButton);
            //#else
            //$$ return ImmutableList.of(this.enableButton, this.resetButton);
            //#endif
        }
        //#endif


        //#if MC > 260000
        @Override
        public void extractContent(GuiGraphicsExtractor context, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.text.setPosition(getX() - 60, getY() + 5);
            this.text.extractWidgetRenderState(context, mouseX, mouseY, tickDelta);
            this.enableButton.setPosition(getX() + 190, getY());
            this.resetButton.setPosition(getX() + 253, getY());
            this.enableButton.extractRenderState(context, mouseX, mouseY, tickDelta);
            this.resetButton.extractRenderState(context, mouseX, mouseY, tickDelta);
        }
        //#else
        //$$ @Override
        //$$ public void renderContent(
                //#if MC > 11904
                //$$ GuiGraphics context,
                //#elseif MC > 11502
                //$$ PoseStack context,
                //#endif
                //#if MC > 12108
                //#else
                //$$ int index, int y, int x, int entryWidth, int entryHeight,
                //#endif
                //$$ int mouseX, int mouseY, boolean hovered, float tickDelta
        //$$ ) {
            //#if MC > 12108
            //$$ this.text.setPosition(getX() - 60, getY() + 5);
            //$$ this.text.render(context, mouseX, mouseY, tickDelta);
            //#elseif MC > 11902
            //$$ this.text.setPosition(x - 60, y + 5);
            //$$ this.text.render(context, mouseX, mouseY, tickDelta);
            //#elseif MC > 11502
            //$$ this.textRenderer.draw(context, this.text, x - 60, y + 5, Color.WHITE.getRGB());
            //#else
            //$$ this.textRenderer.draw(this.text.asFormattedString(), x - 60, y + 5, Color.WHITE.getRGB());
            //#endif

            //#if MC > 12108
            //$$ this.enableButton.setPosition(getX() + 190, getY());
            //$$ this.resetButton.setPosition(getX() + 253, getY());
            //#elseif MC > 11902
            //$$ this.enableButton.setPosition(x + 190, y);
            //$$ this.resetButton.setPosition(x + 253, y);
            //#else
            //$$ this.enableButton.x = x + 190;
            //$$ this.enableButton.y = y;
            //$$ this.resetButton.x = x + 253;
            //$$ this.resetButton.y = y;
            //#endif

            //#if MC > 11502
            //$$ this.enableButton.render(context, mouseX, mouseY, tickDelta);
            //$$ this.resetButton.render(context, mouseX, mouseY, tickDelta);
            //#else
            //$$ this.enableButton.render(mouseX, mouseY, tickDelta);
            //$$ this.resetButton.render(mouseX, mouseY, tickDelta);
            //#endif
            //$$ }
        //#endif

        @Override
        void update() {
            //this.enableButton.setMessage(Text.literal(this.enable ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())));
            //#if MC > 11605
            this.enableButton.setMessage(Component.nullToEmpty(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())));
            //#else
            //$$ this.enableButton.setMessage((new TextComponent(this.enable ? "ON" : "OFF").copy().setStyle((new Style()).setColor(this.enable ? Formatting.GREEN : Formatting.RED))).asFormattedString());
            //#endif

            this.resetButton.active = this.defaultConfig.isEnable() != this.enable;
        }

    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ContainerObjectSelectionList.Entry<Entry> {
        public Entry() {
        }

        abstract void update();
    }
}


