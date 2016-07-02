package net.mcft.copy.betterstorage.misc.handlers;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.config.GlobalConfig;
import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.misc.EquipmentSlot;
import net.mcft.copy.betterstorage.network.packet.PacketBackpackOpen;
import net.mcft.copy.betterstorage.network.packet.PacketDrinkingHelmetUse;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketHideBackpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

//@SideOnly(Side.CLIENT)
public class KeyBindingHandler {
	
	public static final KeyBinding backpackOpen =
			new KeyBinding("key.betterstorage.backpackOpen", Keyboard.KEY_B, "key.categories.gameplay");
	public static final KeyBinding drinkingHelmet =
			new KeyBinding("key.betterstorage.drinkingHelmet", Keyboard.KEY_F, "key.categories.gameplay");
	public static final KeyBinding hideBackpack =
			new KeyBinding("key.betterstorage.hideBackpack", Keyboard.KEY_H, "key.categories.gameplay");
	
	private static final KeyBinding[] bindings = new KeyBinding[]{ backpackOpen, drinkingHelmet, hideBackpack };
	
	public KeyBindingHandler() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);

		ClientRegistry.registerKeyBinding(backpackOpen);
		ClientRegistry.registerKeyBinding(drinkingHelmet);
		ClientRegistry.registerKeyBinding(hideBackpack);
	}
	
	@SubscribeEvent
	public void onKey(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		if (!mc.inGameHasFocus || (player == null)) return;
		if (backpackOpen.isPressed() && (ItemBackpack.getBackpack(player) != null) &&
		    BetterStorage.globalConfig.getBoolean(GlobalConfig.enableBackpackOpen))
			BetterStorage.networkChannel.sendToServer(new PacketBackpackOpen());
		else if (drinkingHelmet.isPressed() && (player.getEquipmentInSlot(EquipmentSlot.HEAD) != null))
			BetterStorage.networkChannel.sendToServer(new PacketDrinkingHelmetUse());
		else if (hideBackpack.isPressed()) {
			BetterStorage.networkChannel.sendToServer(new PacketHideBackpack());
		}
	}
	
}
