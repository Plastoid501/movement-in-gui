package net.plastoid501.movement.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.plastoid501.movement.gui.widget.ConfigWidget;

public class ConfigScreen extends Screen {
    private ConfigWidget configList;
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(new LiteralText("Movement In GUI"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.configList = new ConfigWidget(this, this.minecraft);
        this.children.add(this.configList);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, I18n.translate("gui.done", new Object[0]), (button) -> {
            this.onClose();
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.configList.render(mouseX, mouseY, delta);
        drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 8, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.openScreen(this.parent);
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
