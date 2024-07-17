package net.plastoid501.movement.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"))
    private boolean modifyTick(KeyBinding instance) {
        if (instance.isPressed()) {
            return true;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null) {
            return false;
        }
        InputUtil.KeyCode key = ((IKeyBindingMixin) instance).getKeyCode();
        if (!InputUtil.isKeyPressed(client.window.getHandle(), key.getKeyCode())) {
            return false;
        }
        KeyBinding.setKeyPressed(key, true);
        KeyBinding.onKeyPressed(key);

        return true;
    }
}
