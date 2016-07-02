package net.mcft.copy.betterstorage.client.gui.goldenglow;

import net.mcft.copy.betterstorage.api.goldenglow.CostumePiece;
import net.mcft.copy.betterstorage.api.goldenglow.EnumPokenavButtonType;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiPokenavButton extends GuiButton {
    ResourceLocation buttonTextures = new BetterStorageResource("textures/gui/pokenavButtons.png");
    public EnumPokenavButtonType type;
    public CostumePiece costumePiece;
    public String label;
    public boolean tooltipBelow = false;
    int color;

    public GuiPokenavButton(int id, int x, int y, int color) {
        this(id, x, y, EnumPokenavButtonType.Colour);
        this.color=color;
    }

    public GuiPokenavButton(int id, int x, int y, EnumPokenavButtonType type) {
        super(id, x, y, 22, 22, type.tip);
        this.type=type;
        this.label=type.tip;
    }
    public GuiPokenavButton(int id, int x, int y, CostumePiece costumePiece) {
        super(id, x, y, 22, 22, costumePiece.getName());
        this.label=costumePiece.getName();
        this.costumePiece=costumePiece;
    }

    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
        FontRenderer fontrenderer = minecraft.fontRendererObj;
        if (this.visible)
        {
            minecraft.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.hovered);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, (k - 1) * 23 + 1, 23, this.width, this.height);
            if(this.type!=null) {
                if (this.type != EnumPokenavButtonType.Blank) {
                    if (this.type.equals(EnumPokenavButtonType.Colour)) {
                        this.drawGradientRect(this.xPosition + this.width / 6, (this.yPosition + this.height / 4) + (k - 3), 16, 16, color, color);
                    } else {
                        this.drawTexturedModalRect(this.xPosition + this.width / 6, (this.yPosition + this.height / 4) + (k - 3), type.iconCoords[0], type.iconCoords[1], 16, 16);
                    }
                }
            }
            else {
                if(this.costumePiece!=null) {
                    //minecraft.getTextureManager().bindTexture(new BetterStorageResource("/textures/sprites/costumes.png"));
                    //this.drawTexturedModalRect(this.xPosition + this.width / 6, (this.yPosition + this.height / 4) + (k - 3), 0, 0, 16, 16);
                    GlStateManager.pushMatrix();
                    minecraft.getTextureManager().bindTexture(this.costumePiece.getTexture());
                    GlStateManager.popMatrix();
                }
            }
            this.mouseDragged(minecraft, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.hovered)
            {
                l = 16777120;
            }

            if(k==2) {
                if(this.label!="") {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0,-7,1);
                    minecraft.getTextureManager().bindTexture(buttonTextures);
                    if(tooltipBelow) {
                        GlStateManager.translate(0, 46, 0);
                    }
                        int width = fontrenderer.getStringWidth(label);
                        drawTexturedModalRect((this.xPosition + (this.width / 2)) - width / 2 - 6, this.yPosition - 16, 1, 0, 6, 22);
                        if (width > 76) {
                            drawTexturedModalRect((this.xPosition + (this.width / 2)) - width / 2, this.yPosition - 16, 6, 0, 76, 22);
                            drawTexturedModalRect((this.xPosition + (this.width / 2)) - width / 2 + 76, this.yPosition - 16, 6, 0, width % 76, 22);
                        } else {
                            drawTexturedModalRect((this.xPosition + (this.width / 2)) - width / 2, this.yPosition - 16, 6, 0, width + 1, 22);
                        }
                        drawTexturedModalRect((this.xPosition + (this.width / 2)) + width / 2, this.yPosition - 16, 82, 0, 5, 22);
                        drawCenteredString(fontrenderer, label, this.xPosition + (this.width / 2), this.yPosition - 8, 0xffffff);
                    GlStateManager.popMatrix();
                }
            }
        }
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
    {
        return this.enabled && p_146116_2_ >= this.xPosition && p_146116_3_ >= this.yPosition && p_146116_2_ < this.xPosition + this.width && p_146116_3_ < this.yPosition + this.height;
    }
}
