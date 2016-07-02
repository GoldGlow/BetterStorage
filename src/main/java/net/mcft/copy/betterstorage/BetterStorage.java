package net.mcft.copy.betterstorage;

import net.mcft.copy.betterstorage.addon.Addon;
import net.mcft.copy.betterstorage.client.handler.MusicHandler;
import net.mcft.copy.betterstorage.config.Config;
import net.mcft.copy.betterstorage.config.GlobalConfig;
import net.mcft.copy.betterstorage.content.BetterStorageEntities;
import net.mcft.copy.betterstorage.content.BetterStorageItems;
import net.mcft.copy.betterstorage.content.BetterStorageTileEntities;
import net.mcft.copy.betterstorage.content.BetterStorageTiles;
import net.mcft.copy.betterstorage.item.EnchantmentBetterStorage;
import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.misc.CreativeTabBetterStorage;
import net.mcft.copy.betterstorage.misc.DungeonLoot;
import net.mcft.copy.betterstorage.misc.Recipes;
import net.mcft.copy.betterstorage.misc.handlers.CostumeHandler;
import net.mcft.copy.betterstorage.misc.handlers.EventHandler;
import net.mcft.copy.betterstorage.misc.handlers.GuiHandler;
import net.mcft.copy.betterstorage.network.ChannelHandler;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketPlayMusic;
import net.mcft.copy.betterstorage.network.packet.goldenglow.PacketStopMusic;
import net.mcft.copy.betterstorage.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Constants.modId,
     name = Constants.modName,
     dependencies = "required-after:Forge; after:Thaumcraft; after:NotEnoughItems; after:pixelmon;",
     guiFactory = "net.mcft.copy.betterstorage.client.gui.BetterStorageGuiFactory")
public class BetterStorage {
	
	@Instance(Constants.modId)
	public static BetterStorage instance;
	
	@SidedProxy(serverSide = Constants.commonProxy,
	            clientSide = Constants.clientProxy)
	public static CommonProxy proxy;
	
	public static ChannelHandler networkChannel;
	
	public static Logger log;
	
	public static CreativeTabs creativeTab;

	public static Config globalConfig;

	public static MusicHandler musicHandler;
	public static CostumeHandler costumeHandler;
	public static EventHandler eventHandler;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		networkChannel = new ChannelHandler();
		log = event.getModLog();
		creativeTab = new CreativeTabBetterStorage();

		Addon.initialize();
		
		globalConfig = new GlobalConfig(event.getSuggestedConfigurationFile());
		Addon.setupConfigsAll();
		globalConfig.load();
		globalConfig.save();

		eventHandler = new EventHandler();
		costumeHandler = new CostumeHandler();
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		
		BetterStorageTiles.initialize();
		BetterStorageItems.initialize();
		
		EnchantmentBetterStorage.initialize();
		
		BetterStorageTileEntities.register();
		BetterStorageEntities.register();
		DungeonLoot.add();
		Recipes.add();
		proxy.initialize();

		NetworkRegistry.INSTANCE.registerGuiHandler(BetterStorage.instance, new GuiHandler());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Addon.postInitializeAll();
	}

	public static void test(String song, EntityPlayer player) {
		networkChannel.sendTo(new PacketPlayMusic(song), player);
	}

	public static void testStop(EntityPlayer player) {
		networkChannel.sendTo(new PacketStopMusic(), player);
	}
}
