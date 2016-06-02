package net.mcft.copy.betterstorage.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public final class RenderUtils {
	
	private static final ResourceLocation glint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	
	private RenderUtils() {  }
	
	public static void renderItemIn3d(ItemStack stack) {
		GlStateManager.pushMatrix();	
		RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate(-0.5F, -0.5F, 1 / 32.0F);
		//itemRender.renderItem(stack);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}
	
	public static void setColorFromInt(int color) {
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
		GlStateManager.color(r, g, b, 1.0F);
	}
	
	public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height,
	                                         float zLevel, int textureWidth, int textureHeight) {
		float xScale = 1.0F / textureWidth;
		float yScale = 1.0F / textureHeight;
		WorldRenderer wr = Tessellator.getInstance().getWorldRenderer();
		wr.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		wr.pos(x +     0, y + height, zLevel).tex((u +     0) * xScale, (v + height) * yScale).endVertex();
		wr.pos(x + width, y + height, zLevel).tex((u + width) * xScale, (v + height) * yScale).endVertex();
		wr.pos(x + width, y +      0, zLevel).tex((u + width) * xScale, (v +      0) * yScale).endVertex();
		wr.pos(x +     0, y +      0, zLevel).tex((u +     0) * xScale, (v +      0) * yScale).endVertex();
		Tessellator.getInstance().draw();
	}
	
	public static void bindTexture(ResourceLocation texture) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}
	
}
