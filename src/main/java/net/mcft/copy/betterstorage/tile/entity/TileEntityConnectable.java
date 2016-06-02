package net.mcft.copy.betterstorage.tile.entity;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.config.GlobalConfig;
import net.mcft.copy.betterstorage.inventory.InventoryTileEntity;
import net.mcft.copy.betterstorage.utils.DirectionUtils;
import net.mcft.copy.betterstorage.utils.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

public abstract class TileEntityConnectable extends TileEntityContainer implements IInventory {

	//TODO (1.8): This will most likely crash somewhere now.
	private EnumFacing orientation = null; //TODO (1.8): EnumFacing.UNKNOWN? Mojang knows everything!
	private EnumFacing connected = null;
	
	public EnumFacing getOrientation() { return orientation; }
	public void setOrientation(EnumFacing orientation) { this.orientation = orientation; }
	
	public EnumFacing getConnected() { return connected; }
	public void setConnected(EnumFacing connected) { this.connected = connected; }
	
	/** Returns the possible directions the container can connect to. */
	public abstract EnumFacing[] getPossibleNeighbors();
	
	/** Returns if this container is connected to another one. */
	public boolean isConnected() { return (getConnected() != null); }
	
	/** Returns if this container is the main container, or not connected to another container. */
	public boolean isMain() {
		EnumFacing connected = getConnected();
		return (!isConnected() || connected.getFrontOffsetX() + connected.getFrontOffsetY() + connected.getFrontOffsetZ() > 0);
	}
	
	/** Returns the main container. */
	public TileEntityConnectable getMainTileEntity() {
		if (isMain()) return this;
		TileEntityConnectable connectable = getConnectedTileEntity();
		if (connectable != null) return connectable;
		if (BetterStorage.globalConfig.getBoolean(GlobalConfig.enableWarningMessages))
			BetterStorage.log.warn(
					"getConnectedTileEntity() returned null in getMainTileEntity(). " +
					"Position: {}", getPos());
		return this;
	}
	
	/** Returns the connected container. */
	public TileEntityConnectable getConnectedTileEntity() {
		if (!isConnected()) return null;
		return WorldUtils.get(worldObj, getPos().offset(getConnected()), TileEntityConnectable.class);
	}
	
	/** Returns if the container can connect to the other container. */
	public boolean canConnect(TileEntityConnectable connectable) {
		return ((connectable != null) &&                                  // check for null
		        (getBlockType() == connectable.getBlockType()) &&         // check for same block type
		        (getOrientation() == connectable.getOrientation()) &&     // check for same orientation
		        // Make sure the containers are not already connected.
		        !isConnected() && !connectable.isConnected());
	}
	
	/** Connects the container to any other containers nearby, if possible. */
	public void checkForConnections() {
		if (worldObj.isRemote) return;
		TileEntityConnectable connectableFound = null;
		EnumFacing dirFound = null;
		for (EnumFacing dir : getPossibleNeighbors()) {
			TileEntityConnectable connectable = WorldUtils.get(worldObj, getPos().offset(dir), TileEntityConnectable.class);
			if (!canConnect(connectable)) continue;
			if (connectableFound != null) return;
			connectableFound = connectable;
			dirFound = dir;
		}
		if (connectableFound == null) return;
		setConnected(dirFound);
		connectableFound.setConnected(dirFound == null ? null : dirFound.getOpposite());
		// Mark the block for an update, sends description packet to players.
		markForUpdate();
		connectableFound.markForUpdate();
	}
	
	/** Disconnects the container from its connected container, if it has one. */
	public void disconnect() {
		if (!isConnected()) return;
		TileEntityConnectable connectable = getConnectedTileEntity();
		setConnected(null);
		if (connectable != null) {
			connectable.setConnected(null);
			connectable.markForUpdate();
		} else if (BetterStorage.globalConfig.getBoolean(GlobalConfig.enableWarningMessages))
			BetterStorage.log.warn(
					"getConnectedTileEntity() returned null in disconnect(). " +
					"Position: {}", getPos());
	}
	
	// TileEntityContainer stuff
	
	/** Returns the unlocalized name of the container. <br>
	 *  "Large" will be appended if the container is connected to another one. */
	protected abstract String getConnectableName();
	
	@Override
	public final String getName() { return (getConnectableName() + (isConnected() ? "Large" : "")); }
	@Override
	protected boolean doesSyncPlayers() { return true; }
	
	@Override
	public InventoryTileEntity getPlayerInventory() {
		TileEntityConnectable connected = getConnectedTileEntity();
		if (connected != null)
			return new InventoryTileEntity(this, (isMain() ? this : connected),
			                                     (isMain() ? connected : this));
		else return super.getPlayerInventory();
	}
	
