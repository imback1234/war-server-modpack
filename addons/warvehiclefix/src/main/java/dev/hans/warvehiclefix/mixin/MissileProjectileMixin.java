package dev.hans.warvehiclefix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.atsuishio.superbwarfare.entity.projectile.MissileProjectile;

@Mixin(value = MissileProjectile.class, remap = false)
public abstract class MissileProjectileMixin {
    /**
     * Superb Warfare 0.8.9's public missile flags. AshVehicle's AGM-114, AGM-158, AIM-9, AIM-54, AIM-120 and R-60 read
     * and write them directly; 0.8.9.2 replaced them with private fields, so building one of those missiles threw
     * NoSuchFieldError on the server and crashed it.
     */
    public boolean distracted;
    public boolean lostTarget;

    /**
     * 0.8.9.2 reads the flag through this method. Pointing it at the restored field means AshVehicle's missile code and
     * Superb Warfare's own decoy code share one value again, as they did in 0.8.9.
     *
     * @author Hans
     * @reason one flag shared with AshVehicle's direct field access
     */
    @Overwrite
    public boolean isDistracted() {
        return this.distracted;
    }

    /**
     * @author Hans
     * @reason one flag shared with AshVehicle's direct field access
     */
    @Overwrite
    public void setDistracted(boolean value) {
        this.distracted = value;
    }

    /**
     * @author Hans
     * @reason one flag shared with AshVehicle's direct field access
     */
    @Overwrite
    public boolean isLostTarget() {
        return this.lostTarget;
    }

    /**
     * @author Hans
     * @reason one flag shared with AshVehicle's direct field access
     */
    @Overwrite
    public void setLostTarget(boolean value) {
        this.lostTarget = value;
    }
}
