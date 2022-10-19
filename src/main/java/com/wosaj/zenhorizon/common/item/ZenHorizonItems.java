package com.wosaj.zenhorizon.common.item;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.ZenHorizonBlocks;
import com.wosaj.zenhorizon.common.item.curio.MetaWings;
import com.wosaj.zenhorizon.common.item.curio.PotionMask;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.item.Rarity.*;

@SuppressWarnings("unused")
public final class ZenHorizonItems {
    //COMMON
    //UNCOMMON
    public static final Rarity ENHANCED = create("enhanced", ChatFormatting.BLUE);
    //RARE
    public static final Rarity RELIC = create("relic", s -> s.applyFormats(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
    public static final Rarity MYTHIC = create("mythic", ChatFormatting.RED);
    //EPIC
    public static final Rarity LEGENDARY = create("legendary", ChatFormatting.GOLD);
    public static final Rarity SUPREME = create("supreme", ChatFormatting.GREEN);
    public static final Rarity COSMIC = create("cosmic", ChatFormatting.DARK_PURPLE);

    private static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}
    private ZenHorizonItems(){}
    private static Item.Properties prop() {return new Item.Properties().tab(ZenHorizon.GROUP);}

    public static final RegistryObject<Item> MITHRIL_INGOT = REG.register("mithril_ingot", () -> new Item(prop().rarity(ENHANCED)));
    public static final RegistryObject<Item> MITHRIL_SWORD = REG.register("mithril_sword", () -> new SwordItem(ZenHorizonTiers.MITHRIL, 3, -2.2F, prop().rarity(ENHANCED)));

    public static final RegistryObject<Item> BLACKSTONE_PEDESTAL = REG.register("blackstone_pedestal", () -> new BlockItem(ZenHorizonBlocks.BLACKSTONE_PEDESTAL.get(), prop()));

    public static final RegistryObject<Item> POTION_MASK = REG.register("potion_mask", PotionMask::new);
    public static final RegistryObject<Item> METAWINGS = REG.register("metawings", MetaWings::new);

}
