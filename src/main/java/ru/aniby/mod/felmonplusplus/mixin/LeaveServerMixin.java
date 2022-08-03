package ru.aniby.mod.felmonplusplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.aniby.mod.felmonplusplus.util.PlayerData;
import ru.aniby.mod.felmonplusplus.discord.MainRPC;

@Mixin(MinecraftClient.class)
public class LeaveServerMixin {
    @Inject(at = @At("RETURN"), method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V")
    private void disconnect(Screen screen, CallbackInfo info) {
        PlayerData.biome = null;
        PlayerData.world = null;
        PlayerData.gameMode = null;
        MainRPC.menu();
    }
}