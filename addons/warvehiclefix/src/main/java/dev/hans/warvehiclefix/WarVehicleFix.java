package dev.hans.warvehiclefix;

import com.mojang.logging.LogUtils;

import net.minecraftforge.fml.common.Mod;

/**
 * War Server Vehicle Fix.
 *
 * <p>AshVehicle 4.6 was built against Superb Warfare 0.8.9. The server runs Superb Warfare 0.8.9.2, which removed five
 * functions and two fields AshVehicle still uses. Java only notices a missing member at the moment it is used, so
 * AshVehicle loads fine and then throws {@link NoSuchMethodError} or {@link NoSuchFieldError} when it reaches one:
 * <ul>
 * <li>{@code VehicleEntity.gearRot(float)}: 26 AshVehicle aircraft models animate their landing gear with it, so the
 * game crashes the first time one of those planes is drawn, in the Vehicle Assembling Table preview or in the
 * world;</li>
 * <li>{@code RenderHelper.isInGui()}: AshVehicle's base vehicle model;</li>
 * <li>{@code CustomExplosion.Builder.damageMultiplier(float)}: the nuke bomb;</li>
 * <li>two {@code ProjectileTool.causeCustomExplode} overloads: AshVehicle's missiles and bombs when they explode;</li>
 * <li>the {@code MissileProjectile.distracted} and {@code lostTarget} fields: six AshVehicle missiles, from the moment
 * one is created, which crashes the server.</li>
 * </ul>
 * This mod puts all seven back, each behaving as it did in 0.8.9 on top of 0.8.9.2's code. The instance members are
 * added by mixins in {@code mixin/}; the three static functions are added by {@link WarVehicleFixMixinPlugin} and run
 * the code in {@link LegacyApi}.
 *
 * <p>It also fixes one AshVehicle bug that crashes the server on any Superb Warfare version: the nuke bomb passes null
 * to {@code CustomExplosion.Builder.withParticleType}, which is now treated as Superb Warfare's own default (null).
 */
@Mod(WarVehicleFix.MOD_ID)
public final class WarVehicleFix {
    public static final String MOD_ID = "warvehiclefix";

    public WarVehicleFix() {
        LogUtils.getLogger().info("War Server Vehicle Fix: restored the 7 Superb Warfare 0.8.9 members AshVehicle 4.6 uses, nuke bomb fixed");
    }
}
