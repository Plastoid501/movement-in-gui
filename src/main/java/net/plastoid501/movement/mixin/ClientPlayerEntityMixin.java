package net.plastoid501.movement.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    private boolean modifyTickMovement(KeyBinding instance) {
        if (instance.isPressed()) {
            return true;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen == null) {
            return false;
        }
        InputUtil.Key key = ((IKeyBindingMixin) instance).getBoundKey();
        if (!InputUtil.isKeyPressed(client.getWindow().getHandle(), key.getCode())) {
            return false;
        }
        KeyBinding.setKeyPressed(key, true);
        KeyBinding.onKeyPressed(key);

        return true;
    }
}
