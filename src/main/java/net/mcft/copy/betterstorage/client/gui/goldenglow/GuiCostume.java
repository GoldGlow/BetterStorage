package net.mcft.copy.betterstorage.client.gui.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.api.goldenglow.CostumePiece;
import net.mcft.copy.betterstorage.api.goldenglow.EnumPokenavButtonType;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketUpdateCostumeData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiCostume extends GuiScreen {

    EntityPlayer player;
    CostumeData costumeData;

    public static final BetterStorageResource texture = new BetterStorageResource("textures/gui/pokenavCostume.png");

    int xSize = 200;
    int ySize = 200;
    int guiLeft;
    int guiTop;

    int lastMouseX;
    int initialMouseX;
    int initialMouseY;
    int scale = 50;

    boolean doRotate = true;
    int rotation = 50;

    //6 rows
    int scroll;
    int currentCategory;

    public GuiCostume(EntityPlayer player) {
        this.player = player;
        this.costumeData = CostumeHandler.get(player);
    }

    public void initGui() {
        this.guiLeft =(this.width-this.xSize)/2;
        this.guiTop =(this.height-this.ySize)/2;
        addInitialButtons();
        super.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);
        drawEntityOnScreen(guiLeft+40, (this.height/2)-5+this.scale, this.scale, player);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                mc.displayGuiScreen(new GuiPokenavMain(this.player, true));
                break;
            case 1:
                addButtons(0);
                currentCategory = 0;
                break;
            case 2:
                addButtons(1);
                currentCategory = 1;
                break;
            case 3:
                addButtons(2);
                currentCategory = 2;
                break;
            case 4:
                addButtons(3);
                currentCategory = 3;
                break;
            case 5:
                addButtons(4);
                currentCategory = 4;
                break;
            case 6:
                BetterStorage.networkChannel.sendToServer(new PacketUpdateCostumeData(this.costumeData));
                break;
            case 7:
                if(currentCategory>=0) {
                    this.costumeData.removePiece(currentCategory);
                }
                break;
            default:
                if(((GuiPokenavButton)button).costumePiece!=null) {
                    this.costumeData.equipPiece(((GuiPokenavButton) button).costumePiece);
                }
                break;
        }
    }

    void addInitialButtons() {
        GuiButton button = new GuiPokenavButton(0, guiLeft+xSize, guiTop+ySize, EnumPokenavButtonType.Back);
        this.buttonList.add(button);
        button = new GuiPokenavButton(1, guiLeft-22, guiTop+45, EnumPokenavButtonType.Hat);
        this.buttonList.add(button);
        button = new GuiPokenavButton(2, guiLeft-22, guiTop+67, EnumPokenavButtonType.Torso);
        this.buttonList.add(button);
        button = new GuiPokenavButton(3, guiLeft-22, guiTop+89, EnumPokenavButtonType.Legs);
        this.buttonList.add(button);
        button = new GuiPokenavButton(4, guiLeft-22, guiTop+111, EnumPokenavButtonType.Feet);
        this.buttonList.add(button);
        button = new GuiPokenavButton(5, guiLeft-22, guiTop+133, EnumPokenavButtonType.Extra);
        this.buttonList.add(button);
        button = new GuiPokenavButton(6, guiLeft+30, guiTop+170, EnumPokenavButtonType.Save);
        ((GuiPokenavButton)button).tooltipBelow = true;
        this.buttonList.add(button);
    }

    void addButtons(int category) {
        clearList();
        GuiPokenavButton clearButton = new GuiPokenavButton(7, guiLeft + xSize, guiTop+(this.ySize/2)-11, EnumPokenavButtonType.Clear);
        this.buttonList.add(clearButton);
        int i = 0;
        for (CostumePiece costumePiece : this.costumeData.getUnlockedPieces()) {
            if (costumePiece.getSlot() == category) {
                if(i>29) {

                } else {
                    GuiPokenavButton newButton = new GuiPokenavButton(8 + i, (guiLeft + 79) + ((i % 5) * 24), (guiTop + 30) + ((i / 5) * 24), costumePiece);
                    this.buttonList.add(newButton);
                }
                i++;
            }
        }
    }

    void clearList() {
        this.buttonList.clear();
        addInitialButtons();
    }

    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        //Scroll Zooming
        int i = Mouse.getEventDWheel();
        double j = 0;

        if (i != 0)
        {
            if (i > 1)
            {
                j = 2.5;
            }

            if (i < -1)
            {
                j = -2.5;
            }

            if (isShiftKeyDown())
            {
                j *= 2;
            }

            if(this.scale+j > 20 && this.scale+j < 70)
                this.scale += j;
        }
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if(timeSinceLastClick <= 50) {
            this.initialMouseX = mouseX;
            this.lastMouseX = mouseX;
            this.initialMouseY = mouseY;
        }

        if(((this.initialMouseX >= guiLeft && this.initialMouseX <= guiLeft+xSize)) && (this.initialMouseY >= guiTop && this.initialMouseY <= guiTop+ySize)) {
            this.doRotate = false;
            this.rotation += (-(this.lastMouseX - mouseX) * 2);
            this.lastMouseX = mouseX;
        }
    }

    public void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        if(this.doRotate)
            GlStateManager.rotate((this.rotation++)/4, 0.0F, 1.0F, 0.0F);
        else
            GlStateManager.rotate(this.rotation/4, 0.0F, 1.0F, 0.0F);
        ent.renderYawOffset = 0f;
        ent.rotationYaw = 0f;
        ent.rotationPitch = 0f;
        ent.rotationYawHead = 0f;
        ent.prevRotationYawHead = 0f;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}
