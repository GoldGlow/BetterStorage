package net.mcft.copy.betterstorage.item.goldenglow.customisation;

import net.mcft.copy.betterstorage.BetterStorage;
import net.mcft.copy.betterstorage.misc.Constants;
import net.mcft.copy.betterstorage.misc.Resources;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemCostume extends ItemArmor {

    String name;
    ModelBiped model;

    public ItemCostume(String name, int slot, ModelBiped model) {
        super(BetterStorage.materialCostume, 4, slot);
        setCreativeTab(BetterStorage.customisationTab);
        setUnlocalizedName(Constants.modId + "." + name);
        GameRegistry.registerItem(this, name);
        this.name = name;
        this.model = model;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Resources.getCostume(name).toString();
    }

    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot, ModelBiped _default) {
        return model;
    }
}
