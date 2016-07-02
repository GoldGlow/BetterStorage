package net.mcft.copy.betterstorage.content;

import net.mcft.copy.betterstorage.api.goldenglow.CostumePiece;
import net.mcft.copy.betterstorage.client.model.goldenglow.ModelDuskullMask;
import net.mcft.copy.betterstorage.client.model.goldenglow.ModelKeyblade;
import net.mcft.copy.betterstorage.client.model.goldenglow.ModelThomas;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class Costumes {

    private static List<CostumePiece> costumeRegistry = new ArrayList<CostumePiece>();

    public static CostumePiece duskullMask;
    public static CostumePiece keyblade;
    public static CostumePiece thomas;

    public static void init() {
        costumeRegistry.add(duskullMask = new CostumePiece("Duskull Mask", 0));
        costumeRegistry.add(keyblade = new CostumePiece("Keyblade", 1));
        costumeRegistry.add(thomas = new CostumePiece("Thomas", 0));
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        duskullMask.setModel(new ModelDuskullMask());
        keyblade.setModel(new ModelKeyblade());
        thomas.setModel(new ModelThomas());
    }

    public static CostumePiece getCostumeByName(String name) {
        for(CostumePiece piece : costumeRegistry) {
            if(piece.getTextureName().equalsIgnoreCase(name)) {
                return piece;
            }
        }
        return null;
    }
}
