package net.plastoid501.movement.gui.widget;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.config.ModConfig;
import net.plastoid501.movement.config.ToggleConfig;
import net.plastoid501.movement.config.json.JToggleConfig;
import net.plastoid501.movement.gui.ConfigScreen;
import net.plastoid501.movement.util.FileUtil;

import java.awt.*;
import java.util.List;


@Environment(EnvType.CLIENT)
public class ConfigWidget extends ElementListWidget<ConfigWidget.Entry> {
    final ConfigScreen parent;
    private final MinecraftClient client;
    private final ModConfig CONFIG = Configs.config;

    public ConfigWidget(ConfigScreen parent, MinecraftClient client) {
        //super(client, parent.width + 155, parent.height, 20, parent.height - 32, 23);
        super(client, parent.width + 155, parent.height - 52, 20, 23);
        this.parent = parent;
        this.client = client;
        this.initEntries(client);
    }

    private void initEntries(MinecraftClient client) {
        if (CONFIG != null) {
            this.addEntry(new CategoryEntry(Text.literal("-- Toggle --"), client.textRenderer));
            Configs.getToggles().keySet().forEach((key) -> this.addEntry(new ToggleEntry(key, client.textRenderer, CONFIG)));
            this.addEntry(new CategoryEntry(Text.literal(""), client.textRenderer));
        }
    }

    /*
    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 85;
    }
     */

    public int getRowWidth() {
        return super.getRowWidth() + 150;
    }

    public void update() {
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(Entry::update);
    }

    /*
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.client.player == null || this.client.world == null) {
            this.setRenderBackground(true);
        } else {
            this.setRenderBackground(false);
        }
        super.render(context, mouseX, mouseY, delta);
    }

     */

    public class CategoryEntry extends Entry {
        private final TextWidget text;
        private final int textWidth;
        CategoryEntry(Text CategoryName, TextRenderer textRenderer) {
            this.text = new TextWidget(CategoryName, textRenderer);
            this.textWidth = this.text.getWidth();
        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(this.text);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.text);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.text.setPosition(ConfigWidget.this.client.currentScreen.width / 2 - this.textWidth / 2, y + 5);
            this.text.render(context, mouseX, mouseY, tickDelta);
        }

        @Override
        void update() {
        }

    }

    public class ToggleEntry extends Entry {
        private final ToggleConfig defaultConfig;
        private boolean enable;
        private final TextWidget text;
        private final ButtonWidget enableButton;
        private final ButtonWidget resetButton;

        ToggleEntry(String key, TextRenderer textRenderer, ModConfig config) {
            this.defaultConfig = Configs.getToggles().get(key);
            this.enable = config.getToggles().get(key).isEnable();
            this.text = new TextWidget(Text.literal(key), textRenderer);
            this.text.setTooltip(Tooltip.of(Text.literal(this.defaultConfig.getNarrator())));
            this.enableButton = ButtonWidget.builder(Text.literal(this.enable ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())), button -> {
                this.enable = !this.enable;
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            }).size(60, 20).build();
            this.resetButton = ButtonWidget.builder(Text.literal("RESET"), button -> {
                this.enable = this.defaultConfig.isEnable();
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            }).size(40, 20).build();
            this.resetButton.active = this.defaultConfig.isEnable() != this.enable;

        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(this.text, this.enableButton, this.resetButton);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.text, this.enableButton, this.resetButton);
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.text.setPosition(x - 60, y + 5);
            this.text.render(context, mouseX, mouseY, tickDelta);
            this.enableButton.setPosition(x + 190, y);
            this.enableButton.render(context, mouseX, mouseY, tickDelta);
            this.resetButton.setPosition(x + 253, y);
            this.resetButton.render(context, mouseX, mouseY, tickDelta);
        }

        @Override
        void update() {
            this.enableButton.setMessage(Text.literal(this.enable ? "ON" : "OFF").setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())));
            this.resetButton.active = this.defaultConfig.isEnable() != this.enable;
        }

    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ElementListWidget.Entry<Entry> {
        public Entry() {
        }

        abstract void update();
    }
}


