package dev.hans.warvehiclefix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.atsuishio.superbwarfare.tools.CustomExplosion;
import com.atsuishio.superbwarfare.tools.ParticleTool;

@Mixin(value = CustomExplosion.Builder.class, remap = false)
public abstract class CustomExplosionBuilderMixin {
    @Shadow
    private ParticleTool.ParticleType particleType;

    /**
     * Superb Warfare 0.8.9's {@code Builder.damageMultiplier}. 0.8.9 used it only to add 20% damage per point to hostile
     * mobs; 0.8.9.2 removed that bonus from every explosion, so the value is accepted and not used. AshVehicle's nuke
     * bomb still calls it when it detonates.
     */
    public CustomExplosion.Builder damageMultiplier(float damageMultiplier) {
        return (CustomExplosion.Builder) (Object) this;
    }

    /**
     * AshVehicle's nuke bomb calls {@code withParticleType(null)}, which Superb Warfare rejects with a
     * NullPointerException that crashes the server (in 0.8.9 as well as 0.8.9.2). The builder's own default is null,
     * meaning "pick the particles from the radius", so null is stored the same way instead of throwing.
     */
    @Inject(method = "withParticleType", at = @At("HEAD"), cancellable = true)
    private void warvehiclefix$acceptNullParticleType(ParticleTool.ParticleType type, CallbackInfoReturnable<CustomExplosion.Builder> cir) {
        if (type == null) {
            this.particleType = null;
            cir.setReturnValue((CustomExplosion.Builder) (Object) this);
        }
    }
}
