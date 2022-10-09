package com.wosaj.zenhorizon.common.item.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.Items;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID)
public class MetaWings extends Item implements ICurioItem {
    public MetaWings() {
        super(new Properties().rarity(Items.SUPREME).tab(ZenHorizon.GROUP).stacksTo(1));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(!(slotContext.entity() instanceof Player player)) return;
        if(!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = true;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        var attrs = LinkedHashMultimap.<Attribute, AttributeModifier>create();
        attrs.put(Attributes.FLYING_SPEED, new AttributeModifier(uuid, "generic.flying_speed", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
        attrs.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "generic.movement_speed", 0.3, AttributeModifier.Operation.MULTIPLY_BASE));
        return attrs;
    }

    @SubscribeEvent
    public static void onUnequip(CurioUnequipEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        if(event.getStack().getItem() == Items.METAWINGS.get() && !player.isCreative() && !player.isSpectator()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.stopFallFlying();
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if(!(event.getEntity() instanceof Player player)) return;
        if(!player.isCreative() && !player.isSpectator() && !player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.stopFallFlying();
        }
    }
}
