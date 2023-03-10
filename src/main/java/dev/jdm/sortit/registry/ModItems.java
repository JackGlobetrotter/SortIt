package dev.jdm.sortit.registry;

import dev.jdm.sortit.SortIt;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final BlockItem COPPER_SORTER = new BlockItem(ModBlocks.COPPER_SORTER,
            new Item.Settings());
    public static final BlockItem IRON_SORTER = new BlockItem(ModBlocks.IRON_SORTER,
            new Item.Settings());
    public static final BlockItem GOLD_SORTER = new BlockItem(ModBlocks.GOLD_SORTER,
            new Item.Settings());
    public static final BlockItem EMERALD_SORTER = new BlockItem(ModBlocks.EMERALD_SORTER,
            new Item.Settings());
    public static final BlockItem DIAMOND_SORTER = new BlockItem(ModBlocks.DIAMOND_SORTER,
            new Item.Settings());

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(SortIt.MOD_ID, "copper_sorter"), COPPER_SORTER);
        Registry.register(Registries.ITEM, new Identifier(SortIt.MOD_ID, "iron_sorter"), IRON_SORTER);
        Registry.register(Registries.ITEM, new Identifier(SortIt.MOD_ID, "gold_sorter"), GOLD_SORTER);
        Registry.register(Registries.ITEM, new Identifier(SortIt.MOD_ID, "emerald_sorter"), EMERALD_SORTER);
        Registry.register(Registries.ITEM, new Identifier(SortIt.MOD_ID, "diamond_sorter"), DIAMOND_SORTER);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(COPPER_SORTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(IRON_SORTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(GOLD_SORTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(EMERALD_SORTER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(DIAMOND_SORTER));

    }

    public static void registerItemColorProviders() {

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return MapColor.ORANGE.color;
        }, COPPER_SORTER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return MapColor.IRON_GRAY.color;
        }, IRON_SORTER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return MapColor.GOLD.color;
        }, GOLD_SORTER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return MapColor.EMERALD_GREEN.color;
        }, EMERALD_SORTER);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return MapColor.DIAMOND_BLUE.color;
        }, DIAMOND_SORTER);

    }
}
