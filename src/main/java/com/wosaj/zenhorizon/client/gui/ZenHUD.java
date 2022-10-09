package com.wosaj.zenhorizon.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.wosaj.zenhorizon.ZenHorizon.*;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
@javax.annotation.ParametersAreNonnullByDefault
public class ZenHUD extends GuiComponent {
    public static ZenHUD INSTANCE = new ZenHUD();

    public void draw(PoseStack poseStack, float pt) {
        if(Minecraft.getInstance().player == null) return;
        var lazyOptional = Zen.get(Minecraft.getInstance().player);
        lazyOptional.ifPresent(cap -> {
            if(!cap.canHasZen()) return;
            RenderSystem.setShaderTexture(0, rl("/textures/gui/zen_hud.png"));
            blit(poseStack, 0, 20, 0, 0, 16, 64, 128, 128);
            var i = (int) ((cap.getZen()+0F)/cap.getMaxZen()*30+1);
            var j = 30-i;
            RenderSystem.setShaderTexture(0, rl("/textures/gui/zen_hud.png"));
            blit(poseStack, 0, 20+j, 16, 0, 16, i, 128, 128);
        });
    }

    @SubscribeEvent
    public static void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event) {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        INSTANCE.draw(event.getMatrixStack(), event.getPartialTicks());
    }
    private ZenHUD(){}
}
