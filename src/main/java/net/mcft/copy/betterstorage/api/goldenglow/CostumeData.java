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
        System.out.println("1");
        if(piece!=null) {
            System.out.println("2 - "+this.unlockedPieces.toString());
            if (hasPieceUnlocked(piece)) {
                System.out.println("3");
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
            System.out.println("Unlocked piece: "+piece.getName());
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
        System.out.println("Reading from NBT");
        if(nbtTagCompound.hasKey("unlocks")) {
            System.out.println("Reading unlocks");
            NBTTagList unlocks = nbtTagCompound.getTagList("unlocks", Constants.NBT.TAG_STRING);
            for(int i = 0; i < unlocks.tagCount(); i++) {
                NBTTagString pieceName = (NBTTagString)unlocks.get(i);
                if(Costumes.getCostumeByName(pieceName.getString())!=null) {
                    System.out.println("Unlocking "+pieceName);
                    this.unlockedPieces.add(Costumes.getCostumeByName(pieceName.getString()));
                }
            }
        }
        if(nbtTagCompound.hasKey("equipped")) {
            System.out.println("Reading Equipped");
            NBTTagCompound equipped = nbtTagCompound.getCompoundTag("equipped");
            for(String key : equipped.getKeySet()) {
                String pieceName = equipped.getString(key);
                if(Costumes.getCostumeByName(pieceName)!=null) {
                    System.out.println("Equipping "+pieceName);
                    this.equipPiece(Costumes.getCostumeByName(pieceName));
                }
            }
        }
        System.out.println(this.head);
        System.out.println(this.torso);
        System.out.println(this.legs);
        System.out.println(this.feet);
        System.out.println(this.extra);
        return this;
    }

    public CostumeData readEquippedFromNBT(NBTTagCompound nbtTagCompound) {
        System.out.println("Reading Equipped from NBT");
        if(nbtTagCompound.hasKey("equipped")) {
            NBTTagCompound equipped = nbtTagCompound.getCompoundTag("equipped");
            for(String key : equipped.getKeySet()) {
                String pieceName = equipped.getString(key);
                System.out.println("Equipping: "+pieceName);
                if(Costumes.getCostumeByName(pieceName)!=null)
                    this.equipPiece(Costumes.getCostumeByName(pieceName));
            }
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        System.out.println("Writing NBT");
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


        System.out.println(this.head != null ? this.head.getName() : this.head);
        System.out.println(this.torso != null ? this.torso.getName() : this.torso);
        System.out.println(this.legs != null ? this.legs.getName() : this.legs);
        System.out.println(this.feet != null ? this.feet.getName() : this.feet);
        System.out.println(this.extra != null ? this.extra.getName() : this.extra);

        if(!equipped.hasNoTags())
            nbtTagCompound.setTag("equipped", equipped);
        System.out.println(nbtTagCompound.toString());
        return nbtTagCompound;
    }
}
