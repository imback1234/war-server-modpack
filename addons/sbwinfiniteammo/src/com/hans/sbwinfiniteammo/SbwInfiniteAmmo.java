package com.hans.sbwinfiniteammo;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SBW Infinite Vehicle Ammo.
 *
 * Add-on for the official Superb Warfare 0.8.9.2 jar. With infinite_vehicle_ammo on, every vehicle behaves as if it
 * carried a creative ammo box: its weapons still empty their magazines and reload on their normal timers, but the
 * reserve never runs out. Handheld guns are not affected. The setting is a server config, so Forge syncs it to every
 * client that joins.
 */
@Mod(SbwInfiniteAmmo.MOD_ID)
public final class SbwInfiniteAmmo {
    public static final String MOD_ID = "sbwinfiniteammo";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.BooleanValue INFINITE_VEHICLE_AMMO;
    private static volatile boolean announced;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        INFINITE_VEHICLE_AMMO = builder
                .comment("Give every Superb Warfare vehicle weapon an infinite ammo reserve.",
                        "Magazines still empty and reload on their normal timers; handheld guns are not affected.")
                .define("infinite_vehicle_ammo", true);
        SPEC = builder.build();
    }

    public SbwInfiniteAmmo() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SPEC);
    }

    /** True once the server config is loaded (or synced to this client) with infinite_vehicle_ammo on. */
    public static boolean enabled() {
        try {
            if (!INFINITE_VEHICLE_AMMO.get()) return false;
        } catch (IllegalStateException notLoadedYet) {
            return false; // a client before it has joined a world: no server config yet
        }
        if (!announced) {
            announced = true;
            LOGGER.info("Infinite vehicle ammo is active: vehicle weapons reload from an endless reserve");
        }
        return true;
    }
}
