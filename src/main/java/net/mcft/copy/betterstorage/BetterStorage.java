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
import net.mcft.copy.betterstorage.misc.*;
import net.mcft.copy.betterstorage.misc.handlers.GuiHandler;
import net.mcft.copy.betterstorage.network.ChannelHandler;
import net.mcft.copy.betterstorage.network.packet.PacketPlayMusic;
import net.mcft.copy.betterstorage.network.packet.PacketStopMusic;
import net.mcft.copy.betterstorage.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Constants.modId,
     name = Constants.modName,
     dependencies = "required-after:Forge; after:Thaumcraft; after:NotEnoughItems;",
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
	public static CreativeTabs customisationTab;

	public static Config globalConfig;

	public static MusicHandler musicHandler;

	public static ItemArmor.ArmorMaterial materialCostume = EnumHelper.addArmorMaterial("COSTUME", "COSTUME", -1, new int[]{0,0,0,0}, 0);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		networkChannel = new ChannelHandler();
		log = event.getModLog();
		creativeTab = new CreativeTabBetterStorage();
		customisationTab = new CreativeTabGGCustomisation();

		Addon.initialize();
		
		globalConfig = new GlobalConfig(event.getSuggestedConfigurationFile());
		Addon.setupConfigsAll();
		globalConfig.load();
		globalConfig.save();
	}
	
	@EventHandler
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
	
	@EventHandler
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
