package net.plastoid501.movement.gui.widget;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.config.ModConfig;
import net.plastoid501.movement.config.ToggleConfig;
import net.plastoid501.movement.config.json.JToggleConfig;
import net.plastoid501.movement.gui.ConfigScreen;
import net.plastoid501.movement.util.FileUtil;

import java.awt.*;
import java.util.Collections;
import java.util.List;


@Environment(EnvType.CLIENT)
public class ConfigWidget extends ElementListWidget<ConfigWidget.Entry> {
    final ConfigScreen parent;
    private final MinecraftClient client;
    private final ModConfig CONFIG = Configs.config;

    public ConfigWidget(ConfigScreen parent, MinecraftClient client) {
        super(client, parent.width + 155, parent.height, 20, parent.height - 32, 23);
        //super(client, parent.width + 155, parent.height - 52, 20, 23);
        this.parent = parent;
        this.client = client;
        this.initEntries(client);
    }

    private void initEntries(MinecraftClient client) {
        if (CONFIG != null) {
            this.addEntry(new CategoryEntry(Text.of("-- Toggle --"), client.textRenderer));
            Configs.getToggles().keySet().forEach((key) -> this.addEntry(new ToggleEntry(key, client.textRenderer, CONFIG)));
            this.addEntry(new CategoryEntry(Text.of(""), client.textRenderer));
        }
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 85;
    }

    public int getRowWidth() {
        return super.getRowWidth() + 150;
    }

    public void update() {
        this.updateChildren();
    }

    public void updateChildren() {
        this.children().forEach(Entry::update);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.client.player == null || this.client.world == null) {
            this.setRenderBackground(true);
        } else {
            this.setRenderBackground(false);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    public class CategoryEntry extends Entry {
        private final TextRenderer textRenderer;
        private final Text text;
        private final int textWidth;
        CategoryEntry(Text CategoryName, TextRenderer textRenderer) {
            this.textRenderer = textRenderer;
            this.text = CategoryName;
            this.textWidth = textRenderer.getWidth(this.text);
        }

        @Override
        public List<? extends Element> children() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return Collections.emptyList();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            //this.text.setPos(ConfigWidget.this.client.currentScreen.width / 2 - this.textWidth / 2, y + 5);
            //this.text.render(matrices, mouseX, mouseY, tickDelta);
            this.textRenderer.draw(matrices, this.text, ConfigWidget.this.client.currentScreen.width / 2 - this.textWidth / 2, y + 5, Color.WHITE.getRGB());
        }

        @Override
        void update() {
        }

    }

    public class ToggleEntry extends Entry {
        private final TextRenderer textRenderer;
        private final ToggleConfig defaultConfig;
        private boolean enable;
        private final Text text;
        private final ButtonWidget enableButton;
        private final ButtonWidget resetButton;

        ToggleEntry(String key, TextRenderer textRenderer, ModConfig config) {
            this.textRenderer = textRenderer;
            this.defaultConfig = Configs.getToggles().get(key);
            this.enable = config.getToggles().get(key).isEnable();
            this.text = Text.of(key);
            //this.text.setTooltip(Tooltip.of(Text.literal(this.defaultConfig.getNarrator())));
            this.enableButton = new ButtonWidget(0, 0, 60, 20, Text.of(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())), button -> {
                this.enable = !this.enable;
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            });
            this.resetButton = new ButtonWidget(0, 0, 40, 20, Text.of("RESET"), button -> {
                this.enable = this.defaultConfig.isEnable();
                FileUtil.updateToggleConfig(key, new JToggleConfig(this.enable));
                this.update();
            });
            this.resetButton.active = this.defaultConfig.isEnable() != this.enable;

        }

        @Override
        public List<? extends Element> children() {
            return ImmutableList.of(this.enableButton, this.resetButton);
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return ImmutableList.of(this.enableButton, this.resetButton);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.textRenderer.draw(matrices, this.text, x - 60, y + 5, Color.WHITE.getRGB());
            this.enableButton.x = x + 190;
            this.enableButton.y = y;
            this.enableButton.render(matrices, mouseX, mouseY, tickDelta);
            this.resetButton.x = x + 253;
            this.resetButton.y = y;
            this.resetButton.render(matrices, mouseX, mouseY, tickDelta);
        }

        @Override
        void update() {
            this.enableButton.setMessage(Text.of(this.enable ? "ON" : "OFF").copy().setStyle(Style.EMPTY.withColor(this.enable ? Color.GREEN.getRGB() : Color.red.getRGB())));
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


