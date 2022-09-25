package com.wosaj.zenhorizon.client.renderer.curio;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class PotionMaskRenderer<T extends LivingEntity> extends MaskRenderer<T> {
    public PotionMaskRenderer() {
        super(new ResourceLocation(ZenHorizon.MODID, "/textures/curio/potion_mask.png"));
    }
}
