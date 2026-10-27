package dev.hans.warvehiclefix.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.atsuishio.superbwarfare.tools.ProjectileTool;

/** Empty on purpose: it makes Mixin hand ProjectileTool to WarVehicleFixMixinPlugin, which adds the static methods. */
@Mixin(value = ProjectileTool.class, remap = false)
public abstract class ProjectileToolMixin {
}
