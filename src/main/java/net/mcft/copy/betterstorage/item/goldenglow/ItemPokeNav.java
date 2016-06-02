package net.mcft.copy.betterstorage.item.goldenglow;

import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EnumSpecialTexture;
import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.client.gui.goldenglow.GuiPokenavMain;
import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPokeNav extends Item {

    public ItemPokeNav() {
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabTools);
        String name = MiscUtils.getName(this);
        setUnlocalizedName(Constants.modId + "." + name);
        GameRegistry.registerItem(this, name);
        BetterStorage.proxy.registerItemRender(this, 0, name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if(!world.isRemote) {
            return itemStack;
        }
        showGui(player);
        return itemStack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
        if(target instanceof EntityPixelmon) {
            ((EntityPixelmon) target).setSpecialTexture(EnumSpecialTexture.Online.id);
        }
        return super.itemInteractionForEntity(stack, playerIn, target);
    }


    @SideOnly(Side.CLIENT)
    void showGui(EntityPlayer player) {
        Minecraft.getMinecraft().displayGuiScreen(new GuiPokenavMain(player));
    }
}