package com.wosaj.zenhorizon.common.item.curio;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.stream.Collectors;

public class PotionMask extends Item implements ICurioItem {
    public PotionMask() {
        super(new Properties().stacksTo(1).durability(200).rarity(Rarity.UNCOMMON).tab(ZenHorizon.GROUP));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        var player = slotContext.entity();
        if(!(player instanceof Player)) return;
        var effects = player.getActiveEffects();
        effects.stream()
            .filter(e -> e.getEffect().getCategory() == MobEffectCategory.HARMFUL)
            .collect(Collectors.toSet())
            .forEach(o -> {
                player.removeEffect(o.getEffect());
                stack.setDamageValue(stack.getDamageValue() + 1);
                if(stack.getDamageValue() >= stack.getMaxDamage()) {
                    stack.shrink(1);
                    player.getLevel().playSound(null, player.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.AMBIENT, 1F, 1F);
                }
            });
    }
}
