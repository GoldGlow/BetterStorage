package net.mcft.copy.betterstorage.api.goldenglow;

import net.mcft.copy.betterstorage.content.Costumes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CostumeData {

    private ArrayList<CostumePiece> unlockedPieces = new ArrayList<CostumePiece>();
    private CostumePiece head,torso,legs,feet,extra;

    public CostumePiece getPiece(int slot) {
        switch (slot) {
            case 0:
                return this.head;
            case 1:
                return this.torso;
            case 2:
                return this.legs;
            case 3:
                return this.feet;
            case 4:
                return this.extra;
        }
        return null;
    }

    public boolean equipPiece(CostumePiece piece) {
        if(piece!=null) {
            if (hasPieceUnlocked(piece)) {
                switch (piece.getSlot()) {
                    case 0:
                        this.head = piece;
                        break;
                    case 1:
                        this.torso = piece;
                        break;
                    case 2:
                        this.legs = piece;
                        break;
                    case 3:
                        this.feet = piece;
                        break;
                    case 4:
                        this.extra = piece;
                        break;
                }
                return true;
            }
        }
        return false;
    }

    public void removePiece(int slot) {
        switch (slot) {
            case 0:
                this.head = null;
                break;
            case 1:
                this.torso = null;
                break;
            case 2:
                this.legs = null;
                break;
            case 3:
                this.feet = null;
                break;
            case 4:
                this.extra = null;
                break;
        }
    }

    public boolean unlockPiece(CostumePiece piece) {
        if(!this.unlockedPieces.contains(piece)) {
            this.unlockedPieces.add(piece);
            return true;
        }
        return false;
    }

    public boolean hasPieceUnlocked(CostumePiece piece) {
        return this.unlockedPieces.contains(piece);
    }

    public List<CostumePiece> getUnlockedPieces() {
        return this.unlockedPieces;
    }

    public CostumeData readFromNBT(NBTTagCompound nbtTagCompound) {
        if(nbtTagCompound.hasKey("unlocks")) {
            NBTTagList unlocks = nbtTagCompound.getTagList("unlocks", Constants.NBT.TAG_STRING);
            for(int i = 0; i < unlocks.tagCount(); i++) {
                NBTTagString pieceName = (NBTTagString)unlocks.get(i);
                if(Costumes.getCostumeByName(pieceName.getString())!=null) {
                    this.unlockedPieces.add(Costumes.getCostumeByName(pieceName.getString()));
                }
            }
        }
        if(nbtTagCompound.hasKey("equipped")) {
            NBTTagCompound equipped = nbtTagCompound.getCompoundTag("equipped");
            for(String key : equipped.getKeySet()) {
                String pieceName = equipped.getString(key);
                if(Costumes.getCostumeByName(pieceName)!=null) {
                    this.equipPiece(Costumes.getCostumeByName(pieceName));
                }
            }
        }
        return this;
    }

    public CostumeData readEquippedFromNBT(NBTTagCompound nbtTagCompound) {
        if(nbtTagCompound.hasKey("equipped")) {
            NBTTagCompound equipped = nbtTagCompound.getCompoundTag("equipped");
            for(String key : equipped.getKeySet()) {
                String pieceName = equipped.getString(key);
                if(Costumes.getCostumeByName(pieceName)!=null)
                    this.equipPiece(Costumes.getCostumeByName(pieceName));
            }
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        if(!this.unlockedPieces.isEmpty()) {
            NBTTagList unlocks = new NBTTagList();
            for (CostumePiece piece : this.unlockedPieces) {
                unlocks.appendTag(new NBTTagString(piece.getTextureName()));
            }
            nbtTagCompound.setTag("unlocks", unlocks);
        }

        NBTTagCompound equipped = new NBTTagCompound();

        if(this.head!=null)
            equipped.setString("head", this.head.getTextureName());
        if(this.torso!=null)
            equipped.setString("torso", this.torso.getTextureName());
        if(this.legs!=null)
            equipped.setString("legs", this.legs.getTextureName());
        if(this.feet!=null)
            equipped.setString("feet", this.feet.getTextureName());
        if(this.extra!=null)
            equipped.setString("extra", this.extra.getTextureName());

        if(!equipped.hasNoTags())
            nbtTagCompound.setTag("equipped", equipped);
        return nbtTagCompound;
    }
}
