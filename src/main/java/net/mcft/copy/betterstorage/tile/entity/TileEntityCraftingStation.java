package net.mcft.copy.betterstorage.tile.entity;

import net.mcft.copy.betterstorage.config.GlobalConfig;
import net.mcft.copy.betterstorage.container.ContainerBetterStorage;
import net.mcft.copy.betterstorage.container.ContainerCraftingStation;
import net.mcft.copy.betterstorage.inventory.InventoryCraftingStation;
import net.mcft.copy.betterstorage.inventory.InventoryTileEntity;
import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.utils.NbtUtils;
import net.mcft.copy.betterstorage.utils.WorldUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants.NBT;
import scala.actors.threadpool.Arrays;

public class TileEntityCraftingStation extends TileEntityContainer
                                       implements IInventory, ISidedInventory {
	
	public ItemStack[] crafting;
	public ItemStack[] output;
	
	private InventoryCraftingStation stationInventory;
	
	@Override
	protected int getSizeContents() { return 18; }
	
	@Override
	public String getName() { return Constants.containerCraftingStation; }
	
	@Override
	public InventoryTileEntity makePlayerInventory() {
		// Workaround because instance variables get initialized AFTER the
		// parent constructor. This gets called IN the parent constructor.
		crafting = new ItemStack[9];
		output = new ItemStack[9];
		stationInventory = new InventoryCraftingStation(this);
		return new InventoryTileEntity(this, stationInventory);
	}
	
	@Override
	public ContainerBetterStorage createContainer(EntityPlayer player) {
		return new ContainerCraftingStation(player, getPlayerInventory());
	}
	
	@Override
	public void update() { stationInventory.update(); }
	
	@Override
	public void dropContents() {
		for (ItemStack stack : crafting)
			WorldUtils.dropStackFromBlock(worldObj, getPos(), stack);
		if (stationInventory.outputIsReal)
			for (ItemStack stack : output)
				WorldUtils.dropStackFromBlock(worldObj, getPos(), stack);
		super.dropContents();
	}
	
	// IInventory implementation
	
	@Override
	public boolean hasCustomName() { return !shouldLocalizeTitle(); }
	@Override
	public int getInventoryStackLimit() { return 64; }
	@Override
	public int getSizeInventory() { return (getPlayerInventory().getSizeInventory() - 9); }
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return getPlayerInventory().getStackInSlot(slot + 9); }
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		getPlayerInventory().setInventorySlotContents(slot + 9, stack); }
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return getPlayerInventory().decrStackSize(slot + 9, amount); }

	@Override
	public ItemStack removeStackFromSlot(int i) {
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return getPlayerInventory().isItemValidForSlot(slot + 9, stack); }
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return getPlayerInventory().isUseableByPlayer(player); }
	

	public ItemStack getStackInSlotOnClosing(int slot) { return null; }
	@Override
	public void markDirty() { stationInventory.markDirty(); }
	@Override
	public void openInventory(EntityPlayer player) {  }
	@Override
	public void closeInventory(EntityPlayer player) {  }
	
	// ISidedInventory implementation
	
	private static int[] slotsAny = { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26 };
	private static int[] slotsBottom = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	
	@Override
	public int[] getSlotsForFace(EnumFacing facing) { return facing == EnumFacing.DOWN ? slotsBottom : slotsAny; }
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing facing) { return facing != EnumFacing.DOWN; }
	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing facing) {
		return ((facing != EnumFacing.DOWN) || (GlobalConfig.enableStationAutoCraftingSetting.getValue() &&
		                        stationInventory.canTake(null)));
	}
	
	// Reading from / writing to NBT
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NbtUtils.readItems(crafting, compound.getTagList("Crafting", NBT.TAG_COMPOUND));
		if (compound.hasKey("Output"))
			NbtUtils.readItems(output, compound.getTagList("Output", NBT.TAG_COMPOUND));
		stationInventory.progress = compound.getInteger("progress");
		stationInventory.outputIsReal = compound.hasKey("Output");
		// Update the inventory, causes ghost output to be initialized.
		stationInventory.inputChanged();
	}
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("Crafting", NbtUtils.writeItems(crafting));
		if (stationInventory.outputIsReal)
			compound.setTag("Output", NbtUtils.writeItems(output));
		compound.setInteger("progress", stationInventory.progress);
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		Arrays.fill(crafting, null);
		Arrays.fill(output, null);
	}
	
}
