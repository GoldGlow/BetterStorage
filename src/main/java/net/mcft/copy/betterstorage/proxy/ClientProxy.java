package net.mcft.copy.betterstorage.proxy;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.addon.Addon;
import net.mcft.copy.betterstorage.api.stand.BetterStorageArmorStand;
import net.mcft.copy.betterstorage.attachment.Attachment;
import net.mcft.copy.betterstorage.attachment.Attachments;
import net.mcft.copy.betterstorage.attachment.IHasAttachments;
import net.mcft.copy.betterstorage.client.handler.MusicHandler;
import net.mcft.copy.betterstorage.client.model.ModelBackpackArmor;
import net.mcft.copy.betterstorage.client.model.ModelCluckington;
import net.mcft.copy.betterstorage.client.renderer.*;
import net.mcft.copy.betterstorage.content.Costumes;
import net.mcft.copy.betterstorage.entity.EntityCluckington;
import net.mcft.copy.betterstorage.entity.EntityFrienderman;
import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.misc.Resources;
import net.mcft.copy.betterstorage.misc.handlers.KeyBindingHandler;
import net.mcft.copy.betterstorage.tile.TileBetterStorage;
import net.mcft.copy.betterstorage.tile.entity.*;
import net.mcft.copy.betterstorage.tile.stand.TileEntityArmorStand;
import net.mcft.copy.betterstorage.tile.stand.VanillaArmorStandRenderHandler;
import net.mcft.copy.betterstorage.utils.RenderUtils;
import net.mcft.copy.betterstorage.utils.WorldUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	
	public static int reinforcedChestRenderId;
	public static int lockerRenderId;
	public static int armorStandRenderId;
	public static int backpackRenderId;
	public static int reinforcedLockerRenderId;
	public static int lockableDoorRenderId;
	public static int presentRenderId;
	
	@Override
	public void initialize() {
		
		super.initialize();	
		new KeyBindingHandler();
		registerRenderers();
		BetterStorage.musicHandler = new MusicHandler();

		LayerCostume costumeLayer = new LayerCostume();
		Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default").addLayer(costumeLayer);
		Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim").addLayer(costumeLayer);
		Costumes.registerModels();
	}
	
	@Override
	protected void registerArmorStandHandlers() {
		super.registerArmorStandHandlers();
		BetterStorageArmorStand.registerRenderHandler(new VanillaArmorStandRenderHandler());
	}
	
	private void registerRenderers() {


		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityFrienderman.class, new RenderFrienderman(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityCluckington.class, new RenderChicken(manager, new ModelCluckington(), 0.4F));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityArmorStand.class, new TileEntityArmorStandRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBackpack.class, new TileEntityBackpackRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReinforcedChest.class, new TileEntityReinforcedChestRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLockableDoor.class, new TileEntityLockableDoorRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLocker.class, new TileEntityLockerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityReinforcedLocker.class, new TileEntityLockerRenderer());
		Addon.registerRenderersAll();

		registerBlockRenderers();
		registerItemRenderers();
	}

	private void registerBlockRenderers() {
		/*registerItemRender(BetterStorageItems.itemBackpack);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BetterStorageItems.itemEnderBackpack, 0, new ModelResourceLocation(Constants.modId+":"+BetterStorageItems.itemBackpack.getItemName(), "inventory"));
		registerItemRender(BetterStorageTiles.test);
		registerItemRender(BetterStorageTiles.crate);
		registerItemRender(BetterStorageTiles.reinforcedChest);
		registerItemRender(BetterStorageTiles.reinforcedLocker);
		registerItemRender(BetterStorageTiles.cardboardBox);
		registerItemRender(BetterStorageTiles.present);*/
	}

	private void registerItemRenderers() {

	}
	
	public static void registerItemRender(TileBetterStorage block) {
		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		modelMesher.register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Constants.modId+":"+block.getTileName(), "inventory"));
	}
	
	public static void registerItemRender(Item item) {
		if (item != null)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Constants.modId+":"+item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	@SubscribeEvent
	public void drawBlockHighlight(DrawBlockHighlightEvent event) {
		
		//TODO (1.8): Probably needs some severe changes.
		EntityPlayer player = event.player;
		World world = player.worldObj;
		MovingObjectPosition target = WorldUtils.rayTrace(player, event.partialTicks);
		
		if ((target == null) || (target.typeOfHit != MovingObjectType.BLOCK)) return;
		BlockPos pos = target.getBlockPos();
		
		AxisAlignedBB box = null;
		Block block = world.getChunkFromBlockCoords(pos).getBlock(pos);
		TileEntity tileEntity = world.getTileEntity(pos);
		
//		if (block instanceof TileArmorStand)
//			box = getArmorStandHighlightBox(player, world, pos, target.hitVec);
		/*else*/ if (block == Blocks.iron_door)
			box = getIronDoorHightlightBox(player, world, pos, target.hitVec, block);
//		else if (tileEntity instanceof IHasAttachments)
//			box = getAttachmentPointsHighlightBox(player, tileEntity, target);
		
		if (box == null) return;
		
		double xOff = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
		double yOff = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
		double zOff = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;
		box = box.offset(-xOff, -yOff, -zOff).expand(0.002, 0.002, 0.002);
        
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GlStateManager.depthMask(false);
		
		RenderGlobal.drawOutlinedBoundingBox(box, -1, 0, 0, 0);
		
		GlStateManager.depthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GlStateManager.disableBlend();
		
		event.setCanceled(true);
		
	}

	private AxisAlignedBB getArmorStandHighlightBox(EntityPlayer player, World world, BlockPos pos, Vec3 hitVec) {
		
		//TODO (1.8): A little rewrite required here.
		/*int metadata = world.getBlockMetadata(x, y, z);
		if (metadata > 0) y -= 1;
		
		TileEntityArmorStand armorStand = WorldUtils.get(world, x, y, z, TileEntityArmorStand.class);
		if (armorStand == null) return null;
		
		int slot = Math.max(0, Math.min(3, (int)((hitVec.yCoord - y) * 2)));
		
		double minX = x + 2 / 16.0;
		double minY = y + slot / 2.0;
		double minZ = z + 2 / 16.0;
		double maxX = x + 14 / 16.0;
		double maxY = y + slot / 2.0 + 0.5;
		double maxZ = z + 14 / 16.0;
		
		EnumArmorStandRegion region = EnumArmorStandRegion.values()[slot];
		for (ArmorStandEquipHandler handler : BetterStorageArmorStand.getEquipHandlers(region)) {
			ItemStack item = armorStand.getItem(handler);
			if (player.isSneaking()) {
				// Check if we can swap the player's equipped armor with armor stand's.
				ItemStack equipped = handler.getEquipment(player);
				if (((item == null) && (equipped == null)) ||
				    ((item != null) && !handler.isValidItem(player, item)) ||
				    ((equipped != null) && !handler.isValidItem(player, equipped)) ||
				    !handler.canSetEquipment(player, item)) continue;
				return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
			} else {
				// Check if we can swap the player's held item with armor stand's.
				ItemStack holding = player.getCurrentEquippedItem();
				if (((item == null) && (holding == null)) ||
				    ((holding != null) && !handler.isValidItem(player, holding))) continue;
				return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
			}
		}
		
		return AxisAlignedBB.getBoundingBox(minX, y, minZ, maxX, y + 2, maxZ);
		*/
		return null;
	}
	
	private AxisAlignedBB getAttachmentPointsHighlightBox(EntityPlayer player, TileEntity tileEntity,
	                                                      MovingObjectPosition target) {
		Attachments attachments = ((IHasAttachments)tileEntity).getAttachments();
		Attachment attachment = attachments.get(target.subHit);
		if (attachment == null) return null;
		return attachment.getHighlightBox();
	}
	
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
		ItemStack backpack = ItemBackpack.getBackpackData(event.entityPlayer).backpack;
		if (backpack != null) {
			EntityPlayer player = event.entityPlayer;
			float partial = event.partialRenderTick;
			ItemBackpack backpackType = (ItemBackpack)backpack.getItem();
			int color = backpackType.getColor(backpack);
			ModelBackpackArmor model = (ModelBackpackArmor)backpackType.getArmorModel(player, backpack, 0);
			
			//TODO (1.8): The reflection won't work anyways :P
			/*model.onGround = ReflectionUtils.invoke(
					RendererLivingEntity.class, event.renderer, "func_77040_d", "renderSwingProgress",
					EntityLivingBase.class, float.class, player, partial);*/
			model.setLivingAnimations(player, 0, 0, partial);
			
			RenderUtils.bindTexture(new ResourceLocation(backpackType.getArmorTexture(backpack, player, 0, null)));
			RenderUtils.setColorFromInt((color >= 0) ? color : 0xFFFFFF);
			model.render(player, 0, 0, 0, 0, 0, 0);
			
			if (color >= 0) {
				RenderUtils.bindTexture(new ResourceLocation(backpackType.getArmorTexture(backpack, player, 0, "overlay")));
				GlStateManager.color(1.0F, 1.0F, 1.0F);
				model.render(player, 0, 0, 0, 0, 0, 0);
			}
			
			if (backpack.isItemEnchanted()) {
				float f9 = player.ticksExisted + partial;
				
				RenderUtils.bindTexture(Resources.enchantedEffect);

				GlStateManager.enableBlend();
				GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
				GlStateManager.depthFunc(GL11.GL_EQUAL);
				GlStateManager.depthMask(false);
				for (int k = 0; k < 2; ++k) {
					GlStateManager.disableLighting();
					float f11 = 0.76F;
					GlStateManager.color(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
					GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					GlStateManager.matrixMode(GL11.GL_TEXTURE);
					GlStateManager.loadIdentity();
					float f12 = f9 * (0.001F + k * 0.003F) * 20.0F;
					float f13 = 0.33333334F;
					GlStateManager.scale(f13, f13, f13);
					GlStateManager.rotate(30.0F - k * 60.0F, 0.0F, 0.0F, 1.0F);
					GlStateManager.translate(0.0F, f12, 0.0F);
					GlStateManager.matrixMode(GL11.GL_MODELVIEW);
					model.render(player, 0, 0, 0, 0, 0, 0);
				}
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.matrixMode(GL11.GL_TEXTURE);
				GlStateManager.depthMask(true);
				GlStateManager.loadIdentity();
				GlStateManager.matrixMode(GL11.GL_MODELVIEW);
				GlStateManager.enableLighting();
				GlStateManager.disableBlend();
				GlStateManager.depthFunc(GL11.GL_LEQUAL);
			}
			
		} else backpack = ItemBackpack.getBackpack(event.entityPlayer);
		if (backpack != null) event.renderCape = false;
	}
	
	@Override
	public void registerItemRender(Item item, int id, String name, String type){
		BetterStorage.log.info("Registering item render for " + name + "...");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, id, new ModelResourceLocation(Constants.modId+ ":" + name, type));
	}
}
