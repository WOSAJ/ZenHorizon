package com.wosaj.zenhorizon.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@net.minecraft.MethodsReturnNonnullByDefault
@javax.annotation.ParametersAreNonnullByDefault
public class MetaWingsModel<T extends Entity> extends AgeableListModel<T> {
	public static final double ANIM_ROT_ANGLE = Math.toRadians(45);
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation(ZenHorizon.MODID, "metawings"), "metawings");
	private final ModelPart leftWing;
	private final ModelPart rightWing;

	public MetaWingsModel(ModelPart root) {
		this.leftWing = root.getChild("leftWing");
		this.rightWing = root.getChild("rightWing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition leftWing = partdefinition.addOrReplaceChild("leftWing", CubeListBuilder.create(), PartPose.offset(1.0F, 14.0F, 2.0F));

		leftWing.addOrReplaceChild("wingCanvasL_r1", CubeListBuilder.create().texOffs(0, 2).addBox(-15.0F, 1.0F, 0.25F, 26.0F, 17.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(44, 0).addBox(0.0F, 0.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.9729F, -19.1796F, 1.1967F, 0.0F, -0.0832F, 0.0F));

		leftWing.addOrReplaceChild("middleBoneL_r1", CubeListBuilder.create().texOffs(18, 0).addBox(0.0F, 0.0F, 0.0001F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.564F, -15.5961F, 0.1991F, -0.0263F, -0.0832F, -0.3043F));

		leftWing.addOrReplaceChild("mainBoneL_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, -0.0873F, -0.6109F));

		PartDefinition rightWing = partdefinition.addOrReplaceChild("rightWing", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		rightWing.addOrReplaceChild("wingCanvasR_r1", CubeListBuilder.create().texOffs(0, 2).mirror().addBox(-11.0F, 1.0F, 0.25F, 26.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(44, 0).mirror().addBox(-6.0F, 0.0F, 0.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-17.9729F, -19.1796F, 1.1967F, 0.0F, 0.0832F, 0.0F));

		rightWing.addOrReplaceChild("middleBoneR_r1", CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-12.0F, 0.0F, 0.0001F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.564F, -15.5961F, 0.1991F, -0.0263F, 0.0832F, 0.3043F));

		rightWing.addOrReplaceChild("mainBoneR_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 0.0F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -11.0F, 0.0F, 0.0F, 0.0873F, 0.6109F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if(entity instanceof Player player) if(player.getAbilities().flying) {
			var mul = (float) (((Math.sin(ageInTicks/2)+1)/2)*ANIM_ROT_ANGLE);
			leftWing.yRot = -mul;
			rightWing.yRot = mul;
		} else {
			leftWing.yRot = rightWing.yRot = 0;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		leftWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightWing.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	protected Iterable<ModelPart> headParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(leftWing, rightWing);
	}
}