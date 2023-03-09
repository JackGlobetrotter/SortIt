package dev.jdm.sortit;

import dev.jdm.sortit.registry.ModBlockEntityType;
import dev.jdm.sortit.registry.ModBlocks;
import dev.jdm.sortit.registry.ModItems;
import dev.jdm.sortit.registry.ModScreenHandlerType;
import dev.jdm.sortit.screen.SorterScreenHandler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortIt implements ModInitializer {

	public static final int LEVELS = 5;
	public static final String MOD_ID = new String("sortit");
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Identifier INVERT_SORT_PACKET_ID = new Identifier(MOD_ID, "sorter_invert_update");

	public void onInitialize() {
		
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModBlockEntityType.registerBlockEntities();
        ModScreenHandlerType.registerScreenHandlers();

		ServerPlayNetworking.registerGlobalReceiver(INVERT_SORT_PACKET_ID, (server, player, handler, buf, sender) -> {
			SorterScreenHandler sorterScreenHandler = (SorterScreenHandler) player.currentScreenHandler;
			sorterScreenHandler.set(buf.readByte());
		});

	}
}
