package com.wosaj.zenhorizon;

import com.wosaj.zenhorizon.common.util.ZenHorizonCommand;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID)
public final class EventHandler {
    private EventHandler() {}

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ZenHorizonCommand.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventHandler {
        private ModEventHandler(){}

        @SubscribeEvent
        public static void onEntityAttributeCreation(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, ZenHorizonAttributes.ZEN_DISCOUNT.get());
        }
    }
}
