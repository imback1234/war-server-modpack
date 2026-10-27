package dev.hans.warvehiclefix.mixin;

import org.spongepowered.asm.mixin.Mixin;

import com.atsuishio.superbwarfare.client.RenderHelper;

/** Empty on purpose: it makes Mixin hand RenderHelper to WarVehicleFixMixinPlugin, which adds isInGui(). */
@Mixin(value = RenderHelper.class, remap = false)
public abstract class RenderHelperMixin {
}
