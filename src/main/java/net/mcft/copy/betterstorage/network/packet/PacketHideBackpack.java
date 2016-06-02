package net.mcft.copy.betterstorage.network.packet;

import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.network.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

public class PacketHideBackpack extends AbstractPacket<PacketHideBackpack> {

    public PacketHideBackpack(){}

    @Override
    public void encode(PacketBuffer buffer) throws IOException {
    }

    @Override
    public void decode(PacketBuffer buffer) throws IOException {
    }

    @Override
    public void handle(EntityPlayer player) {
        ItemStack stack = ItemBackpack.getBackpack(player);
        boolean visibility;
        if(stack!=null) {
            if (stack.hasTagCompound()) {
                if (!stack.getTagCompound().hasKey("isHidden"))
                    visibility = true;
                else
                    visibility = !stack.getTagCompound().getBoolean("isHidden");
                stack.getTagCompound().setBoolean("isHidden", visibility);
                System.out.println(stack.getTagCompound().getBoolean("isHidden"));
            } else {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setBoolean("isHidden", true);
                stack.setTagCompound(tagCompound);
            }
        }
    }
}
