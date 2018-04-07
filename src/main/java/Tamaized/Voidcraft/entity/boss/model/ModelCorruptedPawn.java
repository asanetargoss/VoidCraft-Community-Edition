// Date: 8/6/2014 12:40:17 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package Tamaized.Voidcraft.entity.boss.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCorruptedPawn extends ModelBase {

	ModelRenderer head;
	ModelRenderer rightarm;
	ModelRenderer leftarm;
	ModelRenderer rightleg;
	ModelRenderer leftleg;
	ModelRenderer TopSide;
	ModelRenderer RightSide;
	ModelRenderer BotSide;
	ModelRenderer LeftSide;

	public ModelCorruptedPawn() {
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

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
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

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.head.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);

		float maxSwing = 0.95F;

		this.rightarm.rotateAngleX = limbSwingAmount * maxSwing;
		this.leftarm.rotateAngleX = limbSwingAmount * maxSwing;
		this.rightleg.rotateAngleX = limbSwingAmount * maxSwing;
		this.leftleg.rotateAngleX = limbSwingAmount * maxSwing;
		this.TopSide.rotateAngleX = limbSwingAmount * maxSwing;
		this.LeftSide.rotateAngleX = limbSwingAmount * maxSwing;
		this.RightSide.rotateAngleX = limbSwingAmount * maxSwing;
		this.BotSide.rotateAngleX = limbSwingAmount * maxSwing;

	}

}