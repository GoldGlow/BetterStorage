package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketStopMusic extends AbstractPacket<PacketStopMusic> {

    public PacketStopMusic() {}

    public void encode(PacketBuffer buffer) throws IOException {

    }

    public void decode(PacketBuffer buffer) throws IOException {

    }

    public void handle(EntityPlayer player) {
        BetterStorage.musicHandler.stopMusic();
    }
}
