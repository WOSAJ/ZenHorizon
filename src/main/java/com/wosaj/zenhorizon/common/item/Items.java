package com.wosaj.zenhorizon.common.item;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.curio.MetaWings;
import com.wosaj.zenhorizon.common.item.curio.PotionMask;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Items {
    //COMMON
    //UNCOMMON
    public static final Rarity ENHANCED = Rarity.create("enhanced", ChatFormatting.BLUE);
    //RARE
    public static final Rarity RELIC = Rarity.create("relic", s -> s.applyFormats(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
    public static final Rarity MYTHIC = Rarity.create("mythic", ChatFormatting.RED);
    //EPIC
    public static final Rarity LEGENDARY = Rarity.create("legendary", ChatFormatting.GOLD);
    public static final Rarity SUPREME = Rarity.create("supreme", ChatFormatting.GREEN);
    public static final Rarity COSMIC = Rarity.create("cosmic", ChatFormatting.DARK_PURPLE);

    private static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}
    private Items(){}

    public static final RegistryObject<Item> POTION_MASK = REG.register("potion_mask", PotionMask::new);
    public static final RegistryObject<Item> METAWINGS = REG.register("metawings", MetaWings::new);
}
