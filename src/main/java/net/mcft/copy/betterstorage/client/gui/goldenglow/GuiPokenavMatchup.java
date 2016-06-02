package net.mcft.copy.betterstorage.client.gui.goldenglow;

import com.pixelmonmod.pixelmon.client.ServerStorageDisplay;
import com.pixelmonmod.pixelmon.client.gui.GuiHelper;
import com.pixelmonmod.pixelmon.client.gui.GuiResources;
import com.pixelmonmod.pixelmon.comm.PixelmonData;
import com.pixelmonmod.pixelmon.config.PixelmonEntityList;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Gender;
import net.mcft.copy.betterstorage.api.goldenglow.EnumPokenavButtonType;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.mcft.copy.betterstorage.utils.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiPokenavMatchup extends GuiScreen {

    EntityPlayer player;
    public static final BetterStorageResource texture = new BetterStorageResource("textures/gui/pokenavMatchup.png");

    int xSize = 200;
    int ySize = 200;
    int guiLeft;
    int guiTop;

    PixelmonData[] pixelmonData;
    PixelmonData poke1;
    PixelmonData poke2;
    int selectedSlot = 0;
    int compatability = 0;
    int transI;

    GuiPokenavMatchup(EntityPlayer player) {
        this.player = player;
    }

    public void initGui() {
        super.initGui();
        this.guiLeft =(this.width-this.xSize)/2;
        this.guiTop =(this.height-this.ySize)/2;
        pixelmonData = ServerStorageDisplay.pokemon;
        int id = 1;
        for(PixelmonData p : pixelmonData) {
            if(p!=null) {
                this.buttonList.add(new GuiPokenavButton(id, (guiLeft + 9) + (id - 1) * 32, (guiTop + ySize) - 46, EnumPokenavButtonType.Blank));
                id++;
            }
        }
        GuiPokenavButton button = new GuiPokenavButton(0, guiLeft+xSize, guiTop+ySize, EnumPokenavButtonType.Back);
        button.tooltipBelow = true;
        this.buttonList.add(button);
        button = new GuiPokenavButton(this.buttonList.size(), guiLeft+(xSize/2)-12, guiTop+(ySize/2)+5, EnumPokenavButtonType.Blank);
        button.visible=false;
        button.enabled=false;
        this.buttonList.add(button);
    }

    public void drawScreen(int mouseX, int mouseY, float ticks) {
        this.mc.renderEngine.bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0,0, xSize, ySize);
        if(selectedSlot==1)
            drawTexturedModalRect(guiLeft + 25, guiTop + 75, xSize + 2, 0, 32, 32);
        if(selectedSlot==2)
            drawTexturedModalRect(guiLeft + 145, guiTop + 75, xSize + 2, 0, 32, 32);

        if(poke1!=null) {
            bindPokemonSprite(poke1.getNationalPokedexNumber(), poke1.isShiny, mc);
            GuiHelper.drawImageQuad(guiLeft + 23, guiTop + 72, 32.0D, 32.0F, 0.0D, 0.0D, 1.0D, 1.0D, this.zLevel);
            if(poke1.gender == Gender.Male) {
                Minecraft.getMinecraft().renderEngine.bindTexture(GuiResources.male);
            }
            else {
                Minecraft.getMinecraft().renderEngine.bindTexture(GuiResources.female);
            }
            GuiHelper.drawImageQuad(guiLeft + 38, guiTop + 110, 5.0D, 8.0F, 0.0D, 0.0D, 1.0D, 1.0D, this.zLevel);
        }
        if(poke2!=null) {
            bindPokemonSprite(poke2.getNationalPokedexNumber(), poke2.isShiny, mc);
            GuiHelper.drawImageQuad(guiLeft + 144, guiTop + 72, 32.0D, 32.0F, 0.0D, 0.0D, 1.0D, 1.0D, this.zLevel);
            if(poke2.gender == Gender.Male) {
                Minecraft.getMinecraft().renderEngine.bindTexture(GuiResources.male);
            }
            else {
                Minecraft.getMinecraft().renderEngine.bindTexture(GuiResources.female);
            }
            GuiHelper.drawImageQuad(guiLeft + 158, guiTop + 110, 5.0D, 8.0F, 0.0D, 0.0D, 1.0D, 1.0D, this.zLevel);

        }

        if(poke1!=null && poke2!=null && !((GuiPokenavButton)this.buttonList.get(this.buttonList.size()-1)).visible)
            ((GuiPokenavButton)this.buttonList.get(this.buttonList.size()-1)).visible=true;

        this.mc.renderEngine.bindTexture(texture);
        transI = (int) Math.round(MathUtil.approachExp(transI, compatability, 0.05D));
        drawTexturedModalRect(guiLeft + 83, guiTop + 80 - transI, 201, 65 - transI, 34, transI);

        super.drawScreen(mouseX, mouseY, ticks);

        int i = 0;
        for(PixelmonData p : pixelmonData) {
            if(p!=null) {
                bindPokemonSprite(p.getNationalPokedexNumber(), p.isShiny, mc);
                GuiHelper.drawImageQuad((guiLeft+9)+i, (guiTop+ySize)-48, 22.0D, 22.0F, 0.0D, 0.0D, 1.0D, 1.0D, this.zLevel);
                i+=32;
            }
        }
    }

    protected void actionPerformed(GuiButton button) {
        if(button.id==0) {
            mc.displayGuiScreen(new GuiPokenavMain(this.player, true));
        }else if(button.id==this.buttonList.size()-1) {
            transI=0;
            selectedSlot=0;
            if(poke1.gender == poke2.gender) {
                this.compatability = 12;
            } else {
                EntityPixelmon p1 = (EntityPixelmon) PixelmonEntityList.createEntityByName(poke1.name, player.worldObj);
                p1.gender = poke1.gender;
                EntityPixelmon p2 = (EntityPixelmon) PixelmonEntityList.createEntityByName(poke2.name, player.worldObj);
                p2.gender = poke2.gender;
                this.compatability = EntityPixelmon.canBreed(p1, p2) ? 42 : 22;
            }
        }
        else {
            switch (selectedSlot) {
                case 1:
                    if (pixelmonData[button.id - 1] != null && pixelmonData[button.id - 1] != poke2)
                        this.poke1 = pixelmonData[button.id - 1];
                    break;
                case 2:
                    if (pixelmonData[button.id - 1] != null && pixelmonData[button.id - 1] != poke1)
                        this.poke2 = pixelmonData[button.id - 1];
                    break;
                default:
                    break;
            }
        }
        if(this.poke1!=null && this.poke2!=null)
            this.buttonList.get(buttonList.size()-1).enabled = true;
        try {
            super.actionPerformed(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int click) {
        if(mouseX>=guiLeft + 24 && mouseX<=(guiLeft+24)+32 && mouseY>=guiTop+75 && mouseY<=(guiTop+75)+32) {
            this.selectedSlot=1;
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        } else
        if(mouseX>=guiLeft + 144 && mouseX<=(guiLeft+144)+32 && mouseY>=guiTop+75 && mouseY<=(guiTop+75)+32) {
            this.selectedSlot=2;
            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
        } else if(!clickedOnButton(mouseX, mouseY)) {
            this.selectedSlot=0;
        }
        try {
            super.mouseClicked(mouseX, mouseY, click);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean clickedOnButton(int mouseX, int mouseY) {
        for(Object b : this.buttonList) {
            if(b instanceof GuiButton) {
                GuiButton button = (GuiButton)b;
                if(mouseX>=button.xPosition && mouseX<=button.xPosition+button.width && mouseY>=button.yPosition && mouseY<=button.yPosition+button.height) {
                    return true;
                }
            }
        }
        return false;
    }

    void bindPokemonSprite(int npn, boolean isShiny, Minecraft mc)
    {
        String numString = "";
        if (npn < 10)
            numString = "00" + npn;
        else if (npn < 100)
            numString = "0" + npn;
        else
            numString = "" + npn;
        if (isShiny)
            mc.renderEngine.bindTexture(GuiResources.shinySprite(numString));
        else
            mc.renderEngine.bindTexture(GuiResources.sprite(numString));
    }
}
