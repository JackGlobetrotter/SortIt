package dev.jdm.sortit.block.entity;

import net.minecraft.block.entity.Hopper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.shape.VoxelShape;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Sorter extends Hopper {

    public List<ItemStack> getFilter();

    public boolean getInverted();

    public boolean getSingleOutput();

    public default boolean isAcceptedByFilter(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return true;

        // No filter allows any item
        List<ItemStack> filters = this.getFilter();
        boolean inverted = this.getInverted();

        if (filters.size() == 0)
            return true ^ inverted;

        boolean isAccepted = filters.stream().anyMatch(filter -> {

            if (filter == null || filter.isEmpty()) return false ^ inverted;

            Item filterItem = filter.getItem();
            Item item = stack.getItem();

            if ((filterItem == item)) {
                // Potions must match
                if (filterItem instanceof PotionItem && item instanceof PotionItem) {
                    return (PotionUtil.getPotion(filter) == PotionUtil.getPotion(stack)) ^ inverted;
                }

                return true ^ inverted;
            }
            return false ^ inverted;
        });

        return isAccepted;
    }

    default public VoxelShape getInputAreaShape() {
        return Hopper.INPUT_AREA_SHAPE;
    }

}
