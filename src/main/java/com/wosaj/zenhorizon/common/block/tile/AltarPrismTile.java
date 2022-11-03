package com.wosaj.zenhorizon.common.block.tile;

import com.wosaj.zenhorizon.common.block.ZenHorizonBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AltarPrismTile extends ZenHorizonTile {
    public AltarPrismTile(BlockPos pPos, BlockState pBlockState) {
        super(ZenHorizonBlocks.ALTAR_PRISM_TILE.get(), pPos, pBlockState);
    }
}
