package dev.jdm.sortit.registry;

import dev.jdm.sortit.SortIt;
import dev.jdm.sortit.block.SorterTypes;
import dev.jdm.sortit.screen.SorterScreenHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModScreenHandlerType {
        public static ScreenHandlerType<SorterScreenHandler> COPPER_SORTER;
        public static ScreenHandlerType<SorterScreenHandler> IRON_SORTER;
        public static ScreenHandlerType<SorterScreenHandler> GOLD_SORTER;
        public static ScreenHandlerType<SorterScreenHandler> EMERALD_SORTER;
        public static ScreenHandlerType<SorterScreenHandler> DIAMOND_SORTER;

        public static void registerScreenHandlers() {

                COPPER_SORTER = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
                        return new SorterScreenHandler(COPPER_SORTER, SorterTypes.COPPER, syncId, inventory,
                                        new SimpleInventory(5), new SimpleInventory(1),
                                        new ArrayPropertyDelegate(1),
                                        ScreenHandlerContext.EMPTY);
                });
                
                IRON_SORTER = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
                        return new SorterScreenHandler(IRON_SORTER, SorterTypes.IRON, syncId, inventory,
                                        new SimpleInventory(5), new SimpleInventory(2),
                                        new ArrayPropertyDelegate(1),
                                        ScreenHandlerContext.EMPTY);
                });
                
                GOLD_SORTER = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
                        return new SorterScreenHandler(GOLD_SORTER, SorterTypes.GOLD, syncId, inventory,
                                        new SimpleInventory(5), new SimpleInventory(3),
                                        new ArrayPropertyDelegate(1),
                                        ScreenHandlerContext.EMPTY);
                });
                
                
                EMERALD_SORTER = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
                        return new SorterScreenHandler(EMERALD_SORTER, SorterTypes.EMERALD, syncId, inventory,
                                        new SimpleInventory(5), new SimpleInventory(4),
                                        new ArrayPropertyDelegate(1),
                                        ScreenHandlerContext.EMPTY);
                });
                
                
                DIAMOND_SORTER = new ScreenHandlerType<>((int syncId, PlayerInventory inventory) -> {
                        return new SorterScreenHandler(DIAMOND_SORTER, SorterTypes.DIAMOND, syncId, inventory,
                                        new SimpleInventory(5), new SimpleInventory(5),
                                        new ArrayPropertyDelegate(1),
                                        ScreenHandlerContext.EMPTY);
                });               
                
                
                Registry.register(Registries.SCREEN_HANDLER, new Identifier(SortIt.MOD_ID, "copper_sorter"),
                                COPPER_SORTER);
                Registry.register(Registries.SCREEN_HANDLER, new Identifier(SortIt.MOD_ID, "iron_sorter"),
                                IRON_SORTER);
                Registry.register(Registries.SCREEN_HANDLER, new Identifier(SortIt.MOD_ID, "gold_sorter"),
                                GOLD_SORTER);
                Registry.register(Registries.SCREEN_HANDLER, new Identifier(SortIt.MOD_ID, "emerald_sorter"),
                                EMERALD_SORTER);
                Registry.register(Registries.SCREEN_HANDLER, new Identifier(SortIt.MOD_ID, "diamond_sorter"),
                                DIAMOND_SORTER);

           
        }
}
