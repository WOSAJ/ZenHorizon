package com.wosaj.zenhorizon.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ZenHorizon.MODID)
public class ZenDisplayHUD extends AbstractGui {
    private static final ZenDisplayHUD INSTANCE = new ZenDisplayHUD();
    private static final Minecraft minecraft = Minecraft.getInstance();

    public void draw(MatrixStack ms, float pt) {
        if(minecraft.player == null) return;
        Zen.get(minecraft.player).ifPresent(cap -> {
            if(!cap.canHasZen()) return;
            int x = minecraft.getMainWindow().getScaledWidth() - 80;
            int y = minecraft.getMainWindow().getScaledHeight() - 256;
            int i = (int) (cap.getMaxZen() - (64 * (cap.getZen()/((double) cap.getMaxZen() - 0.0))));
            minecraft.getTextureManager().bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/item/potion_mask.png"));
            blit(ms, x, y, 0, 0, 16, 16);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/gui/zen_frame_background.png"));
            blit(ms, x, y, 0, 0, 256, 256);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/gui/zen_hud.png"));
            blit(ms, x, y, i, 0, 256, 256);
            minecraft.getTextureManager().bindTexture(new ResourceLocation(ZenHorizon.MODID, "textures/gui/zen_frame.png"));
            blit(ms, x, y, 0, 0, 256, 256);minecraft.player.sendStatusMessage(new StringTextComponent(String.format("%s | %s | %s", x, y, i)), true);
        });
    }

    @SubscribeEvent
    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        INSTANCE.draw(event.getMatrixStack(), event.getPartialTicks());
    }
}
