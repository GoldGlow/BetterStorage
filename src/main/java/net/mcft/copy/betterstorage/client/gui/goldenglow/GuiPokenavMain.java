package net.mcft.copy.betterstorage.client.gui.goldenglow;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.api.goldenglow.EnumPokenavButtonType;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GuiPokenavMain extends GuiScreen {
    public EntityPlayer player;
    public static final BetterStorageResource texture = new BetterStorageResource("textures/gui/pokenav.png");

    int xSize = 200;
    int ySize = 160;
    int guiLeft;
    int guiTop;

    int i = 0;
    int i1 = 0;
    boolean transition;
    boolean transitioned;
    Map<Integer, GuiPokenavButton> buttons = new HashMap<Integer, GuiPokenavButton>();

    public GuiPokenavMain(EntityPlayer player) {
        this.player = player;
    }

    public GuiPokenavMain(EntityPlayer player, boolean skipTransition) {
        this.player = player;
        if(skipTransition) {
            transition = false;
            transitioned = true;
            i=ySize;
            i1=255;
        }
    }

    public void initGui() {
        super.initGui();

        guiLeft = (this.width-xSize)/2;
        guiTop = (this.height-ySize)/2;

        addButton(new GuiPokenavButton(0, this.width/2 - 32, guiTop + 48, EnumPokenavButtonType.Fly));
        addButton(new GuiPokenavButton(1, this.width/2 - 11, guiTop + 25, EnumPokenavButtonType.Follower));
        addButton(new GuiPokenavButton(2, this.width/2 + 10, guiTop + 48, EnumPokenavButtonType.Matchup));
        addButton(new GuiPokenavButton(3, this.width/2 - 32, guiTop + 73, EnumPokenavButtonType.Costumes));
    }

    public void drawScreen(int mouseX, int mouseY, float ticks) {
        if(transition) {
            if(i<ySize) {
                i += 10;
            } else if (i>=ySize && i1<255) {
                i1+=5;
            } else {
                for(GuiPokenavButton button : buttons.values()) {
                    button.visible=true;
                }
                transition=false;
                transitioned=true;
            }
        }
        this.mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, (this.height / 2 - 16) - i / 2 + 16, 0, 16, xSize, i);
        drawTexturedModalRect(guiLeft, (this.height / 2 - 16) - i / 2, 0, 0, xSize, 16);
        drawTexturedModalRect(guiLeft, (this.height / 2) + i / 2, 0, 184, xSize, 16);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255,
                (byte) (isHovering(this.width / 2 - 15, (this.height / 2 - 16) - i / 2, 32, 32, mouseX, mouseY) || transition || transitioned ? 255 : 150));
        drawTexturedModalRect(this.width / 2 - 15, (this.height / 2 - 16) - i / 2, xSize + 2, 0, 32, 32);

        GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) i1);
        if(transition) {
            this.mc.renderEngine.bindTexture(new BetterStorageResource("textures/gui/PokenavButtons.png"));
            for (GuiPokenavButton button : buttons.values()) {
                drawTexturedModalRect(button.xPosition, button.yPosition, 1, 23, 22, 22);
                drawTexturedModalRect(button.xPosition+(button.width/6), button.yPosition+(button.height/5), button.type.iconCoords[0], button.type.iconCoords[1], 16, 16);
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, ticks);
    }

    public void updateScreen() {
        super.updateScreen();
    }

    void addButton(GuiPokenavButton button) {
        if(!transitioned)
            button.visible = false;
        this.buttons.put(button.id, button);
        this.buttonList.add(button);
    }

    boolean isHovering(int posX, int posY, int width, int height, int mouseX, int mouseY) {
        return (mouseX >= posX && mouseX <= posX+width) && (mouseY >= posY && mouseY <= posY+height);
    }

    protected void actionPerformed(GuiButton button) {
        try {
            super.actionPerformed(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch(button.id) {
            case 0:
                Minecraft.getMinecraft().displayGuiScreen(new GuiPokenavFly(this.player));
                break;
            case 1:
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new GuiPokenavMatchup(this.player));
                break;
            case 3:
                Minecraft.getMinecraft().displayGuiScreen(new GuiCostume(this.player));
                break;
        }
    }

    protected void mouseClicked(int x, int y, int j) {
        try {
            super.mouseClicked(x, y, j);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(transition) {
            transition = false;
            transitioned = true;
            i=ySize;
            i1=255;
            for(GuiPokenavButton button : buttons.values()) {
                button.visible = true;
            }
        } else if(!transitioned) {
            transition = true;
        }

    }

}
