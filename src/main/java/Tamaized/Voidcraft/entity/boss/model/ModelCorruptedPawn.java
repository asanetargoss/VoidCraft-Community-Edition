// Date: 8/6/2014 12:40:17 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Tamaized.Voidcraft.entity.boss.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class ModelCorruptedPawn extends ModelBase
{
	//fields
    ModelRenderer head;
    ModelRenderer rightarm;
    ModelRenderer leftarm;
    ModelRenderer rightleg;
    ModelRenderer leftleg;
    ModelRenderer TopSide;
    ModelRenderer RightSide;
    ModelRenderer BotSide;
    ModelRenderer LeftSide;
  
  public ModelCorruptedPawn()
  {
	  	textureWidth = 128;
	    textureHeight = 64;
	    
	      head = new ModelRenderer(this, 0, 0);
	      head.addBox(-4F, -8F, -4F, 16, 16, 16);
	      head.setRotationPoint(-4F, 0F, -2F);
	      head.setTextureSize(128, 64);
	      head.mirror = true;
	      setRotation(head, 0F, 0F, 0F);
	      rightarm = new ModelRenderer(this, 32, 41);
	      rightarm.addBox(-2F, 0F, -2F, 4, 12, 4);
	      rightarm.setRotationPoint(-9F, -9F, 2F);
	      rightarm.setTextureSize(128, 64);
	      rightarm.mirror = true;
	      setRotation(rightarm, 0F, 0F, 2.324799F);
	      leftarm = new ModelRenderer(this, 32, 41);
	      leftarm.addBox(-2F, 0F, -2F, 4, 12, 4);
	      leftarm.setRotationPoint(9F, -9F, 2F);
	      leftarm.setTextureSize(128, 64);
	      leftarm.mirror = true;
	      setRotation(leftarm, 0F, 0F, -2.324796F);
	      rightleg = new ModelRenderer(this, 32, 41);
	      rightleg.addBox(-2F, 0F, -2F, 4, 12, 4);
	      rightleg.setRotationPoint(-9F, 9F, 2F);
	      rightleg.setTextureSize(128, 64);
	      rightleg.mirror = true;
	      setRotation(rightleg, 0F, 0F, 0.7435722F);
	      leftleg = new ModelRenderer(this, 32, 41);
	      leftleg.addBox(-2F, 0F, -2F, 4, 12, 4);
	      leftleg.setRotationPoint(9F, 9F, 2F);
	      leftleg.setTextureSize(128, 64);
	      leftleg.mirror = true;
	      setRotation(leftleg, 0F, 0F, -0.7435801F);
	      TopSide = new ModelRenderer(this, 32, 41);
	      TopSide.addBox(-2F, 0F, -2F, 4, 12, 4);
	      TopSide.setRotationPoint(0F, -9F, 2F);
	      TopSide.setTextureSize(128, 64);
	      TopSide.mirror = true;
	      setRotation(TopSide, 0F, 0F, 3.141593F);
	      RightSide = new ModelRenderer(this, 32, 41);
	      RightSide.addBox(-2F, 0F, -2F, 4, 12, 4);
	      RightSide.setRotationPoint(-9F, 0F, 2F);
	      RightSide.setTextureSize(128, 64);
	      RightSide.mirror = true;
	      setRotation(RightSide, 0F, 0F, 1.570796F);
	      BotSide = new ModelRenderer(this, 32, 41);
	      BotSide.addBox(-2F, 0F, -2F, 4, 12, 4);
	      BotSide.setRotationPoint(0F, 9F, 2F);
	      BotSide.setTextureSize(128, 64);
	      BotSide.mirror = true;
	      setRotation(BotSide, 0F, 0F, 0F);
	      LeftSide = new ModelRenderer(this, 32, 41);
	      LeftSide.addBox(-2F, 0F, -2F, 4, 12, 4);
	      LeftSide.setRotationPoint(9F, 0F, 2F);
	      LeftSide.setTextureSize(128, 64);
	      LeftSide.mirror = true;
	      setRotation(LeftSide, 0F, 0F, -1.570796F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  	super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    head.render(f5);
	    rightarm.render(f5);
	    leftarm.render(f5);
	    rightleg.render(f5);
	    leftleg.render(f5);
	    TopSide.render(f5);
	    RightSide.render(f5);
	    BotSide.render(f5);
	    LeftSide.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
	  model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
  }
  
  /**
   * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
   * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
   * "far" arms and legs can swing at most.
   */
  //par1 = f = time
  //par2 = f1 = far
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
   super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
   
   this.head.rotateAngleX = f4 / (180F / (float)Math.PI);
   this.head.rotateAngleY = f3 / (180F / (float)Math.PI);
   //this.Headwear.rotateAngleX = f4 / (180F / (float)Math.PI);
   //this.Headwear.rotateAngleY = f3 / (180F / (float)Math.PI);
   //this.leftarm.rotateAngleX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
   this.rightarm.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.leftarm.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.rightleg.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.leftleg.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.TopSide.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.LeftSide.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.RightSide.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   this.BotSide.rotateAngleX = MathHelper.cos(f * 0.26662F) * 3F * f1;
   
  }

}