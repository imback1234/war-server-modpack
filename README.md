# War Server Modpack

The modpack for the War Server: a long-running survival war on Minecraft Java Edition 1.20.1 with Forge 47.4.10.
Guns, tanks, aircraft, craftable military armor and realistic terrain.

## Download

There are two versions. Both join the same server, with the same guns, vehicles and armor.

| Version | Who it is for | Download | Memory to give it |
|---|---|---|---|
| **Standard** (recommended) | Everyone whose PC runs it smoothly. Has far view distance, shaders and all the visual mods. | **[War-Server-Modpack.zip](https://github.com/imback1234/war-server-modpack/releases/latest/download/War-Server-Modpack.zip)** | 8 GB (`8192`) |
| **Low-end PC** | Older or weaker PCs that lag on the standard version. If your PC is good, use the standard version. | **[War-Server-Modpack-LowEnd.zip](https://github.com/imback1234/war-server-modpack/releases/latest/download/War-Server-Modpack-LowEnd.zip)** | 4 GB (`4096`) |

The newest version of each is always at these links.

### What the low-end version leaves out

Mods that cost frame rate but do not change the fighting:

- Distant Horizons (far view distance) and Oculus with every shader pack
- ItemPhysic Lite (dropped items lie flat) and its add-on ItemPhysic Guns
- Falling Leaves, Particle Rain, Cave Dust and Make Bubbles Pop (extra particles)
- Sound Physics Remastered, AmbientSounds and Presence Footsteps (extra sound processing)
- Better Animations Collection (smoother animal animations), 3D Skin Layers (3D skin layers) and YDM's Weapon Master
  (hotbar items shown on your character)
- Architectury (only needed by Cave Dust)

It also starts with lighter settings: render distance 8, Fast graphics, no clouds, fewer particles, no entity
shadows, no biome blend, no smooth lighting, no mipmaps and VSync off. Explosions use smaller glow textures and half
the sparks, dust and mist. Particles are drawn out to 75% of the normal distance. Players can change any of it in
Options afterwards.

## Install with CurseForge

1. Open the CurseForge app, click **Minecraft**, then **My Modpacks**, then **Import**.
2. Click **Choose .zip file** and pick the zip you downloaded. Do not unzip it.
3. CurseForge warns that some files are not on CurseForge. Tick the box and click **All Files** (not "CurseForge Files Only").
4. Set the memory: gear icon, **Minecraft**, **Allocated Memory** = `8192` for the standard version or `4096` for the low-end version.
5. Play the new **War Server** (or **War Server (Low-end PC)**) profile, then join the server from **Multiplayer**.

CurseForge downloads almost every mod from its own site. The zips only carry the files listed below.

## Files inside the zips

| File | In | Author | License | Why it is in the zip |
|---|---|---|---|---|
| `sbwinfiniteammo-1.0.0.jar` | Both | Hans | MIT | Add-on made for this server: every Superb Warfare vehicle reloads from an endless reserve. Source in [`addons/sbwinfiniteammo`](addons/sbwinfiniteammo). |
| `ashvehicle-ammo-fix-1.0.0.jar` | Both | Hans, based on [AshVehicle](https://modrinth.com/mod/ashvehicle) | GPL-3.0 | Data fix made for this server: five AshVehicle vehicle files with their shell item IDs corrected for Superb Warfare 0.8.9.2. The JSON files inside the jar are the complete source. |
| `chunkpanel-1.20.1-1.0.0.jar` | Both | Hans | MIT | Made for this server: an operator screen for Chunky pre-generation. |
| `daffas_arsenal-3.7.1.2.jar` | Both | DaffaTheOne | CC BY-NC 4.0 | [Daffa's Arsenal](https://modrinth.com/mod/daffasarsenal), not hosted on CurseForge. |
| `memoryleakfix-forge-1.17+-1.1.5.jar` | Both | FX | LGPL-2.1 | [Memory Leak Fix](https://modrinth.com/mod/memoryleakfix) ([source](https://github.com/fxmorin/memoryLeakFix)); this version is not on CurseForge. |
| `Presence Footsteps [FORGE] 1.0.0.jar` | Standard | 1Influence | MIT | [Presence Footsteps (Forge)](https://modrinth.com/mod/presence-footsteps-forge), not hosted on CurseForge. |
| `rethinking-voxels_r0.1-beta9.zip` | Standard | gri573 | Complementary License 1.5 | [Rethinking Voxels](https://modrinth.com/shader/rethinking-voxels) shader, bundled under section 1.2 (modpacks). Any problems it causes in this modpack are the modpack author's responsibility, not gri573's. |
| `config/entityculling.json` | Both | Hans | MIT | Keeps Superb Warfare and AshVehicle vehicles from being culled. |
| `config/explosionoverhaul/explosionoverhaul-client.toml` | Low-end | Hans | MIT | Lighter explosion effects: smaller glow textures, vanilla particle rendering, half the sparks, dust and mist. |
| `config/particle_core_config.toml` | Low-end | Hans | MIT | Particles are drawn out to 75% of the normal distance. |
| `options.txt` | Both | Hans | MIT | Starting settings. Standard: GUI scale 2. Low-end: GUI scale 2 plus the lighter settings above. Minecraft reads it on the profile's first launch; players can change it afterwards. |

All other mods are installed by CurseForge from their authors' CurseForge pages, under their own licenses.
Superb Warfare is the official release; nothing in this repository changes or redistributes it.

Not affiliated with Mojang or Microsoft.
