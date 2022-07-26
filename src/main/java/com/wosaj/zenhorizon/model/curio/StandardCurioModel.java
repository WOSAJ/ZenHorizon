package com.wosaj.zenhorizon.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

@javax.annotation.ParametersAreNonnullByDefault
public class StandardCurioModel extends EntityModel<Entity> {
	public final ModelRenderer head;
	public final ModelRenderer body;
//	public final ModelRenderer left_arm;
//	public final ModelRenderer right_arm;
//	public final ModelRenderer left_leg;
//	public final ModelRenderer right_leg;

	public StandardCurioModel() {
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.1F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		body.setTextureOffset(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.1F, false);

//		left_arm = new ModelRenderer(this);
//		left_arm.setRotationPoint(5.0F, 2.0F, 0.0F);
//		left_arm.setTextureOffset(16, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
//
//		right_arm = new ModelRenderer(this);
//		right_arm.setRotationPoint(-5.0F, 2.0F, 0.0F);
//		right_arm.setTextureOffset(24, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
//
//		left_leg = new ModelRenderer(this);
//		left_leg.setRotationPoint(2.0F, 12.0F, 0.0F);
//		left_leg.setTextureOffset(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
//
//		right_leg = new ModelRenderer(this);
//		right_leg.setRotationPoint(-2.0F, 12.0F, 0.0F);
//		right_leg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.1F, false);
	}

	@Override
	public void setRotationAngles(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
//		left_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
//		right_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
//		left_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
//		right_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}