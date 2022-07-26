package com.wosaj.zenhorizon.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.capability.attacher.CapabilityZen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ZenHorizon.MODID)
public class ZenDisplayHUD extends AbstractGui {
    private static final ZenDisplayHUD INSTANCE = new ZenDisplayHUD();
    private final Minecraft minecraft = Minecraft.getInstance();

    public void draw(MatrixStack ms, float pt) {
        if(minecraft.player == null) return;
        minecraft.player.getCapability(CapabilityZen.INSTANCE).ifPresent(cap -> {
            int i = cap.zen / cap.maxZen * 64;
            minecraft.textureManager.bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/gui/zen_hud.png"));
            blit(ms, 6, 6, 0, 0, 8, 64);
            minecraft.textureManager.bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/gui/zen_hud_active.png"));
            blit(ms, 6, 6, 0, 0, 8, i);
        });
    }

    @SubscribeEvent
    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        INSTANCE.draw(event.getMatrixStack(), event.getPartialTicks());
    }
}
