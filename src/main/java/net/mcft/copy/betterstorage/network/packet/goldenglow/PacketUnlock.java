package net.mcft.copy.betterstorage.network.packet.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.api.goldenglow.CostumePiece;
import net.mcft.copy.betterstorage.content.Costumes;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketUnlock extends AbstractPacket<PacketUnlock> {

    String pieceName;

    public PacketUnlock() {}
    public PacketUnlock(String pieceName) {
        this.pieceName = pieceName;
    }
    public PacketUnlock(CostumePiece piece) {
        this.pieceName = piece.getTextureName();
    }

    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeString(this.pieceName);
    }

    public void decode(PacketBuffer buffer) throws IOException {
        this.pieceName = buffer.readStringFromBuffer(16);
    }

    public void handle(EntityPlayer player) {
        CostumePiece costumePiece = Costumes.getCostumeByName(this.pieceName);
        if(costumePiece!=null) {
            CostumeHandler.get(player).unlockPiece(costumePiece);
            BetterStorage.ggOverlay.costumeUnlock(costumePiece);
        }
    }
}
