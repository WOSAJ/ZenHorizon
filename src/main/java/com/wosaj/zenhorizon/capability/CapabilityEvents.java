package com.wosaj.zenhorizon.capability;

import com.wosaj.zenhorizon.capability.attacher.CapabilityZen;
import com.wosaj.zenhorizon.capability.provider.CapabilityProviderPlayers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.wosaj.zenhorizon.ZenHorizon.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public final class CapabilityEvents {
    public static final ResourceLocation ZEN_ID = new ResourceLocation(MODID, "zen");

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        CapabilityZen.register();
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) event.addCapability(ZEN_ID, new CapabilityProviderPlayers());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(CapabilityZen.INSTANCE).ifPresent(oldZen ->
                event.getPlayer().getCapability(CapabilityZen.INSTANCE).ifPresent(newZen ->
                        newZen.maxZen = oldZen.maxZen
                )
        );
    }
}
