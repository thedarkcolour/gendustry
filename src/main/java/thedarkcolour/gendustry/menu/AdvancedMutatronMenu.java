package thedarkcolour.gendustry.menu;

import java.util.List;
import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ILifeStage;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpecies;
import forestry.core.tiles.TileUtil;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.blockentity.AbstractMutatronBlockEntity;
import thedarkcolour.gendustry.blockentity.AdvancedMutatronBlockEntity;
import thedarkcolour.gendustry.blockentity.MutatronInventory;
import thedarkcolour.gendustry.registry.GMenus;

public class AdvancedMutatronMenu extends AbstractMutatronMenu<AdvancedMutatronBlockEntity> {
	// Used in Menu.clickMenuButton and MultiPlayerGameMode.handleInventoryButtonClick
	public static final int BUTTON_CYCLE_LEFT = 0;
	public static final int BUTTON_CYCLE_RIGHT = 1;
	public static final int CHOICE_CLICKED = 2;

	// Currently displayed options
	public final Slot[] choices;
	// Synced: the total number of possibilities, the offset in the list display, and the currently selected mutation
	private final SimpleContainerData data;

	// Tracks the list stored in the block entity (server only)
	private List<IMutation<ISpecies<?>>> possibilities;
	// List of icons kept on the server to rotate into the display slots
	private List<ItemStack> icons;
	// Nonnull on client for the GUI to react to data changes
	@Nullable
	private Runnable dataListener;

	public AdvancedMutatronMenu(int windowId, Inventory playerInventory, AdvancedMutatronBlockEntity tile) {
		super(windowId, GMenus.ADVANCED_MUTATRON.menuType(), playerInventory, tile);

		this.choices = new ChoiceSlot[4];
		this.possibilities = List.of();
		this.icons = List.of();
		this.data = new SimpleContainerData(3);

		for (int i = 0; i < 4; i++) {
			ChoiceSlot choice = new ChoiceSlot(i, 63 + i * 16, 71);
			addSlot(choice);
			this.choices[i] = choice;
		}

		addDataSlots(this.data);
	}

	public static AdvancedMutatronMenu fromNetwork(int windowId, Inventory playerInv, FriendlyByteBuf extraData) {
		AdvancedMutatronBlockEntity tile = TileUtil.getTile(playerInv.player.level(), extraData.readBlockPos(), AdvancedMutatronBlockEntity.class);
		return new AdvancedMutatronMenu(windowId, playerInv, Objects.requireNonNull(tile));
	}

	// Functions as a server tick method for the menu
	@Override
	public void broadcastChanges() {
		// Track possibilities from tile, updating if necessary
		List<IMutation<ISpecies<?>>> newPossibilities = this.tile.getPossibilities();
		if (this.possibilities != newPossibilities) {
			this.possibilities = newPossibilities;

			// -1 is unselected (ImmutableList doesn't like null values)
			IMutation<?> current = this.tile.getCurrentMutation();
			setSelected(current == null ? -1 : newPossibilities.indexOf(current));

			// Reset slot display
			setOffset(0);
			this.icons = newPossibilities.stream().map(mutation -> {
				ILifeStage stage = mutation.getType().getTypeForMutation(2);
				IIndividual individual = mutation.getResult().createIndividual(AbstractMutatronBlockEntity.createMutatedGenome(mutation));
				return individual.createStack(stage);
			}).toList();
			for (int i = 0; i < this.icons.size(); ++i) {
				this.choices[i].set(this.icons.get(i));
			}
			for (int i = this.icons.size(); i < 4; ++i) {
				this.choices[i].set(ItemStack.EMPTY);
			}

			setPossibilityCount(this.possibilities.size());
		}

		super.broadcastChanges();
	}

	// Called on the client by the server
	@Override
	public boolean clickMenuButton(Player player, int id) {
		int offset = getOffset();

		if (getPossibilityCount() > 4) {
			// Left = backward, Right = forward
			if (id == BUTTON_CYCLE_LEFT) {
				if (offset > 0) {
					setOffset(offset - 1);
				}
			} else if (id == BUTTON_CYCLE_RIGHT) {
				if (offset + 4 < getPossibilityCount()) {
					setOffset(offset + 1);
				}
			}
		}

		if (id >= CHOICE_CLICKED && id < CHOICE_CLICKED + 4) {
			setSelected(offset + id - 2);
		}

		return super.clickMenuButton(player, id);
	}

	public int getPossibilityCount() {
		return this.data.get(0);
	}

	public void setPossibilityCount(int possibilityCount) {
		this.data.set(0, possibilityCount);
	}

	public int getOffset() {
		return this.data.get(1);
	}

	public void setOffset(int offset) {
		this.data.set(1, offset);
	}

	public int getSelected() {
		return this.data.get(2);
	}

	public void setSelected(int selected) {
		this.data.set(2, selected);

		if (selected > 0 && !this.tile.getLevel().isClientSide) {
			List<IMutation<ISpecies<?>>> possibilities = this.tile.getPossibilities();
			int choice = getSelected() + getOffset();

			if (choice < possibilities.size()) {
				this.tile.setCurrentMutation(possibilities.get(choice), this.tile.getItem(MutatronInventory.SLOT_PRIMARY), this.tile.getItem(MutatronInventory.SLOT_SECONDARY));
			}
		}
	}

	public void setDataListener(Runnable listener) {
		this.dataListener = listener;
	}

	@Override
	public void setData(int pId, int pData) {
		super.setData(pId, pData);

		if (this.dataListener != null) {
			this.dataListener.run();
		}
	}

	public static class ChoiceSlot extends Slot {
		// Index between 0-3
		public final int choiceIndex;

		public ChoiceSlot(int choiceIndex, int x, int y) {
			super(new SimpleContainer(1), 0, x, y);
			this.choiceIndex = choiceIndex;
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		@Override
		public boolean mayPickup(Player pPlayer) {
			return false;
		}

		@Override
		public boolean isHighlightable() {
			return false;
		}
	}
}
