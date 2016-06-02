package net.mcft.copy.betterstorage.network;

import java.util.List;

import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.network.packet.*;
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
	
	public ChannelHandler() {
		super(Constants.modId);
		register(0, Side.CLIENT, PacketOpenGui.class);
		register(1, Side.CLIENT, PacketBackpackTeleport.class);
		register(2, Side.CLIENT, PacketBackpackHasItems.class);
		register(3, Side.CLIENT, PacketBackpackIsOpen.class);
		register(4, Side.SERVER, PacketBackpackOpen.class);
		register(5, Side.CLIENT, PacketBackpackStack.class);
		register(6, Side.SERVER, PacketDrinkingHelmetUse.class);
		register(7, Side.SERVER, PacketLockHit.class);
		register(8, Side.CLIENT, PacketSyncSetting.class);
		register(9, Side.CLIENT, PacketPresentOpen.class);
		register(10, Side.SERVER, PacketHideBackpack.class);
		register(11, Side.SERVER, PacketSetBackpackSpecial.class);
		register(12, Side.CLIENT, PacketPlayMusic.class);
		register(13, Side.CLIENT, PacketStopMusic.class);
	}
	
	public <T extends IMessage & IMessageHandler<T, IMessage>> void register(int id, Side receivingSide, Class<T> messageClass) {
		registerMessage(messageClass, messageClass, id, receivingSide);
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
		for (EntityPlayer player : (List<EntityPlayer>)world.playerEntities) {
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
		//TODO (1.8): Not sure if func_151248_b is the proper replacement for func_151247_a.
		((WorldServer)entity.worldObj).getEntityTracker().func_151248_b(entity, getPacketFrom(message));
	}
	
	/** Sends a packet to everyone tracking an entity,
	 *  including the entity itself if it's a player. */
	public void sendToAndAllTracking(IMessage message, Entity entity) {
		sendToAllTracking(message, entity);
		if (entity instanceof EntityPlayer)
			sendTo(message, (EntityPlayer)entity);
	}
	
}
