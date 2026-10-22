package com.hans.sbwinfiniteammo.mixin;

import com.atsuishio.superbwarfare.data.gun.GunData;
import com.atsuishio.superbwarfare.entity.vehicle.base.VehicleEntity;
import com.atsuishio.superbwarfare.tools.InventoryTool;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Rounds loaded from an infinite vehicle reserve were never paid for, so unloading them must not hand out real items.
 * Injected after the magazine is emptied and before the rounds are returned to the supplier.
 */
@Mixin(value = GunData.class, remap = false)
public abstract class GunDataMixin {
    @Inject(method = "withdrawAmmo(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(value = "INVOKE", target = "Lcom/atsuishio/superbwarfare/data/gun/GunData;selectedAmmoConsumer$default(Lcom/atsuishio/superbwarfare/data/gun/GunData;Ljava/util/List;ILjava/lang/Object;)Lcom/atsuishio/superbwarfare/data/gun/AmmoConsumer;"),
            cancellable = true, remap = false)
    private void sbwinfiniteammo$keepFreeRounds(Entity ammoSupplier, CallbackInfo ci) {
        if (ammoSupplier instanceof VehicleEntity vehicle && InventoryTool.hasCreativeAmmoBoxForVehicle(vehicle)) ci.cancel();
    }
}
