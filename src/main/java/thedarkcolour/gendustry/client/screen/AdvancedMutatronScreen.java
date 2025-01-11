package thedarkcolour.gendustry.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import forestry.core.config.Constants;
import forestry.core.gui.buttons.GuiBetterButton;
import forestry.core.gui.buttons.StandardButtonTextureSets;

import org.jetbrains.annotations.Nullable;
import thedarkcolour.gendustry.Gendustry;
import thedarkcolour.gendustry.menu.AdvancedMutatronMenu;

public class AdvancedMutatronScreen extends AbstractMutatronScreen<AdvancedMutatronMenu> {
	@Nullable
	private GuiBetterButton leftButton;
	@Nullable
	private GuiBetterButton rightButton;

	public AdvancedMutatronScreen(AdvancedMutatronMenu menu, Inventory playerInv, Component title) {
		super(Gendustry.loc(Constants.TEXTURE_PATH_GUI + "/advanced_mutatron.png"), menu, playerInv, title);

		menu.setDataListener(this::updateButtonVisibility);
	}

	// React to changes in menu data
	private void updateButtonVisibility() {
		if (this.leftButton != null && this.rightButton != null) {
			this.leftButton.visible = this.rightButton.visible = this.menu.getPossibilityCount() > 4;
		}
	}

	@Override
	public void init() {
		super.init();

		Minecraft mc = Minecraft.getInstance();
		int containerId = this.menu.containerId;

		this.leftButton = new GuiBetterButton(this.leftPos + 130, this.topPos + 73, StandardButtonTextureSets.LEFT_BUTTON_SMALL, button -> {
			mc.gameMode.handleInventoryButtonClick(containerId, AdvancedMutatronMenu.BUTTON_CYCLE_LEFT);
		});
		this.rightButton = new GuiBetterButton(this.leftPos + 141, this.topPos + 73, StandardButtonTextureSets.RIGHT_BUTTON_SMALL, button -> {
			mc.gameMode.handleInventoryButtonClick(containerId, AdvancedMutatronMenu.BUTTON_CYCLE_RIGHT);
		});
		addRenderableWidget(this.leftButton);
		addRenderableWidget(this.rightButton);

		updateButtonVisibility();
	}

	@Override
	protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
		super.renderBg(graphics, partialTicks, mouseX, mouseY);

		int slots = Math.min(4, this.menu.getPossibilityCount());
		for (int i = 0; i < slots; ++i) {
			int x = this.leftPos + 63 + i * 16;
			int y = this.topPos + 70;
			int v = 176;

			if (this.menu.getSelected() == i + this.menu.getOffset()) {
				v += 18;
			} else if (mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 18) {
				v += 36;
			}

			graphics.blit(this.textureFile, x, y, 0, v, 16, 18);
		}
	}

	@Override
	protected void slotClicked(Slot slot, int slotId, int button, ClickType type) {
		super.slotClicked(slot, slotId, button, type);

		if (slot instanceof AdvancedMutatronMenu.ChoiceSlot choiceSlot) {
			Minecraft.getInstance().gameMode.handleInventoryButtonClick(this.menu.containerId, AdvancedMutatronMenu.CHOICE_CLICKED + choiceSlot.choiceIndex);
		}
	}
}
