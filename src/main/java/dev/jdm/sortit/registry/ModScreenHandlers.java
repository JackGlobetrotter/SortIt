package dev.jdm.sortit.registry;

import dev.jdm.sortit.block.SorterTypes;
import dev.jdm.sortit.screen.SorterScreen;
import dev.jdm.sortit.screen.SorterScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModScreenHandlers {
    public static void registerScreenHandlers() {

        HandledScreens.register(ModScreenHandlerType.COPPER_SORTER,(HandledScreens.Provider<SorterScreenHandler, SorterScreen> ) (desc, inventory, title) -> new SorterScreen(desc, inventory, title, SorterTypes.COPPER));
        HandledScreens.register(ModScreenHandlerType.IRON_SORTER, (HandledScreens.Provider<SorterScreenHandler, SorterScreen> ) (desc, inventory, title) -> new SorterScreen(desc, inventory, title,  SorterTypes.IRON));
        HandledScreens.register(ModScreenHandlerType.GOLD_SORTER, (HandledScreens.Provider<SorterScreenHandler, SorterScreen> ) (desc, inventory, title) -> new SorterScreen(desc, inventory, title,  SorterTypes.GOLD));
        HandledScreens.register(ModScreenHandlerType.EMERALD_SORTER,(HandledScreens.Provider<SorterScreenHandler, SorterScreen> )  (desc, inventory, title) -> new SorterScreen(desc, inventory, title,  SorterTypes.EMERALD));
        HandledScreens.register(ModScreenHandlerType.DIAMOND_SORTER,(HandledScreens.Provider<SorterScreenHandler, SorterScreen> )  (desc, inventory, title) -> new SorterScreen(desc, inventory, title,  SorterTypes.DIAMOND));
    }
}