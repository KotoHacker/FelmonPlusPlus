package ru.aniby.mod.felmonplusplus.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.GameMode;
import org.jetbrains.annotations.NotNull;

public class PlayerData {
    public static String nickname = null;
    public static String biome = null;
    public static String world = null;
    public static GameMode gameMode = null;

    public static void updateData(@NotNull MinecraftClient client, @NotNull GameProfile profile) {
        assert client.player != null;
        assert client.interactionManager != null;
        nickname = profile.getName();
        world = client.player.world.getRegistryKey().getValue().getPath();
        biome = client.player.world.getBiome(client.player.getBlockPos()).getKey().get().getValue().getPath();
        gameMode = client.interactionManager.getCurrentGameMode();
//        Felmon.LOGGER.info(world + " - " + biome + " - " + gameMode.getName());
    }

    public static void updateData(String world, String biome, GameMode gameMode) {
        PlayerData.world = world;
        PlayerData.biome = biome;
        PlayerData.gameMode = gameMode;
//        Felmon.LOGGER.info(world + " - " + biome + " - " + gameMode.getName());
    }
}
