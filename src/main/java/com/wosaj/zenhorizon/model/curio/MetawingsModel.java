package com.wosaj.zenhorizon.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

@SuppressWarnings("all")
@javax.annotation.ParametersAreNonnullByDefault
public class MetawingsModel extends EntityModel<Entity> {
	public static final float targetAngle = 0.6f, positionSpeed = 0.2f;

	private final ModelRenderer WingL;
	private final ModelRenderer Stick3_r1;
	private final ModelRenderer Stick2_r1;
	private final ModelRenderer Stick1_r1;
	private final ModelRenderer WingL2;
	private final ModelRenderer Stick3_r2;
	private final ModelRenderer Stick2_r2;
	private final ModelRenderer Stick1_r2;
	private final ModelRenderer bb_main;
	private final ModelRenderer BoneR_r1;
	private final ModelRenderer bone;
	private final ModelRenderer BoneL_r1;
	private float lastAge = 0f;

	public MetawingsModel() {
		textureWidth = 64;
		textureHeight = 64;

		WingL = new ModelRenderer(this);
		WingL.setRotationPoint(6.0F, 0.0F, 5.0F);
		WingL.setTextureOffset(0, 12).addBox(0.0F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		Stick3_r1 = new ModelRenderer(this);
		Stick3_r1.setRotationPoint(3.0F, 3.0F, 0.0F);
		WingL.addChild(Stick3_r1);
		setRotationAngle(Stick3_r1, 0.0F, 0.0F, -0.0436F);
		Stick3_r1.setTextureOffset(0, 0).addBox(1.7855F, -5.5088F, -1.0F, 15.0F, 2.0F, 2.0F, 0.0F, false);

		Stick2_r1 = new ModelRenderer(this);
		Stick2_r1.setRotationPoint(3.0F, 3.0F, 0.0F);
		WingL.addChild(Stick2_r1);
		setRotationAngle(Stick2_r1, 0.0F, 0.0F, 0.3054F);
		Stick2_r1.setTextureOffset(0, 4).addBox(0.7855F, -3.5088F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, false);

		Stick1_r1 = new ModelRenderer(this);
		Stick1_r1.setRotationPoint(3.0F, 3.0F, 0.0F);
		WingL.addChild(Stick1_r1);
		setRotationAngle(Stick1_r1, 0.0F, 0.0F, 0.6109F);
		Stick1_r1.setTextureOffset(0, 8).addBox(0.7855F, -0.5088F, -1.0F, 9.0F, 2.0F, 2.0F, 0.0F, false);

		WingL2 = new ModelRenderer(this);
		WingL2.setRotationPoint(-6.0F, 0.0F, 5.0F);
		WingL2.setTextureOffset(0, 12).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, true);

		Stick3_r2 = new ModelRenderer(this);
		Stick3_r2.setRotationPoint(-3.0F, 3.0F, 0.0F);
		WingL2.addChild(Stick3_r2);
		setRotationAngle(Stick3_r2, 0.0F, 0.0F, 0.0436F);
		Stick3_r2.setTextureOffset(0, 0).addBox(-16.7855F, -5.5088F, -1.0F, 15.0F, 2.0F, 2.0F, 0.0F, true);

		Stick2_r2 = new ModelRenderer(this);
		Stick2_r2.setRotationPoint(-3.0F, 3.0F, 0.0F);
		WingL2.addChild(Stick2_r2);
		setRotationAngle(Stick2_r2, 0.0F, 0.0F, -0.3054F);
		Stick2_r2.setTextureOffset(0, 4).addBox(-12.7855F, -3.5088F, -1.0F, 12.0F, 2.0F, 2.0F, 0.0F, true);

		Stick1_r2 = new ModelRenderer(this);
		Stick1_r2.setRotationPoint(-3.0F, 3.0F, 0.0F);
		WingL2.addChild(Stick1_r2);
		setRotationAngle(Stick1_r2, 0.0F, 0.0F, -0.6109F);
		Stick1_r2.setTextureOffset(0, 8).addBox(-9.7855F, -0.5088F, -1.0F, 9.0F, 2.0F, 2.0F, 0.0F, true);

		bb_main = new ModelRenderer(this);
		bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
		bb_main.setTextureOffset(8, 18).addBox(-1.0F, -19.0F, 3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

		BoneR_r1 = new ModelRenderer(this);
		BoneR_r1.setRotationPoint(-2.0F, -20.0F, 4.0F);
		bb_main.addChild(BoneR_r1);
		setRotationAngle(BoneR_r1, -0.1745F, 0.0F, -0.7854F);
		BoneR_r1.setTextureOffset(12, 12).addBox(-1.7071F, -3.7071F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		BoneR_r1.addChild(bone);
		

		BoneL_r1 = new ModelRenderer(this);
		BoneL_r1.setRotationPoint(2.0F, -20.0F, 4.0F);
		bb_main.addChild(BoneL_r1);
		setRotationAngle(BoneL_r1, -0.2182F, 0.0F, 0.7854F);
		BoneL_r1.setTextureOffset(12, 12).addBox(-0.2929F, -3.7071F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float roundTick = (float) (Math.floor(ageInTicks*100f)/100f);
		if(lastAge != roundTick) {
			lastAge = roundTick;
			if (((PlayerEntity) entity).abilities.isFlying) {
				if (WingL.rotateAngleZ > -targetAngle && WingL2.rotateAngleZ < targetAngle) {
					WingL.rotateAngleZ-=positionSpeed;
					WingL2.rotateAngleZ+=positionSpeed;
				}
			} else {
				if (WingL.rotateAngleZ < targetAngle && WingL2.rotateAngleZ > -targetAngle) {
					WingL.rotateAngleZ+=positionSpeed;
					WingL2.rotateAngleZ-=positionSpeed;
				}
			}
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		WingL.render(matrixStack, buffer, packedLight, packedOverlay);
		WingL2.render(matrixStack, buffer, packedLight, packedOverlay);
		bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}