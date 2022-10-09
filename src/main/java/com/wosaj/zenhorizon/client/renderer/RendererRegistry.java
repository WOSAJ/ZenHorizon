package com.wosaj.zenhorizon.client.renderer;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.client.model.MaskModel;
import com.wosaj.zenhorizon.client.model.MetaWingsModel;
import com.wosaj.zenhorizon.client.renderer.curio.MaskRenderer;
import com.wosaj.zenhorizon.client.renderer.curio.MetaWingsRenderer;
import com.wosaj.zenhorizon.common.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static com.wosaj.zenhorizon.ZenHorizon.rl;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RendererRegistry {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(Items.POTION_MASK.get(), () -> new MaskRenderer<>(rl("/textures/curio/potion_mask.png")));
        CuriosRendererRegistry.register(Items.METAWINGS.get(), MetaWingsRenderer::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MaskModel.LAYER, MaskModel::createBodyLayer);
        event.registerLayerDefinition(MetaWingsModel.LAYER, MetaWingsModel::createBodyLayer);
    }
}
