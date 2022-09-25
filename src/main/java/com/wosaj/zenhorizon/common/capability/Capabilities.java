package com.wosaj.zenhorizon.common.capability;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID)
public class Capabilities {
    public static final Capability<Zen> ZEN_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Zen.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ZenHorizon.MODID, "zen"), CapUtil.makeSavedProvider(ZEN_CAPABILITY, new Zen()));
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        var old = event.getOriginal();
        old.revive();
        System.out.printf("%s %s\n", Zen.get(old).isPresent(), Zen.get(event.getPlayer()).isPresent());
        Zen.get(old).ifPresent(oldCap -> Zen.get(event.getPlayer()).ifPresent(newCap -> {
            newCap.setZen(oldCap.getZen());
            newCap.setMaxZen(oldCap.getMaxZen());
        }));
        old.invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(event.getPlayer().getLevel().isClientSide) return;
        Zen.get(event.getPlayer()).ifPresent(cap -> cap.sync((ServerPlayer) event.getPlayer()));
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(event.getPlayer().getLevel().isClientSide) return;
        Zen.get(event.getPlayer()).ifPresent(cap -> cap.sync((ServerPlayer) event.getPlayer()));
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer().getLevel().isClientSide) return;
        Zen.get(event.getPlayer()).ifPresent(cap -> cap.sync((ServerPlayer) event.getPlayer()));
    }

    private Capabilities(){}
}
