package com.wosaj.zenhorizon.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.tile.AltarPrismTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AltarPrismTileRenderer implements BlockEntityRenderer<AltarPrismTile> {
    public static final ResourceLocation TEXTURE = ZenHorizon.rl("textures/tile/altar_prism.png");
    public static final RenderType TYPE = RenderType.entityTranslucent(TEXTURE);

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(new ResourceLocation("modid", "altarprism"), "main");

    private final ModelPart prism;
    private final ModelPart runes;

    public AltarPrismTileRenderer(BlockEntityRendererProvider.Context ctx) {
        var modelPart = ctx.bakeLayer(LAYER);
        prism = modelPart.getChild("prism");
        runes = modelPart.getChild("runes");
    }

    @Override
    public void render(AltarPrismTile be, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        be.frames += Math.toRadians(Minecraft.getInstance().getDeltaFrameTime()) * 1.2D;
        var vertexConsumer = pBufferSource.getBuffer(TYPE);

        pPoseStack.pushPose();
            pPoseStack.translate(0.5D, -0.7D ,0.5D);
            pPoseStack.pushPose();
                pPoseStack.translate(0, Math.sin(be.frames*1.5) * 0.1D, 0);
                prism.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1);
            pPoseStack.popPose();
            pPoseStack.pushPose();
                runes.yRot = be.frames;
                pPoseStack.translate(0, Math.cos(be.frames) * 0.1D, 0);
                runes.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay, 1, 1, 1, 1);
            pPoseStack.popPose();
        pPoseStack.popPose();
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
}
