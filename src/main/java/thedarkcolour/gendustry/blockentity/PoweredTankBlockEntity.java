package thedarkcolour.gendustry.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import forestry.core.fluids.ITankManager;
import forestry.core.fluids.TankManager;
import forestry.core.tiles.ILiquidTankTile;
import forestry.core.tiles.TilePowered;

public abstract class PoweredTankBlockEntity extends TilePowered implements ILiquidTankTile {
	protected final TankManager tankManager;

	public PoweredTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int maxTransfer, int capacity) {
		super(type, pos, state, maxTransfer, capacity);

		this.tankManager = new TankManager(this);
	}

	@Override
	public void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		this.tankManager.write(nbt);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.tankManager.read(nbt);
	}

	@Override
	public void writeData(FriendlyByteBuf data) {
		super.writeData(data);
		this.tankManager.writeData(data);
	}

	@Override
	public void readData(FriendlyByteBuf data) {
		super.readData(data);
		this.tankManager.readData(data);
	}

	@Override
	public void writeGuiData(FriendlyByteBuf data) {
		super.writeGuiData(data);
		this.tankManager.writeData(data);
	}

	@Override
	public void readGuiData(FriendlyByteBuf data) {
		super.readGuiData(data);
		this.tankManager.readData(data);
	}

	@Override
	public ITankManager getTankManager() {
		return this.tankManager;
	}
}
