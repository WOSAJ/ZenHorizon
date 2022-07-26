package com.wosaj.zenhorizon.item.curio.back;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.item.Items;
import com.wosaj.zenhorizon.model.curio.MetawingsModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID)
public class Metawings extends Item implements ICurioItem {
    public static final ResourceLocation MODEL_TEXTURE =
            new ResourceLocation(ZenHorizon.MODID, "textures/curio/metawings.png");
    private final MetawingsModel model = new MetawingsModel();
    public Metawings() {
        super(new Properties()
                .maxStackSize(1)
                .rarity(Rarity.EPIC)
                .group(ZenHorizon.GROUP));
    }

    @Override
    public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer,
                       int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, livingEntity);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, livingEntity);
        IVertexBuilder vertexBuilder = ItemRenderer
                .getBuffer(renderTypeBuffer, model.getRenderType(MODEL_TEXTURE), false,
                        stack.hasEffect());
        model.setRotationAngles(livingEntity,limbSwing,limbSwingAmount,ageInTicks, netHeadYaw, headPitch);
        model.render(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                        1.0F);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attrs = LinkedHashMultimap.create();
        attrs.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "generic.movement_speed", 0.2f, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attrs;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if(livingEntity.getEntityWorld().isRemote()) {
            PlayerEntity entity = (PlayerEntity) livingEntity;
            entity.abilities.allowFlying = true;
        }
    }

    //EVENTS

    @SubscribeEvent
    public static void onCurioUnequip(CurioUnequipEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        if(event.getStack().getItem().equals(Items.METAWINGS.get()) && !player.isCreative() && !player.isSpectator()) {
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if(event.getEntity().getType().equals(EntityType.PLAYER)) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (!player.isCreative() && !player.isSpectator() && !event.getEntity().getEntityWorld().getGameRules().get(GameRules.KEEP_INVENTORY).get())
                player.abilities.allowFlying = false;
        }
    }
}
