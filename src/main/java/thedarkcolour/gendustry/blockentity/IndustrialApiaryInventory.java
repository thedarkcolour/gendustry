package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import forestry.api.apiculture.IBeeHousingInventory;
import forestry.api.apiculture.genetics.BeeLifeStage;
import forestry.api.genetics.capability.IIndividualHandlerItem;
import forestry.core.inventory.InventoryAdapterTile;
import forestry.core.utils.InventoryUtil;

import thedarkcolour.gendustry.api.GendustryTags;

public class IndustrialApiaryInventory extends InventoryAdapterTile<IndustrialApiaryBlockEntity> implements IBeeHousingInventory {
	public static final int QUEEN = 0;
	public static final int DRONE = 1;
	public static final int UPGRADE_SLOT_START = 2;
	public static final int UPGRADE_SLOT_COUNT = 4;
	public static final int OUTPUT_SLOT_START = 6;
	public static final int OUTPUT_SLOT_COUNT = 9;

	public IndustrialApiaryInventory(IndustrialApiaryBlockEntity tile) {
		super(tile, 15, "items");
	}

	@Override
	public boolean canSlotAccept(int slotIndex, ItemStack stack) {
		if (slotIndex == QUEEN) {
			return IIndividualHandlerItem.filter(stack, (i, stage) -> stage == BeeLifeStage.PRINCESS || stage == BeeLifeStage.QUEEN);
		} else if (slotIndex == DRONE) {
			return IIndividualHandlerItem.filter(stack, (i, stage) -> stage == BeeLifeStage.DRONE);
		} else if (UPGRADE_SLOT_START <= slotIndex && slotIndex < UPGRADE_SLOT_START + UPGRADE_SLOT_COUNT) {
			// Only one slot per upgrade type
			for (int i = UPGRADE_SLOT_START; i < UPGRADE_SLOT_START + UPGRADE_SLOT_COUNT; ++i) {
				Item currentItem = getItem(i).getItem();

				if (slotIndex != i) {
					// AIR is empty
					if (stack.is(currentItem)) {
						return false;
					}
				}
			}

			return stack.is(GendustryTags.Items.UPGRADES);
		} else {
			return false;
		}
	}

	@Override
	public boolean canTakeItemThroughFace(int slotIndex, ItemStack stack, Direction side) {
		// Wait till drones are recycled before extracting
		if (this.tile.recycleQueen && IIndividualHandlerItem.filter(stack, (i, stage) -> stage == BeeLifeStage.PRINCESS || stage == BeeLifeStage.DRONE)) {
			return false;
		}
		return OUTPUT_SLOT_START <= slotIndex && slotIndex < OUTPUT_SLOT_COUNT;
	}

	@Override
	public ItemStack getQueen() {
		return getItem(QUEEN);
	}

	@Override
	public ItemStack getDrone() {
		return getItem(DRONE);
	}

	@Override
	public void setQueen(ItemStack itemStack) {
		setItem(QUEEN, itemStack);
	}

	@Override
	public void setDrone(ItemStack itemStack) {
		setItem(DRONE, itemStack);
	}

	@Override
	public boolean addProduct(ItemStack product, boolean all) {
		if (getQueen().isEmpty() && IIndividualHandlerItem.filter(product, (i, stage) -> stage == BeeLifeStage.PRINCESS)) {
			setQueen(product);
			return true;
		}
		return InventoryUtil.tryAddStack(this, product, IndustrialApiaryInventory.OUTPUT_SLOT_START, IndustrialApiaryInventory.OUTPUT_SLOT_COUNT, all, true);
	}
}
