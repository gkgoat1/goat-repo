package at.gkgo.wurm;

import at.gkgo.canon.block.ModTokenWithEntity;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wurmhole implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("wurmhole");
	public static final ModTokenWithEntity<WurmholeBlock,WurmholeBlockEntity,WurmholeItem> WURMHOLE = ModTokenWithEntity.create(Identifier.of("wurmhole","block"),WurmholeBlockEntity::new,WurmholeBlock::new,WurmholeItem::new);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}