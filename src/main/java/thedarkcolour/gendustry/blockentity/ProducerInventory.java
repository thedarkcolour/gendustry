package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import forestry.core.fluids.FluidHelper;
import forestry.core.inventory.InventoryAdapterTile;

import thedarkcolour.gendustry.item.GendustryResourceType;
import thedarkcolour.gendustry.registry.GItems;

public class ProducerInventory<T extends ProducerBlockEntity<T, ?>> extends InventoryAdapterTile<T> {
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_CAN_INPUT = 1;
	public static final int SLOT_CAN_OUTPUT = 2;
	public static final int SLOT_LABWARE = 3;

	private final boolean usesLabware;

	public ProducerInventory(T tile, boolean usesLabware) {
		super(tile, usesLabware ? 4 : 3, "items");

		this.usesLabware = usesLabware;
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		return switch (slotIndex) {
			case SLOT_INPUT -> tile.isValidInput(stack);
			case SLOT_LABWARE -> this.usesLabware && stack.is(GItems.RESOURCE.item(GendustryResourceType.LABWARE));
			case SLOT_CAN_INPUT -> FluidHelper.isFillableEmptyContainer(stack);
			default -> false;
		};
	}

	@Override
	public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction side) {
		return slotIndex == SLOT_CAN_OUTPUT;
	}
}
