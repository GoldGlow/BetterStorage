package net.mcft.copy.betterstorage.client.handler;

import net.mcft.copy.betterstorage.client.audio.BSMusic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class MusicHandler {

    ResourceLocation playingResource;
    BSMusic playing;
    float musicVolume;
    float recordVolume;

    public MusicHandler() {
    }

    public void playMusic(String music) {
        if (isPlaying(music))
            return;
        if (this.playingResource == null) {
            //musicVolume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MUSIC);
            //recordVolume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RECORDS);
        }
        stopMusic();
        Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, 0.0f);
       // if(recordVolume == 0f)
        //    Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, 1.0f);
        this.playingResource = new ResourceLocation(music);

        this.playing = new BSMusic(music);
        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        handler.playSound(this.playing);
        System.out.println("Now playing: "+music);
    }

    public void stopMusic() {
        SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        if (this.playing != null)
            handler.stopSound(this.playing);
        handler.stopSounds();
        this.playingResource = null;
        this.playing = null;
        //Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.MUSIC, musicVolume);
        //Minecraft.getMinecraft().gameSettings.setSoundLevel(SoundCategory.RECORDS, recordVolume);
    }

    public boolean isPlaying(String music) {
        ResourceLocation resource = new ResourceLocation(music);
        if ((this.playingResource == null) || (!this.playingResource.equals(resource))) {
            return false;
        }
        return Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(this.playing);
    }

}
