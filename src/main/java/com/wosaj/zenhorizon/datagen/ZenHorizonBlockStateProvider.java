package com.wosaj.zenhorizon.datagen;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZenHorizonBlockStateProvider extends BlockStateProvider {
    public ZenHorizonBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ZenHorizon.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
