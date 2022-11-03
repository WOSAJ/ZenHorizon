package com.wosaj.zenhorizon.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.tile.AltarPrismTile;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AltarPrismTileRenderer implements BlockEntityRenderer<AltarPrismTile> {
    public static final ResourceLocation TEXTURE = ZenHorizon.rl("textures/tile/altar_prism.png");
    public static final ResourceLocation LOCATION = ZenHorizon.rl("tile/altar_prism");
    //public static final RenderType SHEET = RenderType.entityTranslucent(TEXTURE);
    public static final Material MATERIAL = new Material(TEXTURE, LOCATION);

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation("modid", "altarprism"), "main");

    private final ModelPart prism;
    private final ModelPart runes;

    public AltarPrismTileRenderer(BlockEntityRendererProvider.Context ctx) {
        var modelPart = ctx.bakeLayer(LAYER);
        prism = modelPart.getChild("prism");
        runes = modelPart.getChild("runes");
    }

    @Override
    public void render(AltarPrismTile pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        //TODO Rotation
        var vertexConsumer = MATERIAL.buffer(pBufferSource, RenderType::entityCutout);
        renderToBuffer(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("prism", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition runes = partdefinition.addOrReplaceChild("runes", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, -8.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        runes.addOrReplaceChild("rune4_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        runes.addOrReplaceChild("rune3_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        runes.addOrReplaceChild("rune2_r1", CubeListBuilder.create().texOffs(22, 0).addBox(-5.0F, -6.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        prism.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        runes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
