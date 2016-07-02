package net.mcft.copy.betterstorage.client.model.goldenglow;

import net.mcft.copy.betterstorage.item.ItemBackpack;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ModelKeyblade extends ModelCostume {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;
    public ModelRenderer shape5;
    public ModelRenderer shape6;
    public ModelRenderer shape7;
    public ModelRenderer shape8;
    public ModelRenderer shape9;
    public ModelRenderer shape10;

    public ModelKeyblade() {
        super(-5f, 0, 0, 0);
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.shape1 = new ModelRenderer(this, 6, 19);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);

        this.shape2 = new ModelRenderer(this, 6, 0);
        this.shape2.setRotationPoint(-2.5F, -1.0F, -0.5F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 6, 1, 2, 0.0F);

        this.shape3 = new ModelRenderer(this, 6, 0);
        this.shape3.setRotationPoint(-2.5F, 6.0F, -0.5F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 6, 1, 2, 0.0F);

        this.shape4 = new ModelRenderer(this, 0, 0);
        this.shape4.setRotationPoint(-2.8F, -0.8F, -0.5F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);

        this.shape5 = new ModelRenderer(this, 0, 0);
        this.shape5.setRotationPoint(2.8F, -0.8F, -0.5F);
        this.shape5.addBox(0.0F, 0.0F, 0.0F, 1, 8, 2, 0.0F);

        this.shape6 = new ModelRenderer(this, 0, 11);
        this.shape6.setRotationPoint(-0.5F, -17.0F, 0.0F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 2, 16, 1, 0.0F);

        this.shape7 = new ModelRenderer(this, 12, 3);
        this.shape7.setRotationPoint(1.5F, -16.8F, 0.0F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 1, 6, 1, 0.0F);

        this.shape8 = new ModelRenderer(this, 11, 12);
        this.shape8.setRotationPoint(2.5F, -16.6F, 0.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);

        this.shape9 = new ModelRenderer(this, 11, 12);
        this.shape9.setRotationPoint(2.5F, -12.0F, 0.0F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);

        this.shape10 = new ModelRenderer(this, 11, 12);
        this.shape10.setRotationPoint(2.0F, -14.4F, 0.0F);
        this.shape10.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);

        this.bipedBody.addChild(this.shape1);
        this.bipedBody.addChild(this.shape2);
        this.bipedBody.addChild(this.shape3);
        this.bipedBody.addChild(this.shape4);
        this.bipedBody.addChild(this.shape5);
        this.bipedBody.addChild(this.shape6);
        this.bipedBody.addChild(this.shape7);
        this.bipedBody.addChild(this.shape8);
        this.bipedBody.addChild(this.shape9);
        this.bipedBody.addChild(this.shape10);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if(!entity.isInvisible()) {
            setRotationAngles(f, f1, f2, f3, f4, f5, entity);
            GlStateManager.pushMatrix();
            ItemStack backpack = ItemBackpack.getBackpack((EntityPlayer)entity);
            if(backpack == null || (backpack.getTagCompound().hasKey("isHidden") && backpack.getTagCompound().getBoolean("isHidden"))) {
                GlStateManager.translate(-0.06f, 0.5f, 0.15f);
                GlStateManager.rotate(35, 0.0f, 0.0f, 1.0f);
            }
            else {
                GlStateManager.translate(-0.3f, 0.3f, 0.4f);
                GlStateManager.rotate(90, 0.0f, 0.0f, 1.0f);
            }
            if (entity != null && entity.isSneaking()) {
                GlStateManager.rotate(30, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(-15, 0.0f, 1.0f, 0.0f);
                GlStateManager.translate(0.0f, 0.0f, 0.15f);
            }
            this.shape10.render(f5);
            this.shape6.render(f5);
            this.shape4.render(f5);
            this.shape9.render(f5);
            this.shape2.render(f5);
            this.shape5.render(f5);
            this.shape8.render(f5);
            this.shape1.render(f5);
            this.shape7.render(f5);
            this.shape3.render(f5);
            GlStateManager.popMatrix();
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
