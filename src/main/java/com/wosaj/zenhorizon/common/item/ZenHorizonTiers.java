package com.wosaj.zenhorizon.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public final class ZenHorizonTiers {
    public static final Tier MITHRIL = new ForgeTier(3, 2031, 8, 3, 22, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ZenHorizonItems.MITHRIL_INGOT.get()));
}
