package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketUpdateCostumeData extends AbstractPacket<PacketUpdateCostumeData> {

    NBTTagCompound dataTag;

    public PacketUpdateCostumeData() {}

    public PacketUpdateCostumeData(CostumeData data) {
        System.out.println("Update Packet");
        this.dataTag = data.writeToNBT(new NBTTagCompound());
    }

    public void encode(PacketBuffer buffer) throws IOException {
        System.out.println("Encode");
        buffer.writeNBTTagCompoundToBuffer(this.dataTag);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        System.out.println("Decode");
        this.dataTag = buffer.readNBTTagCompoundFromBuffer();
    }

    public void handle(EntityPlayer player) {
        System.out.println("[Server] Handle");
        CostumeData data = CostumeHandler.get(player);
        System.out.println(data.getUnlockedPieces().toString());
        data.readEquippedFromNBT(this.dataTag);
        CostumeHandler.add(data, player.getGameProfile().getId());
        BetterStorage.networkChannel.sendToAll(new PacketSendCostumeData(player.getGameProfile().getId(), data));
    }
}
