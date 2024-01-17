package at.gkgo.thing;

import at.gkgo.canon.block.ModTokenWithEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Thingamajiggy implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("thingamajiggy");
	public static ModTokenWithEntity<ThingamajiggyBlock,ThingamajiggyBlockEntity,ThingamajiggyItem> TOKEN = ModTokenWithEntity.create(Identifier.of("thingamajiggy","block"),ThingamajiggyBlockEntity::new,ThingamajiggyBlock::new,ThingamajiggyItem::new);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		ItemStorage.SIDED.registerFallback((w,p,s,e,c) -> {
			var p2 = p.offset(c.getOpposite());
			var s2 = w.getBlockState(p2);
			if(s2.getBlock() == TOKEN.getBlock()){
				return ((ThingamajiggyBlockEntity) Objects.requireNonNull(w.getBlockEntity(p2))).io.get(c);
			}
			return null;
		});
	}
}