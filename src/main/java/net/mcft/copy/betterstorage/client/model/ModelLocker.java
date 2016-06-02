package net.mcft.copy.betterstorage.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Lockers - Jean Cena
 * Created using Tabula 5.1.0
 */
public class ModelLocker extends ModelBase {
    public ModelRenderer Base;
    public ModelRenderer BorderTop;
    public ModelRenderer BorderBottom;
    public ModelRenderer BorderLeft;
    public ModelRenderer BorderRight;
    public ModelRenderer Handle;

    public ModelLocker() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Base = new ModelRenderer(this, 0, 1);
        this.Base.setRotationPoint(-8.0F, 8.0F, -7.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 16, 15, 0.0F);
        this.BorderBottom = new ModelRenderer(this, 0, 0);
        this.BorderBottom.setRotationPoint(-8.0F, 24.0F, -8.0F);
        this.BorderBottom.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
        this.setRotateAngle(BorderBottom, 0.0F, 0.0F, -1.5707963267948966F);
        this.BorderLeft = new ModelRenderer(this, 0, 0);
        this.BorderLeft.setRotationPoint(7.0F, 8.0F, -8.0F);
        this.BorderLeft.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
        this.BorderRight = new ModelRenderer(this, 0, 0);
        this.BorderRight.setRotationPoint(-8.0F, 8.0F, -8.0F);
        this.BorderRight.addBox(0.0F, 0.0F, 0.0F, 1, 16, 1, 0.0F);
        this.Handle = new ModelRenderer(this, 51, 6);
        this.Handle.setRotationPoint(-6.0F, 15.0F, -8.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.BorderTop = new ModelRenderer(this, 0, 0);
        this.BorderTop.setRotationPoint(-8.0F, 9.0F, -8.0F);
        this.BorderTop.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.setRotateAngle(BorderTop, 0.0F, 0.0F, -1.5707963267948966F);
    }

    public void render(float f5) {
        this.Base.render(f5);
        this.BorderBottom.render(f5);
        this.BorderLeft.render(f5);
        this.BorderRight.render(f5);
        this.Handle.render(f5);
        this.BorderTop.render(f5);
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
