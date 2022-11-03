package com.wosaj.zenhorizon.client.renderer.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wosaj.zenhorizon.common.block.tile.BlackstonePedestalTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlackstonePedestalTileRenderer implements BlockEntityRenderer<BlackstonePedestalTile> {
    @Override
    public void render(BlackstonePedestalTile tileEntityIn, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int i, int i1) {
        if(tileEntityIn.getLevel() == null || tileEntityIn.getStack() == null) return;

        double x = tileEntityIn.getBlockPos().getX();
        double y = tileEntityIn.getBlockPos().getY();
        double z = tileEntityIn.getBlockPos().getZ();

        if (tileEntityIn.entity == null || !ItemStack.matches(tileEntityIn.entity.getItem(), tileEntityIn.getStack())) {
            tileEntityIn.entity = new ItemEntity(tileEntityIn.getLevel(), x, y, z, tileEntityIn.getStack());
        }

        ItemEntity entityItem = tileEntityIn.entity;
        matrixStack.pushPose();
        tileEntityIn.frames += Minecraft.getInstance().getDeltaFrameTime();
        entityItem.setYHeadRot(tileEntityIn.frames);
        entityItem.age = (int) tileEntityIn.frames;
        Minecraft.getInstance().getEntityRenderDispatcher().render(entityItem, 0.5,1.5,0.5, entityItem.getYRot(), 2.0f,matrixStack, iRenderTypeBuffer,i);

        matrixStack.popPose();
    }
}
