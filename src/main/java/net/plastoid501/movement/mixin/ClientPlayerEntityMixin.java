package net.plastoid501.movement.mixin;

import io.github.prospector.modmenu.gui.ModsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.screen.pack.PackScreen;
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
                client.currentScreen instanceof GameOptionsScreen ||
                client.currentScreen instanceof GameMenuScreen ||
                client.currentScreen instanceof OptionsScreen ||
                //client.currentScreen instanceof TelemetryInfoScreen ||
                client.currentScreen instanceof StatsScreen ||
                client.currentScreen instanceof OpenToLanScreen ||
                client.currentScreen instanceof ConfirmScreen ||
                client.currentScreen instanceof PackScreen ||
                //client.currentScreen instanceof CreditsAndAttributionScreen ||
                client.currentScreen instanceof CreditsScreen ||
                client.currentScreen instanceof ModsScreen ||
                client.currentScreen instanceof ConfigScreen
        ) {
            return false;
        }
        if (!Configs.inCreative.isEnable() && client.player != null && client.player.isCreative()) {
            return false;
        }
        InputUtil.Key key = ((IKeyBindingMixin) instance).getBoundKey();
        if (!InputUtil.isKeyPressed(client.getWindow().getHandle(), key.getCode())) {
            return false;
        }
        //KeyBinding.setKeyPressed(key, true);
        //KeyBinding.onKeyPressed(key);

        return true;
    }
}
