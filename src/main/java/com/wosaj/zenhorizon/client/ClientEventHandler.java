package com.wosaj.zenhorizon.client;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.client.model.curio.MaskModel;
import com.wosaj.zenhorizon.client.model.curio.MetaWingsModel;
import com.wosaj.zenhorizon.client.renderer.curio.MaskRenderer;
import com.wosaj.zenhorizon.client.renderer.curio.MetaWingsRenderer;
import com.wosaj.zenhorizon.client.renderer.tile.AltarPrismTileRenderer;
import com.wosaj.zenhorizon.client.renderer.tile.BlackstonePedestalTileRenderer;
import com.wosaj.zenhorizon.common.block.ZenHorizonBlocks;
import com.wosaj.zenhorizon.common.item.ZenHorizonItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import static com.wosaj.zenhorizon.ZenHorizon.rl;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        CuriosRendererRegistry.register(ZenHorizonItems.POTION_MASK.get(), () -> new MaskRenderer<>(rl("/textures/curio/potion_mask.png")));
        CuriosRendererRegistry.register(ZenHorizonItems.METAWINGS.get(), MetaWingsRenderer::new);

        ItemBlockRenderTypes.setRenderLayer(ZenHorizonBlocks.BLACKSTONE_PEDESTAL.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MaskModel.LAYER, MaskModel::createBodyLayer);
        event.registerLayerDefinition(MetaWingsModel.LAYER, MetaWingsModel::createBodyLayer);

        event.registerLayerDefinition(AltarPrismTileRenderer.LAYER, AltarPrismTileRenderer::createBodyLayer);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ZenHorizonBlocks.BLACKSTONE_PEDESTAL_TILE.get(),a->new BlackstonePedestalTileRenderer());
        event.registerBlockEntityRenderer(ZenHorizonBlocks.ALTAR_PRISM_TILE.get(), AltarPrismTileRenderer::new);
    }
}
