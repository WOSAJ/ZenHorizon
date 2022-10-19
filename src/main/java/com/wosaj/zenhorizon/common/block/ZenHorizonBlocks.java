package com.wosaj.zenhorizon.common.block;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.tile.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"unused", "ConstantConditions"})
public final class ZenHorizonBlocks {
    private ZenHorizonBlocks(){}
    private static final DeferredRegister<Block> REG = DeferredRegister.create(ForgeRegistries.BLOCKS, ZenHorizon.MODID);
    private static final DeferredRegister<BlockEntityType<?>> TREG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ZenHorizon.MODID);
    public static void register(IEventBus bus){REG.register(bus);TREG.register(bus);}

    public static final RegistryObject<Block> BLACKSTONE_PEDESTAL = REG.register("blackstone_pedestal", BlackstonePedestal::new);
    public static final RegistryObject<BlockEntityType<BlackstonePedestalTile>> BLACKSTONE_PEDESTAL_TILE = TREG.register("blackstone_pedestal_tile", () -> BlockEntityType.Builder.of(BlackstonePedestalTile::new).build(null));

}
