package net.mcft.copy.betterstorage.misc.handlers;

import net.mcft.copy.betterstorage.api.goldenglow.CostumeData;
import net.mcft.copy.betterstorage.content.Costumes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class CostumeHandler {

    private static HashMap<UUID, CostumeData> costumeMap = new HashMap<UUID, CostumeData>();

    public CostumeHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static CostumeData get(EntityPlayer player) {
        if(costumeMap.containsKey(player.getGameProfile().getId()))
            return costumeMap.get(player.getGameProfile().getId());
        return null;
    }
    public static CostumeData get(UUID uuid) {
        if(costumeMap.containsKey(uuid))
            return costumeMap.get(uuid);
        return null;
    }
    public static CostumeData get(String uuid) {
        if(costumeMap.containsKey(UUID.fromString(uuid)))
            return costumeMap.get(UUID.fromString(uuid));
        return null;
    }

    public static void add(CostumeData data, EntityPlayer player) {
        costumeMap.put(player.getGameProfile().getId(), data);
    }
    public static void add(CostumeData data, UUID uuid) {
        costumeMap.put(uuid, data);
    }
    public static void add(CostumeData data, String uuid) {
        costumeMap.put(UUID.fromString(uuid), data);
    }

    @SubscribeEvent
    public void onLoadPlayerFromFile(PlayerEvent.LoadFromFile event) {
        CostumeData data = new CostumeData();
        NBTTagCompound nbtTagCompound = null;
        try {
            File file = event.getPlayerFile("gg");
            if(file.exists() && file.isFile()) {
                nbtTagCompound = CompressedStreamTools.readCompressed(new FileInputStream(file));
                data.readFromNBT(nbtTagCompound);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        costumeMap.put(UUID.fromString(event.playerUUID), data);
    }

    @SubscribeEvent
    public void onSavePlayerToFile(PlayerEvent.SaveToFile event) {
        try {
            File tempFile = event.getPlayerFile("gg.tmp");
            File file = event.getPlayerFile("gg");
            CostumeData data = costumeMap.get(UUID.fromString(event.playerUUID));

            NBTTagCompound nbtTagCompound = data.writeToNBT(new NBTTagCompound());
            CompressedStreamTools.writeCompressed(nbtTagCompound, new FileOutputStream(tempFile));

            if(file.exists())
                file.delete();
            tempFile.renameTo(file);

        } catch (IOException e) {
            System.out.println("Failed to save player costume data for "+event.entityPlayer.getName());
        }
    }
}