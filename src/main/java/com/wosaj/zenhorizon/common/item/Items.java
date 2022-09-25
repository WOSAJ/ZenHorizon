package com.wosaj.zenhorizon.common.item;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.curio.PotionMask;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public final class Items {
    private static final DeferredRegister<Item> REG = DeferredRegister.create(ForgeRegistries.ITEMS, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}

    public static final RegistryObject<Item> POTION_MASK = REG.register("potion_mask", PotionMask::new);
}
