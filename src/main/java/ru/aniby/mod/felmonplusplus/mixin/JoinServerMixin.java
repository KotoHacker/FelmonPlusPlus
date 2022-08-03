package ru.aniby.mod.felmonplusplus.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.util.telemetry.TelemetrySender;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.aniby.mod.felmonplusplus.util.PlayerData;
import ru.aniby.mod.felmonplusplus.discord.MainRPC;

@Mixin(ClientPlayNetworkHandler.class)
public class JoinServerMixin {
    @Inject(at = @At("RETURN"), method = "<init>")
    private void onServerJoin(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile, TelemetrySender telemetrySender, CallbackInfo ci) {
        new Thread(() -> {
            while (client == null ||
                    client.world == null ||
                    client.player == null ||
                    client.interactionManager == null ||
                    profile == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
            PlayerData.updateData(client, profile);
            MainRPC.server();
        }, "PlayerDataGetter").start();
    }
}
