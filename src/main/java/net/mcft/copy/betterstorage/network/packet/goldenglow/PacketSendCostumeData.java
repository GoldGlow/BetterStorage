package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.UUID;

public class PacketSendCostumeData extends AbstractPacket<PacketSendCostumeData> {

    UUID uuid;
    NBTTagCompound dataTag;

    public PacketSendCostumeData(){}

    public PacketSendCostumeData(UUID uuid, CostumeData data) {
        this.uuid = uuid;
        this.dataTag = data.writeToNBT(new NBTTagCompound());
    }

    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeUuid(this.uuid);
        buffer.writeNBTTagCompoundToBuffer(this.dataTag);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        this.uuid = buffer.readUuid();
        this.dataTag = buffer.readNBTTagCompoundFromBuffer();
    }

    public void handle(EntityPlayer player) {
        CostumeData data = new CostumeData();
        data.readFromNBT(this.dataTag);
        CostumeHandler.add(data, this.uuid);
    }
}
