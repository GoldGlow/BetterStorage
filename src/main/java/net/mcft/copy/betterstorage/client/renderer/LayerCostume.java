package net.mcft.copy.betterstorage.client.renderer;

import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;

public class LayerCostume implements LayerRenderer {

    public void doRenderLayer(EntityPlayer player, float f, float f1, float partialTicks, float f2, float f3, float f4, float scale) {
        CostumeData costumeData = CostumeHandler.get(player);
        if(costumeData!=null) {
            if (costumeData.getPiece(0) != null) {
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(costumeData.getPiece(0).getTexture());
                costumeData.getPiece(0).getModel().render(player, f, f1, f2, f3, f4, scale);
            }
            if (costumeData.getPiece(1) != null) {
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(costumeData.getPiece(1).getTexture());
                costumeData.getPiece(1).getModel().render(player, f, f1, f2, f3, f4, scale);
            }
            if (costumeData.getPiece(2) != null) {
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(costumeData.getPiece(2).getTexture());
                costumeData.getPiece(2).getModel().render(player, f, f1, f2, f3, f4, scale);
            }
            if (costumeData.getPiece(3) != null) {
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(costumeData.getPiece(3).getTexture());
                costumeData.getPiece(3).getModel().render(player, f, f1, f2, f3, f4, scale);
            }
            if (costumeData.getPiece(4) != null) {
                Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(costumeData.getPiece(4).getTexture());
                costumeData.getPiece(4).getModel().render(player, f, f1, f2, f3, f4, scale);
            }
        }
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float f1, float f2, float partialTicks, float f3, float f4, float f5, float scale) {
        doRenderLayer((EntityPlayer)entitylivingbaseIn, f1,f2,partialTicks,f3,f4,f5,scale);
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
