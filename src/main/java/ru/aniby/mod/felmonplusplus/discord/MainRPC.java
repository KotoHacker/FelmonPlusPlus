package ru.aniby.mod.felmonplusplus.discord;

import net.minecraft.client.resource.language.I18n;
import ru.aniby.mod.felmonplusplus.discord.rpclib.DiscordEventHandlers;
import ru.aniby.mod.felmonplusplus.discord.rpclib.DiscordRPC;
import ru.aniby.mod.felmonplusplus.discord.rpclib.DiscordRichPresence;
import ru.aniby.mod.felmonplusplus.util.FelmonFunctions;
import ru.aniby.mod.felmonplusplus.util.PlayerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static ru.aniby.mod.felmonplusplus.util.PlayerData.biome;
import static ru.aniby.mod.felmonplusplus.util.PlayerData.world;

public class MainRPC {
    static final DiscordRPC instance = DiscordRPC.INSTANCE;
    public static final DiscordRichPresence presence = new DiscordRichPresence();

    public static void init() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        instance.Discord_Initialize("795331486332616754", handlers, true, null);
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        menu();
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                instance.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public static String getState() {
        String start = "rpc.ws.";
        if (PlayerData.biome == null)
            return start + "null";
        switch (world) {
            case "overworld" -> {
                if (biome.contains("swamp"))
                    return start + world + ".swamp";
                else if (biome.contains("beach")) {
                    return FelmonFunctions.getRandomValue(Arrays.asList(
                            start + world + ".beach.a",
                            start + world + ".beach.b"
                    ));
                } else if (biome.contains("river")) {
                    return FelmonFunctions.getRandomValue(Arrays.asList(
                            start + world + ".river.a",
                            start + world + ".river.b"
                    ));
                } else if (biome.contains("ocean")) {
                    return FelmonFunctions.getRandomValue(Arrays.asList(
                            start + world + ".ocean.a",
                            start + world + ".ocean.b"
                    ));
                } else return FelmonFunctions.getRandomValue(Arrays.asList(
                        start + world + ".a",
                        start + world + ".b"
                ));
            }
            case "the_nether" -> {
                if (biome.contains("crimson_forest"))
                    return start + world + ".crimson_forest";
                else if (biome.contains("warped_forest"))
                    return start + world + ".warped_forest";
                else if (biome.contains("basalt_deltas"))
                    return start + world + ".basalt_deltas";
                else if (biome.contains("soul_sand_valley")) {
                    return FelmonFunctions.getRandomValue(Arrays.asList(
                            start + world + ".soul_sand_valley.a",
                            start + world + ".soul_sand_valley.b"
                    ));
                } else return FelmonFunctions.getRandomValue(Arrays.asList(
                        start + world + ".a",
                        start + world + ".b",
                        start + world + ".c"
                ));
            }
            case "the_end" -> {
                return start + world + ".a";
            }
        }
        return start + "null";
    }

    // biome : translates
    private static final HashMap<String, List<String>> map = new HashMap<>();

    private static String getRandomState(String world, String biome_type) {
        String key = world + (biome_type != null ? "." + biome_type : "");
        if (!map.containsKey(key)) {
            List<String> exists = new ArrayList<>();
            String start = "rpc.ws." + key + ".";
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                String translate = start + alphabet;
                if (I18n.hasTranslation(translate))
                    exists.add(I18n.translate(translate));
                else break;
            }
            map.put(key, exists);
        }
        return FelmonFunctions.getRandomValue(map.get(key));
    }

    public static void menu() {
        presence.largeImageKey = "1";
        presence.largeImageText = "felmon.xyz";
        presence.smallImageKey = "iron_golem";
        String sit = I18n.translate("rpc.menu.small_image");
        presence.smallImageText = sit.equals("rpc.menu.small_image") ? "Offline" : sit;
        String details = I18n.translate("rpc.menu.details");
        presence.details = details.equals("rpc.menu.details") ? "In menu" : details;
        String state = I18n.translate("rpc.menu.state");
        presence.state = state.equals("rpc.menu.state") ? "Waiting for a miracle" : state;
//        presence.buttons = DiscordRichPresence.arrayButtons(new String[]{"Играть", "ttps://discord.gg/kmS8r5gefc"});
        instance.Discord_UpdatePresence(presence);
    }

    public static void server() {
        presence.smallImageKey = "http://cravatar.eu/helmavatar/" + PlayerData.nickname + "/512.png";
        presence.smallImageText = PlayerData.nickname;
        presence.details = I18n.translate("rpc.player") + ": " + PlayerData.nickname;
        presence.state = I18n.translate(MainRPC.getState());
//        presence.state = getRandomState()
        presence.largeImageKey = world;
        instance.Discord_UpdatePresence(presence);
    }
}
