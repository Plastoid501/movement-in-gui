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


package net.plastoid501.movement.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
//#if MC > 11603
import net.minecraft.client.option.KeyBinding;
//#else
//$$ import net.minecraft.client.options.KeyBinding;
//#endif
import net.plastoid501.movement.util.ClientUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    //#if MC < 12105
    //$$ @Redirect(
    //$$         method = "tickMovement",
    //$$         at = @At(
    //$$                 value = "INVOKE",
    //$$                 //#if MC > 11603
    //$$                 target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"
    //$$                 //#else
    //$$                 //$$ target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"
    //$$                 //#endif
    //$$         )
    //$$ )
    //$$ private boolean modifyTickMovement(KeyBinding instance) {
    //$$     return ClientUtil.test(instance);
    //$$ }
    //#endif
}
