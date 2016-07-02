package net.mcft.copy.betterstorage.client.model;

import java.util.List;

import net.mcft.copy.betterstorage.api.goldenglow.EnumSpecialBackpacks;
import net.mcft.copy.betterstorage.entity.EntityFrienderman;
import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.mcft.copy.betterstorage.misc.PropertiesBackpack;
import net.mcft.copy.betterstorage.tile.TileBackpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBackpackArmor extends ModelBiped {
	
	public final ModelBackpack baseModel;
	
	public ModelBackpackArmor(ModelBackpack baseModel) {
		this.baseModel = baseModel;
		
		textureWidth = baseModel.textureWidth;
		textureHeight = baseModel.textureHeight;
		
		bipedBody = new ModelRenderer(this);
		
		ModelRenderer model = new ModelRenderer(this);
		for (ModelRenderer box : baseModel.boxList) {
			box.rotationPointY -= baseModel.getModelHeight();
			box.rotationPointZ += 5.5F;
			bipedBody.addChild(box);
		}
		
		bipedBody.addChild(model);
	}
	
	@Override
	public void setLivingAnimations(EntityLivingBase entity, float par2, float par3, float partialTicks) {
		float angle = 0;
		if (entity != null) {
			PropertiesBackpack backpack = ItemBackpack.getBackpackData(entity);
			angle = backpack.prevLidAngle + (backpack.lidAngle - backpack.prevLidAngle) * partialTicks;
			angle = 1.0F - angle;
			angle = 1.0F - angle * angle;
		}
		if (baseModel.top != null)
			baseModel.top.rotateAngleX = (float)(angle * Math.PI / 4.0);
	}
	
	@Override
	public void render(Entity entity, float v1, float v2, float partialTicks, float v4, float v5, float v6) {
		if(!entity.isInvisible()) {
			ItemStack stack = ItemBackpack.getBackpack((EntityLivingBase) entity);
			if (stack.hasTagCompound()) {
				NBTTagCompound tag = stack.getTagCompound();
				if (tag.hasKey("isHidden") && tag.getBoolean("isHidden"))
					return;
				if (tag.hasKey("special") && tag.getInteger("special") > 0) {
					int specialType = tag.getInteger("special");
					if (specialType == EnumSpecialBackpacks.Rainbow.ordinal()) {
						int i = entity.ticksExisted / 25 + entity.getEntityId();
						int j = EnumDyeColor.values().length;
						int k = i % j;
						int l = (i + 1) % j;
						float f = ((float) (entity.ticksExisted % 25)) / 25.0F;
						float[] afloat1 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(k));
						float[] afloat2 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(l));
						GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
					}
				}
			}
			setRotationAngles(v1, v2, partialTicks, v4, v5, v6, entity);
			if (entity instanceof EntityFrienderman) {
				float y = bipedBody.rotationPointY;
				bipedBody.rotationPointY -= 18.0F;
				bipedBody.render(1 / 20.0F);
				bipedBody.rotationPointY = y;
			} else bipedBody.render(1 / 20.0F);
		}
	}
	
	@Override
	public void setRotationAngles(float v1, float v2, float v3, float v4, float v5, float v6, Entity entity) {
		// For some reason this is not properly updated.
		isSneak = ((entity != null) ? entity.isSneaking() : false);
		super.setRotationAngles(v1, v2, v3, v4, v5, v6, entity);
	}
	
}
