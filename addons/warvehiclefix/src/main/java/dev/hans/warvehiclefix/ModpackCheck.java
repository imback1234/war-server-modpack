package dev.hans.warvehiclefix;

import com.mojang.logging.LogUtils;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Makes the fix required on players' computers. Without it a player's game still crashes when an AshVehicle plane is
 * drawn, so the server sends players who do not have it away with a message that says how to update.
 *
 * <p>The channel carries no packets; Forge exchanges channel names at login, so the server can tell whether the player
 * has this mod. Both sides accept a missing channel during that exchange, so the player reaches the world and gets
 * this readable message instead of Forge's "mismatched mod channel list" error.
 */
final class ModpackCheck {
    private static final String PROTOCOL = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(WarVehicleFix.MOD_ID, "check"))
            .networkProtocolVersion(() -> PROTOCOL)
            .clientAcceptedVersions(version -> true)
            .serverAcceptedVersions(version -> true)
            .simpleChannel();

    private static final Component OUTDATED = Component.literal(
            "Your War Server modpack is out of date.\n\n"
            + "The server now needs the War Server Vehicle Fix, which stops AshVehicle planes and missiles from "
            + "crashing your game and the server.\n\n"
            + "Download modpack 1.1.3 or newer (standard or low-end) from the War Server website or the Discord "
            + "(discord.gg/2478TwB8eh), import it in CurseForge, and join again.");

    private ModpackCheck() {
    }

    static void register() {
        MinecraftForge.EVENT_BUS.addListener(ModpackCheck::onLogin);
    }

    private static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && !CHANNEL.isRemotePresent(player.connection.connection)) {
            LogUtils.getLogger().info("{} joined without War Server Vehicle Fix; told to update the modpack", player.getGameProfile().getName());
            player.connection.disconnect(OUTDATED);
        }
    }
}
