package com.wosaj.zenhorizon.datagen;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.ZenHorizonBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZenHorizonBlockStateProvider extends BlockStateProvider {
    protected ExistingFileHelper existingFileHelper;
    public ZenHorizonBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ZenHorizon.MODID, exFileHelper);
        existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ZenHorizonBlocks.BLACKSTONE_PEDESTAL.get(), models().getExistingFile(modLoc("blackstone_pedestal")));
        simpleBlock(ZenHorizonBlocks.ALTAR_PRISM.get(), models().getExistingFile(modLoc("altar_prism")));
    }
}
