package dev.hans.warvehiclefix.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.atsuishio.superbwarfare.entity.vehicle.base.VehicleEntity;

@Mixin(value = VehicleEntity.class, remap = false)
public abstract class VehicleEntityMixin {
    /**
     * Superb Warfare 0.8.9's {@code gearRot(partialTicks)}: the landing-gear angle in degrees, smoothed from the previous
     * tick's angle to the current one. 0.8.9.2 removed this method and the previous-tick angle, but still keeps the
     * current angle in {@code gearRot}, so that is what this returns. AshVehicle 4.6's aircraft models call it every
     * frame they are drawn; without it they crash the game.
     */
    public float gearRot(float partialTicks) {
        return ((VehicleEntity) (Object) this).getGearRot();
    }
}
