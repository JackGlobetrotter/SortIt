package dev.jdm.sortit.registry;

import dev.jdm.sortit.SortIt;
import dev.jdm.sortit.block.SorterTypes;
import dev.jdm.sortit.block.entity.BaseSorterEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class ModBlockEntityType {
    public static final BlockEntityType<BaseSorterEntity> COPPER_SORTER = FabricBlockEntityTypeBuilder
            .create((BlockPos pos, BlockState state) -> new BaseSorterEntity(SorterTypes.COPPER, pos, state),
                    ModBlocks.COPPER_SORTER)
            .build(null);
    public static final BlockEntityType<BaseSorterEntity> IRON_SORTER = FabricBlockEntityTypeBuilder
    .create((BlockPos pos, BlockState state) -> new BaseSorterEntity(SorterTypes.IRON, pos, state),
            ModBlocks.IRON_SORTER)
    .build(null);
    public static final BlockEntityType<BaseSorterEntity> GOLD_SORTER = FabricBlockEntityTypeBuilder
    .create((BlockPos pos, BlockState state) -> new BaseSorterEntity(SorterTypes.GOLD, pos, state),
            ModBlocks.GOLD_SORTER)
    .build(null);
    public static final BlockEntityType<BaseSorterEntity> EMERALD_SORTER = FabricBlockEntityTypeBuilder
    .create((BlockPos pos, BlockState state) -> new BaseSorterEntity(SorterTypes.EMERALD, pos, state),
            ModBlocks.EMERALD_SORTER)
    .build(null);
    public static final BlockEntityType<BaseSorterEntity> DIAMOND_SORTER = FabricBlockEntityTypeBuilder
    .create((BlockPos pos, BlockState state) -> new BaseSorterEntity(SorterTypes.DIAMOND, pos, state),
            ModBlocks.DIAMOND_SORTER)
    .build(null);

    public static void registerBlockEntities() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SortIt.MOD_ID, "copper_sorter"), COPPER_SORTER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SortIt.MOD_ID, "iron_sorter"), IRON_SORTER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SortIt.MOD_ID, "gold_sorter"), GOLD_SORTER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SortIt.MOD_ID, "emerald_sorter"), EMERALD_SORTER);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(SortIt.MOD_ID, "diamond_sorter"), DIAMOND_SORTER);
    }
}
