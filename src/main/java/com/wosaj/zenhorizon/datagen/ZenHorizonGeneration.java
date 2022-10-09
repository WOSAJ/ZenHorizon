package com.wosaj.zenhorizon.datagen;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZenHorizonGeneration {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if(event.includeClient()) {
            var generator = event.getGenerator();
            var helper = event.getExistingFileHelper();
            generator.addProvider(new ZenHorizonItemModelProvider(generator, helper));
            generator.addProvider(new ZenHorizonBlockStateProvider(generator, helper));
        }
    }
}
