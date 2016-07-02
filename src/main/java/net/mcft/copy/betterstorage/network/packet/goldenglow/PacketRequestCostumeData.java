package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;
import java.util.UUID;

public class PacketRequestCostumeData extends AbstractPacket<PacketRequestCostumeData> {

    UUID uuid;

    public PacketRequestCostumeData(){}

    public PacketRequestCostumeData(UUID uuid) {
        this.uuid = uuid;
    }

    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeUuid(this.uuid);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        this.uuid = buffer.readUuid();
    }

    public void handle(EntityPlayer player) {
        CostumeData data = CostumeHandler.get(this.uuid);
        BetterStorage.networkChannel.sendTo(new PacketSendCostumeData(this.uuid, data), player);
    }
}
