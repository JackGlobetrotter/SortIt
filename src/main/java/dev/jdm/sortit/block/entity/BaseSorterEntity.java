package dev.jdm.sortit.block.entity;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.IntStream;

import dev.jdm.sortit.block.BaseSorterBlock;
import dev.jdm.sortit.block.SorterTypes;
import dev.jdm.sortit.screen.SorterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BaseSorterEntity extends HopperBlockEntity implements Sorter, SidedInventory {

    private static final int[] AVAILABLE_SLOTS = IntStream.range(0, 5).toArray();

    private int inverted;

    private final Inventory filterInventory;

    private final boolean singleOutput;

    private final SorterTypes sorterType;

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return inverted;
        }

        @Override
        public void set(int index, int value) {
            inverted = value;
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public BaseSorterEntity(SorterTypes type, BlockPos pos, BlockState state) {
        super(pos, state);
        this.sorterType = type;
        filterInventory = new SimpleInventory(type.size);
        this.singleOutput = state.get(BaseSorterBlock.FACING) == Direction.DOWN;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        if (this.singleOutput)// single output
            return this.isAcceptedByFilter(stack);
        else
            return true;

    }

    @Override
    public boolean canExtract(int var1, ItemStack var2, Direction var3) {
        return true;
    }

    @Override
    public List<ItemStack> getFilter() {
        DefaultedList<ItemStack> result = DefaultedList.ofSize(sorterType.size);
        for (int i = 0; i < sorterType.size; i++) {
            if (this.filterInventory.getStack(i).getItem() != Items.AIR)
                result.add(this.filterInventory.getStack(i));
        }
        return result;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable(String.format("container.%s_sorter", sorterType.name));
    }

    @Override
    public boolean getInverted() {
        return this.inverted == 1;
    }

    public boolean getSingleOutput() {
        return this.singleOutput;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new SorterScreenHandler(this.sorterType.getScreenHandlerType(), this.sorterType, syncId, playerInventory, this,
                this.filterInventory, this.propertyDelegate,
                ScreenHandlerContext.create(this.world, this.getPos()));
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return AVAILABLE_SLOTS;
    }

    private static IntStream getAvailableSlots(Inventory inventory, Direction side) {
        if (inventory instanceof SidedInventory) {
            return IntStream.of(((SidedInventory) inventory).getAvailableSlots(side));
        }
        return IntStream.range(0, inventory.size());
    }

    public void scatterFilter() {
        ItemScatterer.spawn(this.getWorld(), this.getPos(), this.filterInventory);
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        NbtCompound filter = tag.getCompound("filter");
        for (int i = 0; i < this.filterInventory.size(); i++) {
            this.filterInventory.setStack(i, ItemStack.fromNbt(filter.getCompound(Integer.toString(i))));
        }
        this.inverted = tag.getInt("inverted");
    }

    @Override
    public void writeNbt(NbtCompound tag) { // store level here?

        NbtCompound filter = new NbtCompound();
        for (int i = 0; i < this.filterInventory.size(); i++) {
            filter.put(String.format("%s", i), this.filterInventory.getStack(i).writeNbt(new NbtCompound()));
        }
        tag.put("filter", filter); // MISSING DATA
        tag.putInt("inverted", this.inverted);
        super.writeNbt(tag);
    }

    @Override
    public BlockEntityType<?> getType() {
        return sorterType.getBlockEntityType();
    }

    @Nullable
    private static Inventory getOutputInventory(World world, BlockPos pos, BlockState state, boolean defaultOutput) {
        Direction direction = state.get(BaseSorterBlock.FACING);
        if (direction == Direction.DOWN && defaultOutput)
            return null;
        return BaseSorterEntity.getInventoryAt(world, pos.offset(defaultOutput ? Direction.DOWN : direction));
    }
    private boolean isFull() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxCount())
                continue;
            return false;
        }
        return true;
    }


    public static boolean insert(World world, BlockPos pos, BlockState state, Inventory inventory) {
        Inventory defaultOutputInventory = BaseSorterEntity.getOutputInventory(world, pos, state, false);
        Inventory filteredOutputInventory = BaseSorterEntity.getOutputInventory(world, pos, state, true);
        if (filteredOutputInventory == null && defaultOutputInventory == null) {
            return false;
        }
        //TODO: check for item overflow if hopper/chest beneath is full or block is not a inventory
        Inventory outputInventory;

        Direction filtered_direction = state.get(BaseSorterBlock.FACING).getOpposite();
        Direction default_direction = Direction.UP;

        for (int i = 0; i < inventory.size(); ++i) {
            if (inventory.getStack(i).isEmpty())
                continue;

            ItemStack itemStack = inventory.getStack(i).copy();

            boolean isFilteredItem = ((BaseSorterEntity) inventory).isAcceptedByFilter(itemStack);

            if (filteredOutputInventory == null) {
                if (HopperBlockEntity.isInventoryFull(defaultOutputInventory, default_direction))
                    return false;
                else {
                    outputInventory = defaultOutputInventory; // empty everything if no secondary output, because
                    // filtering should have been done beforehand, TBD !!!!
                }

            } else {
                if (isFilteredItem) {
                    if (HopperBlockEntity.isInventoryFull(filteredOutputInventory, filtered_direction) && defaultOutputInventory != null) {
                        outputInventory = defaultOutputInventory; //Overflow protection!!!
                    } else {
                        outputInventory = filteredOutputInventory;
                    }
                } else {
                    if (defaultOutputInventory == null
                            || HopperBlockEntity.isInventoryFull(defaultOutputInventory, default_direction))
                        continue;
                    else {
                        outputInventory = defaultOutputInventory;
                    }
                }
            }

            ItemStack itemStack2 = HopperBlockEntity.transfer(inventory, outputInventory,
                    inventory.removeStack(i, 1),
                    default_direction);
            if (itemStack2.isEmpty()) {
                outputInventory.markDirty();
                return true;
            }
            inventory.setStack(i, itemStack);
        }
        return false;
    }


}
