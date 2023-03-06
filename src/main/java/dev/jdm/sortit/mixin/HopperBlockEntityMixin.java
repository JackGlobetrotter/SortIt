package dev.jdm.sortit.mixin;

import dev.jdm.sortit.block.BaseSorterBlock;
import dev.jdm.sortit.block.entity.BaseSorterEntity;
import dev.jdm.sortit.item.ItemSorterItem;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.jdm.sortit.block.entity.Sorter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @Redirect(method = "insert", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0))
    private static boolean filterOutput(ItemStack stack, World world, BlockPos pos, BlockState state, Inventory inventory) {
        if (stack.isEmpty()) return true;
        if ((Object) inventory instanceof Sorter) {
            Sorter itemSorter = (Sorter) (Object) inventory;
            System.out.println("insert 1");
            if (!itemSorter.isAcceptedByFilter(stack)) return true;
        }
        return false;
    }

    @Inject(method = "extract(Lnet/minecraft/block/entity/Hopper;Lnet/minecraft/inventory/Inventory;ILnet/minecraft/util/math/Direction;)Z", at = @At("HEAD"), cancellable = true)
    private static void filterInventoryInput(Hopper hopper, Inventory inventory, int slot, Direction side, CallbackInfoReturnable<Boolean> ci) {
        if (hopper instanceof Sorter) {

            Sorter itemSorter = (Sorter) hopper;

            System.out.println("Extract 1");
            if (itemSorter.getSingleOutput() && !itemSorter.isAcceptedByFilter(inventory.getStack(slot))) {
                ci.setReturnValue(false);
            }
        }
    }

    @Inject(method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z", at = @At("HEAD"), cancellable = true)
    private static void filterItemEntityInput(Inventory inventory, ItemEntity itemEntity, CallbackInfoReturnable<Boolean> ci) {
        if (inventory instanceof Sorter) {
            System.out.println("Extract 2");
            Sorter itemSorter = (Sorter) inventory;
            if (!itemSorter.isAcceptedByFilter(itemEntity.getStack())) {
                ci.setReturnValue(false);
            }
        }
    }
    @Inject(method = "insert(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/inventory/Inventory;)Z", at = @At("HEAD"), cancellable = true)
    private static void filterInput(World world, BlockPos pos, BlockState state, Inventory inventory, CallbackInfoReturnable<Boolean> ci) {
        if (inventory instanceof Sorter) {
            BaseSorterEntity.insert(world, pos, state, inventory);
        }
        ci.setReturnValue(false);
    }

}