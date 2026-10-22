package com.hans.sbwinfiniteammo.mixin;

import com.atsuishio.superbwarfare.entity.vehicle.base.VehicleEntity;
import com.atsuishio.superbwarfare.tools.InventoryTool;
import com.hans.sbwinfiniteammo.SbwInfiniteAmmo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Every vehicle counts as carrying a creative ammo box, which is how Superb Warfare models an endless reserve. */
@Mixin(value = InventoryTool.class, remap = false)
public abstract class InventoryToolMixin {
    @Inject(method = "hasCreativeAmmoBoxForVehicle", at = @At("HEAD"), cancellable = true, remap = false)
    private static void sbwinfiniteammo$infiniteReserve(VehicleEntity vehicle, CallbackInfoReturnable<Boolean> cir) {
        if (SbwInfiniteAmmo.enabled()) cir.setReturnValue(true);
    }
}
