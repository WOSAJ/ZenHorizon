package com.wosaj.zenhorizon.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wosaj.zenhorizon.client.model.curio.MaskModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MaskRenderer<L extends LivingEntity> implements ICurioRenderer {
    protected final MaskModel<L> model = new MaskModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(MaskModel.LAYER));
    public final ResourceLocation texture;
    public MaskRenderer(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int i, float v, float v1, float v2, float v3, float v4, float v5) {
        ICurioRenderer.followHeadRotations(slotContext.entity(), model.mask);
        VertexConsumer vertexconsumer = ItemRenderer
                .getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(texture), false,
                        itemStack.hasFoil());
        model.renderToBuffer(poseStack, vertexconsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
