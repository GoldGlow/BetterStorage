package net.mcft.copy.betterstorage.client.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderTickHandler {

    @SubscribeEvent
    public void renderLivingPre(RenderLivingEvent.Pre event) {
        if(event.entity instanceof EntityPlayer) {

        }
    }
}
