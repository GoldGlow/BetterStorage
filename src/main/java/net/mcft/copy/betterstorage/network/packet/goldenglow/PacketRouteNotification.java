package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketRouteNotification extends AbstractPacket<PacketRouteNotification> {

    String routeName;

    public PacketRouteNotification(){}
    public PacketRouteNotification(String routeName) {
        this.routeName = routeName;
    }

    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeString(routeName);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        this.routeName = buffer.readStringFromBuffer(32);
    }

    public void handle(EntityPlayer player) {
        BetterStorage.ggOverlay.routeNotification(this.routeName);
    }
}
