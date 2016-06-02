package net.mcft.copy.betterstorage.tile.entity;

import net.mcft.copy.betterstorage.api.lock.EnumLockInteraction;
import net.mcft.copy.betterstorage.api.lock.ILock;
import net.mcft.copy.betterstorage.api.lock.ILockable;
import net.mcft.copy.betterstorage.attachment.Attachments;
import net.mcft.copy.betterstorage.attachment.IHasAttachments;
import net.mcft.copy.betterstorage.attachment.LockAttachment;
import net.mcft.copy.betterstorage.misc.SetBlockFlag;
import net.mcft.copy.betterstorage.utils.WorldUtils;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumHingePosition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLockableDoor extends TileEntity implements ILockable, IHasAttachments, ITickable {

	public static final AxisAlignedBB AABB_LOCK = AxisAlignedBB.fromBounds(1.5, -2.5, -0.5, 6.5, 3.5, 3.5);
	private Attachments attachments = new Attachments(this);	
	public LockAttachment lockAttachment;
	private byte timeout;
	
	public TileEntityLockableDoor() {
		
		lockAttachment = attachments.add(LockAttachment.class);
		lockAttachment.setScale(0.5F, 1.5F);
		lockAttachment.setBox(AABB_LOCK);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return WorldUtils.getAABB(this, 0, 0, 0, 0, 1, 0);
	}
	
	private void updateLockPosition() {
		//Maybe we should use the orientation that the attachment has by itself.
		
		IBlockState state = getWorld().getBlockState(getPos());
		boolean isMirrored = ((EnumHingePosition) state.getValue(BlockDoor.HINGE)) == EnumHingePosition.RIGHT;
		boolean isOpen = (Boolean) state.getValue(BlockDoor.OPEN);
		EnumFacing facing = (EnumFacing) state.getValue(BlockDoor.FACING);
		if (isOpen && isMirrored) facing = facing.rotateYCCW();
		else if (isOpen) facing = facing.rotateY();
		
		lockAttachment.setDirection(facing);
		
		/*switch (orientation) {
		case WEST:
			if (isOpen) lockAttachment.setBox(12.5, -1.5, 1.5, 5, 6, 3);
			else lockAttachment.setBox(1.5, -1.5, 12.5, 3, 6, 5);
			break;
		case EAST:
			if (isOpen) lockAttachment.setBox(3.5, -1.5, 14.5, 5, 6, 3);
			else lockAttachment.setBox(14.5, -1.5, 3.5, 3, 6, 5);
			break;
		case SOUTH:
			if (isOpen) lockAttachment.setBox(1.5, -1.5, 3.5, 3, 6, 5);
			else lockAttachment.setBox(12.5, -1.5, 14.5, 5, 6, 3);
			break;
		default:
			if (isOpen) lockAttachment.setBox(14.5, -1.5, 12.5, 3, 6, 5);
			else lockAttachment.setBox(3.5, -1.5, 1.5, 5, 6, 3);
			break;
		}*/	
	}
	
	@Override
	public Attachments getAttachments() {
		return attachments;
	}

	@Override
	public ItemStack getLock() {
		return lockAttachment.getItem();
	}

	@Override
	public boolean isLockValid(ItemStack lock) {
		return (lock == null) || (lock.getItem() instanceof ILock);
	}

	@Override
	public void setLock(ItemStack lock) {		
		// Turn it back into a normal iron door
		if(lock == null) {
			lockAttachment.setItem(null);
			IBlockState stateUpper = WorldUtils.cloneBlockState(Blocks.iron_door.getDefaultState(), 
				getWorld().getBlockState(getPos().offset(EnumFacing.UP)).withProperty(BlockDoor.POWERED, false));
			IBlockState stateLower = WorldUtils.cloneBlockState(Blocks.iron_door.getDefaultState(), getWorld().getBlockState(getPos()));
			
			getWorld().setBlockState(getPos(), stateLower, SetBlockFlag.SEND_TO_CLIENT);
			getWorld().setBlockState(getPos().offset(EnumFacing.UP), stateUpper, SetBlockFlag.SEND_TO_CLIENT);
		} else setLockWithUpdate(lock);		
	}
	
	public void setLockWithUpdate(ItemStack lock) {
		lockAttachment.setItem(lock);
		updateLockPosition();
		worldObj.markBlockForUpdate(getPos());
		markDirty();
	}

	@Override
	public boolean canUse(EntityPlayer player) {
		return false;
	}

	@Override
	public void useUnlocked(EntityPlayer player) {
		IBlockState state = getWorld().getBlockState(getPos());
		state.withProperty(BlockDoor.OPEN, !(Boolean) state.getValue(BlockDoor.OPEN));
		worldObj.setBlockState(getPos(), state);
		updateLockPosition();
	}

	@Override
	public void applyTrigger() {
		IBlockState state = getWorld().getBlockState(getPos());
		state.withProperty(BlockDoor.POWERED, true);
		worldObj.setBlockState(getPos(), state);
		worldObj.scheduleUpdate(getPos(), getBlockType(), 10);
	}
	
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		if(canUse(player)) useUnlocked(player);
		else ((ILock)getLock().getItem()).applyEffects(getLock(), this, player, EnumLockInteraction.OPEN);
		return true;
	}

	@Override
	public void update() {	
		attachments.update();
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("lock")) 
			lockAttachment.setItem(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("lock")));
		updateLockPosition();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (lockAttachment.getItem() != null) 
			compound.setTag("lock", lockAttachment.getItem().writeToNBT(new NBTTagCompound()));
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToNBT(compound);
		return new S35PacketUpdateTileEntity(getPos(), 0, compound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		NBTTagCompound compound = pkt.getNbtCompound();
		if (!compound.hasKey("lock")) lockAttachment.setItem(null);
		else lockAttachment.setItem(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("lock")));
		updateLockPosition();
	}
	
}
