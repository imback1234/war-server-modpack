package dev.hans.warvehiclefix;

import com.atsuishio.superbwarfare.tools.ProjectileTool;
import com.atsuishio.superbwarfare.tools.RenderDistanceHelper;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;

/**
 * The bodies of the static Superb Warfare 0.8.9 functions that {@link WarVehicleFixMixinPlugin} adds back. Each method
 * here has exactly the name and parameters of the removed function, and the added function only forwards to it.
 */
public final class LegacyApi {
    private LegacyApi() {
    }

    /**
     * {@code ProjectileTool.causeCustomExplode(projectile, damage, radius, damageMultiplier)}, used by AshVehicle's
     * CBU-87, GBU-57 and toilet bomb.
     *
     * <p>0.8.9 used {@code damageMultiplier} only to add 20% damage per point to hostile mobs. 0.8.9.2 removed that
     * bonus from every explosion, so the value is accepted and not used; damage to players and vehicles is the same.
     */
    public static void causeCustomExplode(ThrowableItemProjectile projectile, float damage, float radius, float damageMultiplier) {
        ProjectileTool.causeCustomExplode(projectile, damage, radius);
    }

    /**
     * {@code ProjectileTool.causeCustomExplode(projectile, source, target, damage, radius)}, used by AshVehicle's
     * AGM-114, AGM-158, AIM-9, AIM-54, AIM-120 and R-60 missiles. 0.8.9.2 has the same function for any projectile.
     */
    public static void causeCustomExplode(ThrowableItemProjectile projectile, DamageSource source, Entity target, float damage, float radius) {
        ProjectileTool.causeCustomExplode(projectile, source, target, damage, radius);
    }

    /**
     * {@code RenderHelper.isInGui()}: whether a model was drawn in a menu during the last 100 ms. 0.8.9.2 moved the same
     * check, unchanged, to {@code RenderDistanceHelper}.
     */
    public static boolean isInGui() {
        return RenderDistanceHelper.isInGui();
    }
}
