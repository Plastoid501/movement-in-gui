package net.plastoid501.movement.mixin;

import io.github.prospector.modmenu.gui.ModListScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.options.*;
import net.minecraft.client.gui.screen.resourcepack.ResourcePackOptionsScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.plastoid501.movement.config.Configs;
import net.plastoid501.movement.gui.ConfigScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"))
    private boolean modifyTickMovement(KeyBinding instance) {
        if (instance.isPressed()) {
            return true;
        }
        if (!Configs.modEnable.isEnable()) {
            return false;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null ||
                client.currentScreen instanceof ChatScreen ||
                client.currentScreen instanceof AccessibilityScreen ||
                client.currentScreen instanceof ChatOptionsScreen ||
                client.currentScreen instanceof ControlsOptionsScreen ||
                client.currentScreen instanceof LanguageOptionsScreen ||
                client.currentScreen instanceof MouseOptionsScreen ||
                client.currentScreen instanceof SkinOptionsScreen ||
                client.currentScreen instanceof SoundOptionsScreen ||
                client.currentScreen instanceof GameMenuScreen ||
                client.currentScreen instanceof SettingsScreen ||
                //client.currentScreen instanceof TelemetryInfoScreen ||
                client.currentScreen instanceof StatsScreen ||
                client.currentScreen instanceof OpenToLanScreen ||
                client.currentScreen instanceof ConfirmScreen ||
                client.currentScreen instanceof ResourcePackOptionsScreen ||
                //client.currentScreen instanceof CreditsAndAttributionScreen ||
                client.currentScreen instanceof CreditsScreen ||
                client.currentScreen instanceof ModListScreen ||
                client.currentScreen instanceof ConfigScreen
        ) {
            return false;
        }
        if (!Configs.inCreative.isEnable() && client.player != null && client.player.isCreative()) {
            return false;
        }
        InputUtil.KeyCode key = ((IKeyBindingMixin) instance).getKeyCode();
        if (!InputUtil.isKeyPressed(client.window.getHandle(), key.getKeyCode())) {
            return false;
        }
        //KeyBinding.setKeyPressed(key, true);
        //KeyBinding.onKeyPressed(key);

        return true;
    }
}
