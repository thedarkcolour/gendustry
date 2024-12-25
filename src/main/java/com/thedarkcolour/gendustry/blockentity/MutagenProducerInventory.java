package com.thedarkcolour.gendustry.blockentity;

import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;

import forestry.core.fluids.FluidHelper;
import forestry.core.fluids.TankManager;
import forestry.core.inventory.InventoryAdapterTile;

public class MutagenProducerInventory extends InventoryAdapterTile<MutagenProducerBlockEntity> {
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_CAN_INPUT = 1;
	public static final int SLOT_CAN_OUTPUT = 2;

	public MutagenProducerInventory(MutagenProducerBlockEntity tile) {
		super(tile, 3, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_INPUT -> MutagenProducerBlockEntity.isMutagenIngredient(stack);
			case SLOT_CAN_INPUT -> FluidHelper.isFillableEmptyContainer(stack);
			default -> false;
		};
	}

	public void fillContainers(FluidStack fluidStack, TankManager tankManager) {
		if (getItem(SLOT_CAN_INPUT).isEmpty()) {
			return;
		}
		FluidHelper.fillContainers(tankManager, this, SLOT_CAN_INPUT, SLOT_CAN_OUTPUT, fluidStack.getFluid(), true);
	}
}
