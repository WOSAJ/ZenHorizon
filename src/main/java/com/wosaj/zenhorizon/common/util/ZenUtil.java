package com.wosaj.zenhorizon.common.util;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.resources.ResourceLocation;

public final class ZenUtil {
    public static ResourceLocation rl(String val) {
        return new ResourceLocation(ZenHorizon.MODID, val);
    }

    private ZenUtil(){}
}
