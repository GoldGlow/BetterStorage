package net.mcft.copy.betterstorage.client.gui.goldenglow;

import net.mcft.copy.betterstorage.api.goldenglow.EnumPokenavButtonType;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import java.io.IOException;

public class GuiPokenavFly extends GuiScreen {
    public EntityPlayer player;
    public static final BetterStorageResource texture = new BetterStorageResource("textures/gui/pokenavFly.png");

    int xSize = 200;
    int ySize = 200;
    int guiLeft;
    int guiTop;

    public GuiPokenavFly(EntityPlayer player) {
        this.player = player;
    }

    public void initGui() {
        super.initGui();
        guiLeft = (this.width-xSize)/2;
        guiTop = (this.height-ySize)/2;
        GuiPokenavButton button = new GuiPokenavButton(0, guiLeft+xSize, guiTop+ySize, EnumPokenavButtonType.Back);
        this.buttonList.add(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if(button.id==0)
            mc.displayGuiScreen(new GuiPokenavMain(this.player, true));
    }
}
