package dev.jdm.sortit.registry;

import dev.jdm.sortit.SortIt;
import dev.jdm.sortit.block.BaseSorterBlock;
import dev.jdm.sortit.block.SorterTypes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    private static final FabricBlockSettings settings = FabricBlockSettings.copyOf(Blocks.HOPPER);

    public static final Block COPPER_SORTER = new BaseSorterBlock(settings.mapColor(MapColor.ORANGE), SorterTypes.COPPER);
    public static final Block IRON_SORTER = new BaseSorterBlock(settings, SorterTypes.IRON);
    public static final Block GOLD_SORTER = new BaseSorterBlock(settings.mapColor(MapColor.GOLD), SorterTypes.GOLD);
    public static final Block EMERALD_SORTER = new BaseSorterBlock(settings.mapColor(MapColor.EMERALD_GREEN), SorterTypes.EMERALD);
    public static final Block DIAMOND_SORTER = new BaseSorterBlock(settings.mapColor(MapColor.DIAMOND_BLUE), SorterTypes.DIAMOND);

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(SortIt.MOD_ID, "copper_sorter"), COPPER_SORTER);
        Registry.register(Registry.BLOCK, new Identifier(SortIt.MOD_ID, "iron_sorter"), IRON_SORTER);
        Registry.register(Registry.BLOCK, new Identifier(SortIt.MOD_ID, "gold_sorter"), GOLD_SORTER);
        Registry.register(Registry.BLOCK, new Identifier(SortIt.MOD_ID, "emerald_sorter"), EMERALD_SORTER);
        Registry.register(Registry.BLOCK, new Identifier(SortIt.MOD_ID, "diamond_sorter"), DIAMOND_SORTER);
    }
}
