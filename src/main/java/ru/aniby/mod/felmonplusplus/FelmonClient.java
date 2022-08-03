package ru.aniby.mod.felmonplusplus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ru.aniby.mod.felmonplusplus.discord.MainRPC;
import ru.aniby.mod.felmonplusplus.util.PlayerData;

import java.util.Objects;

public class FelmonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.interactionManager != null && client.player.getGameProfile() != null) {
                String world_name = client.player.world.getRegistryKey().getValue().getPath();
                String biome_name = client.player.world.getBiome(client.player.getBlockPos()).getKey().get().getValue().getPath();
                if (!Objects.equals(PlayerData.world, world_name) || !Objects.equals(PlayerData.biome, biome_name)) {
                    PlayerData.updateData(world_name, biome_name, client.interactionManager.getCurrentGameMode());
                    MainRPC.server();
                }
            }
        });
    }
}
