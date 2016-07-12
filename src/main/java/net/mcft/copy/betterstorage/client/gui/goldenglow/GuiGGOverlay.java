package net.mcft.copy.betterstorage.client.gui.goldenglow;

import net.mcft.copy.betterstorage.api.goldenglow.CostumePiece;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiGGOverlay extends Gui {

    private static final ResourceLocation achievementBg = new BetterStorageResource("textures/gui/notifications.png");
    public Minecraft mc;
    public FontRenderer fontRenderer;
    int screenWidth;
    int screenHeight;

    CostumePiece unlock;
    String routeName;
    private long notificationTime;

    public GuiGGOverlay() {
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = mc.fontRendererObj;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void costumeUnlock(CostumePiece piece) {
        this.unlock = piece;
        this.routeName = "";
        this.notificationTime = Minecraft.getSystemTime();
    }

    public void routeNotification(String routeName) {
        this.routeName = routeName;
        this.unlock = null;
        this.notificationTime = Minecraft.getSystemTime();
    }

    void updateWindowScale() {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        this.screenWidth = this.mc.displayWidth;
        this.screenHeight = this.mc.displayHeight;
        ScaledResolution scaledresolution = new ScaledResolution(this.mc);
        this.screenWidth = scaledresolution.getScaledWidth();
        this.screenHeight = scaledresolution.getScaledHeight();
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double)this.screenWidth, (double)this.screenHeight, 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type.equals(RenderGameOverlayEvent.ElementType.ALL)) {
            if (fontRenderer == null) {
                this.fontRenderer = mc.fontRendererObj;
            }
            if (this.unlock!=null && this.notificationTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
                double d0 = (double) (Minecraft.getSystemTime() - this.notificationTime) / 5000.0D;


                if (d0 < 0.0D || d0 > 1.0D) {
                    this.notificationTime = 0L;
                    return;
                }
                this.updateWindowScale();
                GlStateManager.disableDepth();
                GlStateManager.depthMask(false);

                double d1 = d0 * 2.0D;

                if (d1 > 1.0D) {
                    d1 = 2.0D - d1;
                }

                d1 = d1 * 4.0D;
                d1 = 1.0D - d1;

                if (d1 < 0.0D) {
                    d1 = 0.0D;
                }

                d1 = d1 * d1;
                d1 = d1 * d1;
                int i = this.screenWidth - 240;
                int j = (this.screenHeight - 64) + (int) (d1 * 68.0D);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, (float) (0.75F - (d1 * 1F)));
                GlStateManager.enableTexture2D();
                this.mc.getTextureManager().bindTexture(achievementBg);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(i, j, 0, 0, 240, 64);
                drawCenteredString(this.fontRenderer, "Costume Unlocked!", i + 120, j + 27, -252);
                drawCenteredString(this.fontRenderer, this.unlock.getName(), i + 120, j + 38, -1);
                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.disableLighting();
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
            }
            else if (this.routeName!=null && !this.routeName.isEmpty() && this.notificationTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
                double d0 = (double) (Minecraft.getSystemTime() - this.notificationTime) / 5000.0D;


                if (d0 < 0.0D || d0 > 1.0D) {
                    this.notificationTime = 0L;
                    return;
                }
                this.updateWindowScale();
                GlStateManager.disableDepth();
                GlStateManager.depthMask(false);

                double d1 = d0 * 2.0D;

                if (d1 > 1.0D) {
                    d1 = 2.0D - d1;
                }

                d1 = d1 * 4.0D;
                d1 = 1.0D - d1;

                if (d1 < 0.0D) {
                    d1 = 0.0D;
                }

                d1 = d1 * d1;
                d1 = d1 * d1;
                int i = (this.screenWidth - 75)/2;
                int j = 0 - (int) (d1 * 60.0D);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableTexture2D();
                this.mc.getTextureManager().bindTexture(achievementBg);
                GlStateManager.disableLighting();
                this.drawTexturedModalRect(i, j, 0, 64, 95, 55);
                drawCenteredString(this.fontRenderer, this.routeName, i+47, j + 28, -1);
                GlStateManager.disableLighting();
                GlStateManager.enableRescaleNormal();
                GlStateManager.enableColorMaterial();
                GlStateManager.depthMask(true);
                GlStateManager.enableDepth();
            }
        }
    }
}
