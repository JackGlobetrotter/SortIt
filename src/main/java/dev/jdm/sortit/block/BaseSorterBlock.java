package dev.jdm.sortit.block;

import dev.jdm.sortit.block.entity.Sorter;
import net.minecraft.block.entity.*;
import org.jetbrains.annotations.Nullable;

import dev.jdm.sortit.block.entity.BaseSorterEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BaseSorterBlock extends HopperBlock {

	private final SorterTypes type;

	public static final DirectionProperty FACING = Properties.HOPPER_FACING;

	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape MIDDLE_SHAPE = Block.createCuboidShape(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
	private static final VoxelShape OUTSIDE_SHAPE = VoxelShapes.union(MIDDLE_SHAPE, TOP_SHAPE);
	private static final VoxelShape DEFAULT_SHAPE = VoxelShapes.combineAndSimplify(OUTSIDE_SHAPE, Hopper.INSIDE_SHAPE,
			BooleanBiFunction.ONLY_FIRST);
	private static final VoxelShape DOWN_SHAPE = VoxelShapes.union(DEFAULT_SHAPE,
			Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
	private static final VoxelShape EAST_SHAPE = VoxelShapes.union(DEFAULT_SHAPE,
			Block.createCuboidShape(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
	private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(DEFAULT_SHAPE,
			Block.createCuboidShape(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
	private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(DEFAULT_SHAPE,
			Block.createCuboidShape(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
	private static final VoxelShape WEST_SHAPE = VoxelShapes.union(DEFAULT_SHAPE,
			Block.createCuboidShape(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
	private static final VoxelShape DOWN_RAYCAST_SHAPE = Hopper.INSIDE_SHAPE;
	private static final VoxelShape EAST_RAYCAST_SHAPE = VoxelShapes.union(Hopper.INSIDE_SHAPE,
			Block.createCuboidShape(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
	private static final VoxelShape NORTH_RAYCAST_SHAPE = VoxelShapes.union(Hopper.INSIDE_SHAPE,
			Block.createCuboidShape(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
	private static final VoxelShape SOUTH_RAYCAST_SHAPE = VoxelShapes.union(Hopper.INSIDE_SHAPE,
			Block.createCuboidShape(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
	private static final VoxelShape WEST_RAYCAST_SHAPE = VoxelShapes.union(Hopper.INSIDE_SHAPE,
			Block.createCuboidShape(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

	public BaseSorterBlock(Block.Settings settings, SorterTypes type) {
		super(settings);
		this.type = type;
		this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(FACING,
				Direction.DOWN)).with(ENABLED, true));
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BaseSorterEntity) {
				((BaseSorterEntity) blockEntity).scatterFilter();
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return this.type.makeEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(FACING)) {
			case SOUTH:
				return VoxelShapes.union(DOWN_SHAPE, SOUTH_SHAPE);
			case NORTH:
				return VoxelShapes.union(DOWN_SHAPE, NORTH_SHAPE);
			case EAST:
				return VoxelShapes.union(DOWN_SHAPE, EAST_SHAPE);
			case WEST:
				return VoxelShapes.union(DOWN_SHAPE, WEST_SHAPE); // DEFAULT_SHAPE;
			case DOWN:
				return DOWN_SHAPE; // DEFAULT_SHAPE;
		}
		return DEFAULT_SHAPE;
	}

	@Override
	public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		switch (state.get(FACING)) {
			case DOWN: {
				return DOWN_RAYCAST_SHAPE;
			}
			case NORTH: {
				return VoxelShapes.union(NORTH_RAYCAST_SHAPE, DOWN_RAYCAST_SHAPE);
			}
			case SOUTH: {
				return VoxelShapes.union(SOUTH_RAYCAST_SHAPE, DOWN_RAYCAST_SHAPE);
			}
			case WEST: {
				return VoxelShapes.union(WEST_RAYCAST_SHAPE, DOWN_RAYCAST_SHAPE);
			}
			case EAST: {
				return VoxelShapes.union(EAST_RAYCAST_SHAPE, DOWN_RAYCAST_SHAPE);
			}
		}
		return Hopper.INSIDE_SHAPE;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {

		Direction direction = ctx.getSide().getOpposite();
		if (direction == Direction.DOWN)
			return (BlockState) ((BlockState) this.getDefaultState().with(FACING,
					direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction))
					.with(ENABLED, true);
		else
			return (BlockState) ((BlockState) this.getDefaultState().with(FACING,
					direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction))
					.with(ENABLED, true);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, ENABLED);
	}

	/*@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
																  BlockEntityType<T> type) {
		return world.isClient() ? null
				: checkType(type, this.type.getBlockEntityType(),
				BaseSorterEntity::serverTick);
	}*/


	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient() ? null : checkType(type,this.type.getBlockEntityType() , HopperBlockEntity::serverTick);
	}
}
