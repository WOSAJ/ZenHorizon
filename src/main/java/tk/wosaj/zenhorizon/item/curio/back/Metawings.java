package tk.wosaj.zenhorizon.item.curio.back;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import tk.wosaj.zenhorizon.ZenHorizon;
import tk.wosaj.zenhorizon.model.curio.MetawingsModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

public class Metawings extends Item implements ICurioItem {
    public static final ResourceLocation MODEL_TEXTURE =
            new ResourceLocation(ZenHorizon.MODID, "textures/entity/metawings.png");
    private final MetawingsModel model = new MetawingsModel();
    public Metawings() {
        super(new Properties()
                .maxStackSize(1)
                .rarity(Rarity.EPIC)
                .group(ItemGroup.TRANSPORTATION));
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer,
                       int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
        IVertexBuilder vertexBuilder = ItemRenderer
                .getBuffer(renderTypeBuffer, model.getRenderType(MODEL_TEXTURE), false,
                        stack.hasEffect());
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                        1.0F);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attrs = LinkedHashMultimap.create();
        attrs.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "speed_bonus", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attrs;
    }
}
