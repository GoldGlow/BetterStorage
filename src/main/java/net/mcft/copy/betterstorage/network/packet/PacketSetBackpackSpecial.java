package net.mcft.copy.betterstorage.network.packet;

import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketSetBackpackSpecial extends AbstractPacket<PacketSetBackpackSpecial> {

    int specialId;

    public PacketSetBackpackSpecial() {}

    public PacketSetBackpackSpecial(int specialId) {
        this.specialId = specialId;
    }

    @Override
    public void encode(PacketBuffer buffer) throws IOException {
        buffer.writeInt(specialId);
    }

    @Override
    public void decode(PacketBuffer buffer) throws IOException {
        specialId = buffer.readInt();
    }

    @Override
    public void handle(EntityPlayer player) {
        ItemStack stack = ItemBackpack.getBackpack(player);
        if(stack.hasTagCompound()) {
            stack.getTagCompound().setInteger("special", specialId);
        } else {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("special", specialId);
            stack.setTagCompound(tagCompound);
        }
    }
}
