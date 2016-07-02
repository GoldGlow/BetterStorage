package net.mcft.copy.betterstorage.api.goldenglow;

import net.mcft.copy.betterstorage.client.model.goldenglow.ModelCostume;
import net.mcft.copy.betterstorage.misc.BetterStorageResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CostumePiece {

    private String name;

    @SideOnly(Side.CLIENT)
    private ModelCostume model;

    private int slot;

    public CostumePiece(String name, int slot) {
        this.name = name;
        this.slot = slot;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getTexture() {
        return new BetterStorageResource("textures/models/"+getTextureName()+".png");
    }

    public String getTextureName() {
        String s = "";
        for(String word : this.name.split(" ")) {
            s = s + word;
        }
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    @SideOnly(Side.CLIENT)
    public void setModel(ModelCostume model) {
        this.model = model;
    }

    @SideOnly(Side.CLIENT)
    public ModelCostume getModel() {
        return this.model;
    }

    public int getSlot() {
        return this.slot;
    }

}