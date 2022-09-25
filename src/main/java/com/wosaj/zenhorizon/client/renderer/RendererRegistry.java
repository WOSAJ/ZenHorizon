package com.wosaj.zenhorizon.client.renderer;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.client.model.StandardMaskModel;
import com.wosaj.zenhorizon.client.renderer.curio.PotionMaskRenderer;
import com.wosaj.zenhorizon.common.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ZenHorizon.MODID)
public final class RendererRegistry {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(Items.POTION_MASK.get(), PotionMaskRenderer::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(StandardMaskModel.LAYER, StandardMaskModel::createLayer);
    }
}
