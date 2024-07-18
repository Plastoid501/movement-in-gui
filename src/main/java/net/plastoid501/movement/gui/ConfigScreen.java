package net.plastoid501.movement.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.plastoid501.movement.gui.widget.ConfigWidget;

public class ConfigScreen extends Screen {
    private ConfigWidget configList;
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Movement In GUI"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.configList = new ConfigWidget(this, this.client);
        this.addSelectableChild(this.configList);
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            this.close();
        }).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.configList.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    /*
    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client == null || this.client.player == null || this.client.world == null) {
            this.renderBackgroundTexture(context);
        }
    }

     */

}
