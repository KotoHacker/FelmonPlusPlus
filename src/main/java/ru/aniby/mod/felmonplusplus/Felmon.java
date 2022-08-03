package ru.aniby.mod.felmonplusplus;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aniby.mod.felmonplusplus.discord.MainRPC;

public class Felmon implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("felmonplusplus");

	@Override
	public void onInitialize() {
		MainRPC.init();
	}
}
