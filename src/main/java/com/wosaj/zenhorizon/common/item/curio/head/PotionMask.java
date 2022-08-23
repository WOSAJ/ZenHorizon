package com.wosaj.zenhorizon.common.item.curio.head;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.curio.StandardCurio;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Collection;

public class PotionMask extends StandardCurio {
    public PotionMask() {
        super (
                new Item.Properties().rarity(Rarity.RARE).maxStackSize(1).group(ZenHorizon.GROUP).maxDamage(1000),
                new ResourceLocation(ZenHorizon.MODID, "textures/curio/potion_mask.png")
        );
    }

    @SuppressWarnings("all")
    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if(stack.getDamage() == stack.getMaxDamage()) {
            livingEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 5, 1);
            stack.shrink(1);
        }
        if (!livingEntity.getEntityWorld().isRemote()) {
            Collection<EffectInstance> effects = new ArrayList<>(livingEntity.getActivePotionEffects());
            for (EffectInstance effect : effects) {
                if (!effect.getPotion().isBeneficial()) {
                    livingEntity.removePotionEffect(effect.getPotion());
                }
            }
        }
    }
}
