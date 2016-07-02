package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketPlayMusic extends AbstractPacket<PacketPlayMusic> {

    String song;

    public PacketPlayMusic() {}

    public PacketPlayMusic(String song) {
        this.song = song;
    }

    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.song);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        this.song = buffer.readStringFromBuffer(255);
    }

    public void handle(EntityPlayer player) {
        BetterStorage.musicHandler.playMusic("betterstorage:music."+this.song);
    }
}
