package dev.jdm.sortit.block;

import dev.jdm.sortit.SortIt;
import dev.jdm.sortit.block.entity.BaseSorterEntity;
import dev.jdm.sortit.registry.ModBlockEntityType;
import dev.jdm.sortit.registry.ModBlocks;
import dev.jdm.sortit.registry.ModScreenHandlerType;
import dev.jdm.sortit.screen.SorterScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public enum SorterTypes {
    COPPER(1, new Identifier(SortIt.MOD_ID, "model/copper_sorter"), "copper"),
    IRON(2, new Identifier(SortIt.MOD_ID, "model/iron_sorter"), "iron"),
    GOLD(3, new Identifier(SortIt.MOD_ID, "model/gold_sorter"), "gold"),
    EMERALD(4, new Identifier(SortIt.MOD_ID, "model/emerald_sorter"), "emerald"),
    DIAMOND(5, new Identifier(SortIt.MOD_ID, "model/diamond_sorter"), "diamond");

    public final int size;
    public final Identifier texture;
    public final String name; 

    SorterTypes(int size, Identifier texture, String name) {
        this.size = size;
        this.texture = texture;
        this.name = name; 
    }

    public static Block get(SorterTypes type) {
        return switch (type) {
            case COPPER -> ModBlocks.COPPER_SORTER;
            case IRON -> ModBlocks.IRON_SORTER;
            case GOLD -> ModBlocks.GOLD_SORTER;
            case DIAMOND -> ModBlocks.DIAMOND_SORTER;
            case EMERALD -> ModBlocks.EMERALD_SORTER;
            default -> Blocks.HOPPER;
        };
    }

        public BaseSorterEntity makeEntity(BlockPos pos, BlockState state) {
            return switch (this) {
                case COPPER -> ModBlockEntityType.COPPER_SORTER.instantiate(pos, state);
                case IRON -> ModBlockEntityType.IRON_SORTER.instantiate(pos, state);
                case GOLD -> ModBlockEntityType.GOLD_SORTER.instantiate(pos, state);
                case DIAMOND -> ModBlockEntityType.DIAMOND_SORTER.instantiate(pos, state);
                case EMERALD -> ModBlockEntityType.EMERALD_SORTER.instantiate(pos, state);
                default -> new BaseSorterEntity(SorterTypes.COPPER,pos, state);
            };
        }
    
        public ScreenHandlerType<SorterScreenHandler> getScreenHandlerType() {
            return switch (this) {
                case COPPER -> ModScreenHandlerType.COPPER_SORTER;
                case IRON -> ModScreenHandlerType.IRON_SORTER;
                case GOLD -> ModScreenHandlerType.GOLD_SORTER;
                case EMERALD -> ModScreenHandlerType.EMERALD_SORTER;
                case DIAMOND -> ModScreenHandlerType.DIAMOND_SORTER;
                default -> ModScreenHandlerType.COPPER_SORTER;
            };
        }
    
        public BlockEntityType<? extends BaseSorterEntity> getBlockEntityType() {
            return switch (this) {
                case COPPER -> ModBlockEntityType.COPPER_SORTER;
                case IRON -> ModBlockEntityType.IRON_SORTER;
                case GOLD -> ModBlockEntityType.GOLD_SORTER;
                case DIAMOND -> ModBlockEntityType.DIAMOND_SORTER;
                case EMERALD -> ModBlockEntityType.EMERALD_SORTER;
                default -> ModBlockEntityType.COPPER_SORTER;
            };
        }


}
