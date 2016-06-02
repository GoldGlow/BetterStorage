package net.mcft.copy.betterstorage.client.model;

import net.minecraft.client.model.ModelRenderer;

/**
 * Lockers - Jean Cena
 * Created using Tabula 5.1.0
 */
public class ModelLargeLocker extends ModelLocker {
    public ModelRenderer Base;
    public ModelRenderer BorderTop;
    public ModelRenderer BorderBottom;
    public ModelRenderer BorderLeft;
    public ModelRenderer BorderRight;
    public ModelRenderer Handle;

    public ModelLargeLocker() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.BorderRight = new ModelRenderer(this, 0, -1);
        this.BorderRight.setRotationPoint(-8.0F, -8.0F, -8.0F);
        this.BorderRight.addBox(0.0F, 1.0F, 0.0F, 1, 31, 1, 0.0F);
        this.Base = new ModelRenderer(this, 2, 17);
        this.Base.setRotationPoint(-8.0F, -8.0F, -7.0F);
        this.Base.addBox(0.0F, 0.0F, 0.0F, 16, 32, 15, 0.0F);
        this.BorderTop = new ModelRenderer(this, 4, 0);
        this.BorderTop.setRotationPoint(-8.0F, -8.0F, -8.0F);
        this.BorderTop.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
        this.Handle = new ModelRenderer(this, 55, 23);
        this.Handle.setRotationPoint(-6.0F, 8.0F, -8.0F);
        this.Handle.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.BorderLeft = new ModelRenderer(this, 0, -1);
        this.BorderLeft.setRotationPoint(7.0F, -8.0F, -8.0F);
        this.BorderLeft.addBox(0.0F, 1.0F, 0.0F, 1, 31, 1, 0.0F);
        this.BorderBottom = new ModelRenderer(this, 4, 0);
        this.BorderBottom.setRotationPoint(-8.0F, 23.0F, -8.0F);
        this.BorderBottom.addBox(0.0F, 0.0F, 0.0F, 16, 1, 1, 0.0F);
    }

    public void render(float f5) {
        this.BorderRight.render(f5);
        this.Base.render(f5);
        this.BorderTop.render(f5);
        this.Handle.render(f5);
        this.BorderLeft.render(f5);
        this.BorderBottom.render(f5);
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
