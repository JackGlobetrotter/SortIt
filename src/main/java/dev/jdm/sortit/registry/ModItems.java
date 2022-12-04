package dev.jdm.sortit.registry;

import dev.jdm.sortit.SortIt;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    // Chest Items
    public static final BlockItem COPPER_SORTER = new BlockItem(ModBlocks.COPPER_SORTER,
            new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem IRON_SORTER = new BlockItem(ModBlocks.IRON_SORTER,
            new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem GOLD_SORTER = new BlockItem(ModBlocks.GOLD_SORTER,
            new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem EMERALD_SORTER = new BlockItem(ModBlocks.EMERALD_SORTER,
            new Item.Settings().group(ItemGroup.REDSTONE));
    public static final BlockItem DIAMOND_SORTER = new BlockItem(ModBlocks.DIAMOND_SORTER,
            new Item.Settings().group(ItemGroup.REDSTONE));

    public static void registerItems() {

        // Block Items
        Registry.register(Registry.ITEM, new Identifier(SortIt.MOD_ID, "copper_sorter"), COPPER_SORTER);
        Registry.register(Registry.ITEM, new Identifier(SortIt.MOD_ID, "iron_sorter"), IRON_SORTER);
        Registry.register(Registry.ITEM, new Identifier(SortIt.MOD_ID, "gold_sorter"), GOLD_SORTER);
        Registry.register(Registry.ITEM, new Identifier(SortIt.MOD_ID, "emerald_sorter"), EMERALD_SORTER);
        Registry.register(Registry.ITEM, new Identifier(SortIt.MOD_ID, "diamond_sorter"), DIAMOND_SORTER);

        registerColorProviders();
    }

    public static void registerColorProviders() {

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
