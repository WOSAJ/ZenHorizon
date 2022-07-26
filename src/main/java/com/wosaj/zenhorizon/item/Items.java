package com.wosaj.zenhorizon.item;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.item.curio.head.PotionMask;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.wosaj.zenhorizon.item.curio.back.Metawings;

@SuppressWarnings("unused")
public final class Items {
    private static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}

    public static final RegistryObject<Item> METAWINGS = REG.register("metawings", Metawings::new);
    public static final RegistryObject<Item> POTION_MASK = REG.register("potion_mask", PotionMask::new);
}
