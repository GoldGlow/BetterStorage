package net.mcft.copy.betterstorage.network;

import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.network.packet.*;
import net.mcft.copy.betterstorage.network.packet.goldenglow.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ChannelHandler extends SimpleNetworkWrapper {

	int id = 0;
	
	public ChannelHandler() {
		super(Constants.modId);
		register(Side.CLIENT, PacketOpenGui.class);
		register(Side.CLIENT, PacketBackpackTeleport.class);
		register(Side.CLIENT, PacketBackpackHasItems.class);
		register(Side.CLIENT, PacketBackpackIsOpen.class);
		register(Side.SERVER, PacketBackpackOpen.class);
		register(Side.CLIENT, PacketBackpackStack.class);
		register(Side.SERVER, PacketDrinkingHelmetUse.class);
		register(Side.SERVER, PacketLockHit.class);
		register(Side.CLIENT, PacketSyncSetting.class);
		register(Side.CLIENT, PacketPresentOpen.class);
		register(Side.SERVER, PacketHideBackpack.class);
		register(Side.SERVER, PacketSetBackpackSpecial.class);
		register(Side.SERVER, PacketRequestCostumeData.class);
		register(Side.CLIENT, PacketSendCostumeData.class);
		register(Side.SERVER, PacketUpdateCostumeData.class);
		register(Side.CLIENT, PacketUnlock.class);
		register(Side.CLIENT, PacketRouteNotification.class);
	}
	
	public <T extends IMessage & IMessageHandler<T, IMessage>> void register(Side receivingSide, Class<T> messageClass) {
		registerMessage(messageClass, messageClass, id++, receivingSide);
	}
	
	// Sending packets
	
	public void sendTo(IMessage message, EntityPlayer player) {
		sendTo(message, (EntityPlayerMP)player);
	}
	
	public void sendToAllAround(IMessage message, World world, double x, double y, double z, double distance) {
		sendToAllAround(message, new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), x, y, z, distance));
	}
	
	public void sendToAllAround(IMessage message, World world, double x, double y, double z,
	                            double distance, EntityPlayer except) {
		for (EntityPlayer player : world.playerEntities) {
			if (player == except) continue;
			double dx = x - player.posX;
			double dy = y - player.posY;
			double dz = z - player.posZ;
            if ((dx * dx + dy * dy + dz * dz) < (distance * distance))
            	sendTo(message, player);
		}
	}
	
	/** Sends a packet to everyone tracking an entity. */
	public void sendToAllTracking(IMessage message, Entity entity) {
		((WorldServer)entity.worldObj).getEntityTracker().sendToAllTrackingEntity(entity, getPacketFrom(message));
	}
	
	/** Sends a packet to everyone tracking an entity,
	 *  including the entity itself if it's a player. */
	public void sendToAndAllTracking(IMessage message, Entity entity) {
		sendToAllTracking(message, entity);
		if (entity instanceof EntityPlayer)
			sendTo(message, (EntityPlayer)entity);
	}
	
}
