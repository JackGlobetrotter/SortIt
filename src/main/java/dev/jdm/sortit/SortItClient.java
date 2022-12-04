package dev.jdm.sortit;

import dev.jdm.sortit.registry.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;

public class SortItClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModScreenHandlers.registerScreenHandlers();
       // ModTextures.registerTextures();

    }
}