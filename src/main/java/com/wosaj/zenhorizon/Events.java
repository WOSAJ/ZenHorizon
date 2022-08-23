package com.wosaj.zenhorizon;

import com.wosaj.zenhorizon.common.capability.CapabilityEvents;
import com.wosaj.zenhorizon.common.util.ZenHorizonCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.wosaj.zenhorizon.ZenHorizon.*;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID)
public final class Events {
    private Events() {}

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        CapabilityEvents.setup();
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ZenHorizonCommand.register(event.getDispatcher());
    }
}