	@Override
	public final void onBlockPlaced(EntityLivingBase player, ItemStack stack) {
		super.onBlockPlaced(player, stack);
		onBlockPlacedBeforeCheckingConnections(player, stack);
		checkForConnections();
	}
	
	@Override
	public void onBlockDestroyed() {
		super.onBlockDestroyed();
		disconnect();
	}
	
	// This is a horrible name for a function.
	protected void onBlockPlacedBeforeCheckingConnections(EntityLivingBase player, ItemStack stack) {
		setOrientation(DirectionUtils.getOrientation(player).getOpposite());
	}
	
	/** Returns if the container is accessible by other machines etc. */
	protected boolean isAccessible() { return true; }
	
	// Update entity
	
	@Override
	public void update() {
		super.update();
		
		double x = getPos().getX() + 0.5;
		double y = getPos().getY() + 0.5;
		double z = getPos().getZ() + 0.5;
		
		if (isConnected()) {
			if (!isMain()) return;
			TileEntityConnectable connectable = getConnectedTileEntity();
			if (connectable != null) {
				x = (x + connectable.getPos().getX() + 0.5) / 2;
				z = (z + connectable.getPos().getZ() + 0.5) / 2;
				lidAngle = Math.max(lidAngle, connectable.lidAngle);
			}
		}
		
		float pitch = worldObj.rand.nextFloat() * 0.1F + 0.9F;
		
		// Play sound when opening
		if ((lidAngle > 0.0F) && (prevLidAngle == 0.0F))
			worldObj.playSoundEffect(x, y, z, "random.chestopen", 0.5F, pitch);
		// Play sound when closing
		if ((lidAngle < 0.5F) && (prevLidAngle >= 0.5F))
			worldObj.playSoundEffect(x, y, z, "random.chestclosed", 0.5F, pitch);
	}
	
	// IInventory stuff
	
	@Override
	public boolean hasCustomName() { return !shouldLocalizeTitle(); }
	@Override
	public int getInventoryStackLimit() { return 64; }
	@Override
	public int getSizeInventory() {
		return (isAccessible() ? getPlayerInventory().getSizeInventory() : 0);
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		return (isAccessible() ? getPlayerInventory().getStackInSlot(slot) : null);
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (isAccessible()) getPlayerInventory().setInventorySlotContents(slot, stack);
	}
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return (isAccessible() ? getPlayerInventory().decrStackSize(slot, amount) : null);
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return (isAccessible() ? getPlayerInventory().isItemValidForSlot(slot, stack) : false);
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return (isAccessible() ? getPlayerInventory().isUseableByPlayer(player) : false);
	}

	public ItemStack getStackInSlotOnClosing(int slot) { return null; }
	@Override
	public void openInventory(EntityPlayer player) { if (isAccessible()) getPlayerInventory().openInventory(player); }
	@Override
	public void closeInventory(EntityPlayer player) { if (isAccessible()) getPlayerInventory().closeInventory(player); }
	@Override
	public void markDirty() { if (isAccessible()) getPlayerInventory().markDirty(); }
	
	// Tile entity synchronization
	
	public NBTTagCompound getDescriptionPacketData(NBTTagCompound compound) {
		
		byte orientationID = -1, connectedID = -1;
		if(orientation != null)
			orientationID = (byte)orientation.ordinal();
		if(connected != null)
			connectedID = (byte)connected.ordinal();
		
		compound.setByte("orientation", orientationID);
		compound.setByte("connected", connectedID);
		return compound;
	}
	
	@Override
	public Packet getDescriptionPacket() {
        return new S35PacketUpdateTileEntity(getPos(), 0, getDescriptionPacketData(new NBTTagCompound()));
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		NBTTagCompound compound = packet.getNbtCompound();
		int orientationID = compound.getByte("orientation"), connectedID =  compound.getByte("connected");
		if(orientationID != -1)
			setOrientation(EnumFacing.getFront(orientationID));
		if(connectedID != -1)
			setConnected(EnumFacing.getFront(connectedID));
	}
	
	// Reading from / writing to NBT
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		int orientationID = compound.getByte("orientation"), connectedID =  compound.getByte("connected");
		if(orientationID != -1)
			setOrientation(EnumFacing.getFront(orientationID));
		if(connectedID != -1)
			setConnected(EnumFacing.getFront(connectedID));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		byte orientationID = -1, connectedID = -1;
		if(orientation != null)
			orientationID = (byte)orientation.ordinal();
		if(connected != null)
			connectedID = (byte)connected.ordinal();
		
		compound.setByte("orientation", orientationID);
		compound.setByte("connected", connectedID);
	}
	
}
