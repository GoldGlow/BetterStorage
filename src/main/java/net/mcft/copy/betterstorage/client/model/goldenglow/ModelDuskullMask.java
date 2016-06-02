package net.mcft.copy.betterstorage.client.model.goldenglow;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class ModelDuskullMask extends ModelBiped {

    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape2_1;
    public ModelRenderer shape2_2;
    public ModelRenderer shape2_3;
    public ModelRenderer shape2_4;
    public ModelRenderer shape2_5;
    public ModelRenderer shape2_6;
    public ModelRenderer shape2_7;
    public ModelRenderer shape2_8;
    public ModelRenderer shape2_9;
    public ModelRenderer shape2_10;
    public ModelRenderer shape2_11;
    public ModelRenderer shape2_12;
    public ModelRenderer shape2_13;
    public ModelRenderer shape2_14;

    public ModelDuskullMask() {
        super(-5f, 0, 0, 0);
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(-1.0F, -7.0F, -5.5F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1, 0.0F);

        this.shape2 = new ModelRenderer(this, 20, 5);
        this.shape2.setRotationPoint(1.0F, -6.0F, -5.5F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);

        this.shape2_1 = new ModelRenderer(this, 20, 5);
        this.shape2_1.setRotationPoint(-3.0F, -6.0F, -5.5F);
        this.shape2_1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);

        this.shape2_2 = new ModelRenderer(this, 0, 3);
        this.shape2_2.setRotationPoint(-1.5F, -5.0F, -5.5F);
        this.shape2_2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);

        this.shape2_3 = new ModelRenderer(this, 27, 5);
        this.shape2_3.setRotationPoint(-3.5F, -5.0F, -5.2F);
        this.shape2_3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_4 = new ModelRenderer(this, 27, 5);
        this.shape2_4.setRotationPoint(2.5F, -5.0F, -5.2F);
        this.shape2_4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_5 = new ModelRenderer(this, 27, 3);
        this.shape2_5.setRotationPoint(-4.5F, -4.0F, -5.0F);
        this.shape2_5.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_6 = new ModelRenderer(this, 27, 3);
        this.shape2_6.setRotationPoint(3.5F, -4.0F, -5.0F);
        this.shape2_6.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_7 = new ModelRenderer(this, 27, 7);
        this.shape2_7.setRotationPoint(2.5F, -3.0F, -5.2F);
        this.shape2_7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_8 = new ModelRenderer(this, 27, 7);
        this.shape2_8.setRotationPoint(-3.5F, -3.0F, -5.2F);
        this.shape2_8.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_9 = new ModelRenderer(this, 9, 4);
        this.shape2_9.setRotationPoint(-0.5F, -4.0F, -5.5F);
        this.shape2_9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_10 = new ModelRenderer(this, 9, 2);
        this.shape2_10.setRotationPoint(-1.5F, -3.0F, -5.5F);
        this.shape2_10.addBox(0.0F, 0.0F, 0.0F, 3, 1, 1, 0.0F);

        this.shape2_11 = new ModelRenderer(this, 9, 0);
        this.shape2_11.setRotationPoint(-2.5F, -2.0F, -5.5F);
        this.shape2_11.addBox(0.0F, 0.0F, 0.0F, 5, 1, 1, 0.0F);

        this.shape2_12 = new ModelRenderer(this, 0, 16);
        this.shape2_12.setRotationPoint(-3.0F, -1.0F, -6.0F);
        this.shape2_12.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_13 = new ModelRenderer(this, 0, 13);
        this.shape2_13.setRotationPoint(-0.5F, -1.0F, -6.0F);
        this.shape2_13.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.shape2_14 = new ModelRenderer(this, 0, 16);
        this.shape2_14.setRotationPoint(2.0F, -1.0F, -6.0F);
        this.shape2_14.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);

        this.bipedHead.addChild(shape1);
        this.bipedHead.addChild(shape2);
        this.bipedHead.addChild(shape2_1);
        this.bipedHead.addChild(shape2_2);
        this.bipedHead.addChild(shape2_3);
        this.bipedHead.addChild(shape2_4);
        this.bipedHead.addChild(shape2_5);
        this.bipedHead.addChild(shape2_6);
        this.bipedHead.addChild(shape2_7);
        this.bipedHead.addChild(shape2_8);
        this.bipedHead.addChild(shape2_9);
        this.bipedHead.addChild(shape2_10);
        this.bipedHead.addChild(shape2_11);
        this.bipedHead.addChild(shape2_12);
        this.bipedHead.addChild(shape2_13);
        this.bipedHead.addChild(shape2_14);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f,f1,f2,f3,f4,f5, entity);
        if(entity!=null && entity.isSneaking()) {
                this.bipedBody.rotateAngleX = 0.5F;
                this.bipedRightArm.rotateAngleX += 0.4F;
                this.bipedLeftArm.rotateAngleX += 0.4F;
                this.bipedRightLeg.rotationPointZ = 4.0F;
                this.bipedLeftLeg.rotationPointZ = 4.0F;
                this.bipedRightLeg.rotationPointY = 13.0F;
                this.bipedLeftLeg.rotationPointY = 13.0F;
                this.bipedHead.rotationPointY = 4.5F;

                this.bipedBody.rotationPointY = 4.5F;
                this.bipedRightArm.rotationPointY = 5.0F;
                this.bipedLeftArm.rotationPointY = 5.0F;
        }
        if(entity!=null && entity instanceof EntityLivingBase) {
            this.heldItemRight = ((EntityLivingBase) entity).getHeldItem() != null ? 1 : 0;
            if (((EntityLivingBase)entity).isChild()) {
                float f6 = 2.0F;
                GL11.glPushMatrix();
                GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
                GL11.glTranslatef(0.0F, 16.0F * f5, 0.0F);
                this.bipedHead.render(f5);
                GL11.glPopMatrix();
            } else {
                GL11.glPushMatrix();
                GL11.glScalef(1.01F, 1.01F, 1.01F);
                this.bipedHead.render(f5);
                GL11.glPopMatrix();
            }
        }
        //super.render(entity, f, f1, f2, f3, f4, f5);

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