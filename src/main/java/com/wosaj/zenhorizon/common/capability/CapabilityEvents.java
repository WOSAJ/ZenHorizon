package com.wosaj.zenhorizon.common.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.wosaj.zenhorizon.ZenHorizon.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public final class CapabilityEvents {
    public static final ResourceLocation ZEN_ID = new ResourceLocation(MODID, "zen");

    public static void setup() {
        CapabilityZen.register();
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) event.addCapability(ZEN_ID, new CapabilityZenProvider());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(event.getPlayer().getEntityWorld().isRemote) return;
        event.getOriginal().getCapability(CapabilityZen.INSTANCE).ifPresent(oldZen ->
            event.getPlayer().getCapability(CapabilityZen.INSTANCE).ifPresent(newZen -> {
                newZen.setMaxZen(oldZen.getMaxZen());
                newZen.setZen(0);
            })
        );
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        sync(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        sync(event.getPlayer());
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        sync(event.getPlayer());
    }

    private static void sync(PlayerEntity player) {
        if(!Zen.get(player).isPresent()) player.sendStatusMessage(new StringTextComponent("NOT PRESENT BLYAT"), false);
        if(player instanceof ServerPlayerEntity) {
            Zen.get(player).ifPresent(cap -> {
                cap.setMaxZen(cap.getMaxZen());
                cap.setZen(cap.getZen());
                cap.sync((ServerPlayerEntity) player);
            });
        }
    }
}
