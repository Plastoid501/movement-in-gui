package net.plastoid501.movement.mixin;

import com.terraformersmc.modmenu.gui.ModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.option.TelemetryInfoScreen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.plastoid501.movement.config.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    private boolean modifyTick(KeyBinding instance) {
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
                client.currentScreen instanceof GameOptionsScreen ||
                client.currentScreen instanceof GameMenuScreen ||
                client.currentScreen instanceof OptionsScreen ||
                client.currentScreen instanceof TelemetryInfoScreen ||
                client.currentScreen instanceof StatsScreen ||
                client.currentScreen instanceof OpenToLanScreen ||
                client.currentScreen instanceof ConfirmLinkScreen ||
                client.currentScreen instanceof PackScreen ||
                client.currentScreen instanceof CreditsAndAttributionScreen ||
                client.currentScreen instanceof CreditsScreen ||
                client.currentScreen instanceof ModsScreen
        ) {
            return false;
        }
        if (!Configs.inCreative.isEnable() && client.player != null && client.player.isCreative()) {
            return false;
        }
        if (!Configs.isMultiplayer.isEnable() && !client.isInSingleplayer()) {
            return false;
        }
        InputUtil.Key key = ((IKeyBindingMixin) instance).getBoundKey();
        if (!InputUtil.isKeyPressed(client.getWindow().getHandle(), key.getCode())) {
            return false;
        }

        return true;
    }
}
