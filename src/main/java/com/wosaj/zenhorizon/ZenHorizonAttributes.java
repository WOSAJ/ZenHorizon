package com.wosaj.zenhorizon;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public final class ZenHorizonAttributes {
    private ZenHorizonAttributes(){}
    private static final DeferredRegister<Attribute> REG = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}

    public static final RegistryObject<Attribute> ZEN_DISCOUNT = REG.register("player.zen_discount", () -> new RangedAttribute("attribute.name.player.zen_discount", 0, 0, 1024).setSyncable(true));

}
