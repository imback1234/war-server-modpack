# War Server Vehicle Fix

AshVehicle 4.6 (`ashvehicle-1.20.1-4.6-8.9`) was built against Superb Warfare 0.8.9. Superb Warfare 0.8.9.2 removed
seven members AshVehicle still uses. Java only notices a missing member when the code reaches it, so the game loads and
then crashes:

| Removed in 0.8.9.2 | Used by | What happened |
|---|---|---|
| `VehicleEntity.gearRot(float)` | 26 aircraft models (landing gear) | Game crash when the plane is drawn: Vehicle Assembling Table preview or in the world |
| `MissileProjectile.distracted`, `lostTarget` (public fields) | AGM-114, AGM-158, AIM-9, AIM-54, AIM-120, R-60 | Server crash the moment the missile is created |
| `ProjectileTool.causeCustomExplode(ThrowableItemProjectile, float, float, float)` | CBU-87, GBU-57, toilet bomb | Error when the bomb explodes |
| `ProjectileTool.causeCustomExplode(ThrowableItemProjectile, DamageSource, Entity, float, float)` | the six missiles above | Error when the missile explodes |
| `CustomExplosion.Builder.damageMultiplier(float)` | nuke bomb | Error when it detonates |
| `RenderHelper.isInGui()` | AshVehicle's base vehicle model | Not reached by any current model; restored for completeness |

This mod adds each one back with its 0.8.9 behaviour, written on top of 0.8.9.2's code:

- `gearRot` returns the current landing-gear angle (0.8.9.2 no longer keeps the previous tick's angle to smooth from).
- `distracted` / `lostTarget` are public fields again, and Superb Warfare's own `isDistracted` / `setDistracted` /
  `isLostTarget` / `setLostTarget` read and write them, so both mods share one value as in 0.8.9.
- The `causeCustomExplode` overloads call 0.8.9.2's `causeCustomExplode`. Their extra `damageMultiplier` and
  `Builder.damageMultiplier` only ever added damage against hostile mobs; 0.8.9.2 removed that for every explosion,
  so the value is accepted and not used.
- `isInGui` calls 0.8.9.2's `RenderDistanceHelper.isInGui`.

It also fixes one AshVehicle bug that is not a version mismatch: the nuke bomb calls
`CustomExplosion.Builder.withParticleType(null)`, and Superb Warfare (0.8.9 and 0.8.9.2) throws NullPointerException on
null there, which crashed the server when the bomb detonated. The builder's own default is null ("pick particles from
the radius"), so null is now stored the same way.

Mixin does not allow a mixin class to add public static methods, so the three static functions are added by
`WarVehicleFixMixinPlugin` while the class loads; each one forwards to the method with the same signature in
`LegacyApi`.

Required on the server and on every player's computer. Without it a player's game still crashes on those planes, so
the mod registers a network channel (`warvehiclefix:check`, no packets) and the server sends any player whose game
lacks it back to the menu with a message telling them to download the newest modpack (`ModpackCheck`). Both sides
accept a missing channel during Forge's login handshake, so the player sees that message instead of Forge's
"mismatched mod channel list" error.

Build: put the official `superbwarfare-0.8.9.2-mc1.20.1-65d985a64-all.jar` in `libs/` as `superbwarfare-0.8.9.2.jar`,
then run `gradlew build` (ForgeGradle 6, Forge 1.20.1-47.4.10). The jar is `build/libs/War-Server-Vehicle-Fix-1.1.0.jar`.
