package net.mcft.copy.betterstorage.client.audio;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class BSMusic extends PositionedSoundRecord {

    public BSMusic(String music) {
        super(new ResourceLocation(music), 1.0f, 1.0f, 0.0F,0.0F,0.0F);
        this.repeat=true;
        this.repeatDelay=0;
        this.attenuationType=AttenuationType.NONE;
    }
}
