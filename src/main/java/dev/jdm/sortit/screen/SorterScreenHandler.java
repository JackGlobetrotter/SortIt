package dev.jdm.sortit.screen;

import dev.jdm.sortit.block.SorterTypes;
import dev.jdm.sortit.registry.ModScreenHandlerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class SorterScreenHandler extends ScreenHandler {
	private final Inventory inventory;
	private final Inventory filterInventory;
	public final DefaultedList<Slot> filterSlots = DefaultedList.of();
	private final ScreenHandlerContext context;

	PropertyDelegate propertyDelegate;

	public SorterScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(ModScreenHandlerType.COPPER_SORTER, SorterTypes.COPPER, syncId, playerInventory, new SimpleInventory(5), new SimpleInventory(1),
				new ArrayPropertyDelegate(1),
				ScreenHandlerContext.EMPTY);
	}

	public SorterScreenHandler(ScreenHandlerType<?> handlerType, SorterTypes sorterType, int syncId, PlayerInventory playerInventory, Inventory inventory,
			Inventory filterInventory, PropertyDelegate delegate, ScreenHandlerContext context) {
		super(handlerType, syncId);

		// Filter
		this.filterInventory = filterInventory;
		this.inventory = inventory;
		this.context = context;
		this.propertyDelegate = delegate;

		// FilterSlot
		ScreenHandler.checkSize(this.filterInventory, sorterType.size);
		for (int slot = 0; slot < this.filterInventory.size(); slot++) {
			filterSlots.add(this.addSlot(new Slot(this.filterInventory, /*100+*/slot, 44+(18*slot), 18) {
				@Override
				public int getMaxItemCount() {
					return 1;
				}
			}));
		}

		// Sorter Inventory
		ScreenHandler.checkSize(this.inventory, 5);
		for (int slot = 0; slot < 5; slot++) {
			this.addSlot(new Slot(this.inventory, slot, slot * 18 + 44, 37));
		}

		// Player inventory
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				this.addSlot(new Slot(playerInventory, column + row * 9 + 9, column * 18 + 8, row * 18 + 69));
			}
		}

		// Hotbar
		for (int column = 0; column < 9; column++) {
			this.addSlot(new Slot(playerInventory, column, column * 18 + 8, 127));
		}

		this.inventory.onOpen(playerInventory.player);
		this.addProperties(delegate);
		this.filterInventory.onOpen(playerInventory.player);
	}

	public DefaultedList<Slot> getFilteSlots(){
		return this.filterSlots;
	}

	public int getSyncedInverted() {
		return this.propertyDelegate.get(0);
	}

	@Override
	public boolean onButtonClick(PlayerEntity i, int u) {
		this.propertyDelegate.set(0, u);
		this.context.run(World::markDirty);
		return true;
	}

	public void set(int u) {
		this.propertyDelegate.set(0, u);
		this.context.run(World::markDirty);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		Slot slot = this.slots.get(index);
		if (slot == null || !slot.hasStack())
			return ItemStack.EMPTY;

		ItemStack slotStack = slot.getStack();
		ItemStack stack = slotStack.copy();

		if (index < this.inventory.size()) {
			if (!this.insertItem(slotStack, this.inventory.size() + 1, this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
		} else if (!this.insertItem(slotStack, 0, this.inventory.size(), false)) {
			return ItemStack.EMPTY;
		}

		if (slotStack.isEmpty()) {
			slot.setStack(ItemStack.EMPTY);
		} else {
			slot.markDirty();
		}

		return stack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);

		this.inventory.onClose(player);
		this.filterInventory.onClose(player);
	}
}
