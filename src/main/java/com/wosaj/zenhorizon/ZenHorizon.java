package com.wosaj.zenhorizon;

import com.wosaj.zenhorizon.capability.CapabilityEvents;
import com.wosaj.zenhorizon.entity.EntityTypes;
import com.wosaj.zenhorizon.item.Items;
import com.wosaj.zenhorizon.util.ZenHorizonCommand;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;

import javax.annotation.Nonnull;

@Mod(ZenHorizon.MODID)
public class ZenHorizon {
    public static final String MODID = "zenhorizon";
    public static final ItemGroup GROUP = new ItemGroup(new TranslationTextComponent("itemGroup.zenhorizon").getString()) {
        @OnlyIn(Dist.CLIENT)
        @Nonnull
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Items.POTION_MASK.get());
        }
    };

    public ZenHorizon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onInterModEnqueue);
        MinecraftForge.EVENT_BUS.addListener(this::onRegisterCommands);
        MinecraftForge.EVENT_BUS.register(this);
        //CONTENT REGISTRATION
        Items.register(modEventBus);
        EntityTypes.register(modEventBus);
    }

    @SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("back").size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("belt").size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("body").size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("bracelet").size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("head").size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("hands").size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("necklace").size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("ring").size(4).build());
        InterModComms.sendTo("curios", "register_type",
                () -> new SlotTypeMessage.Builder("charm").size(2).cosmetic().build());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ZenHorizonCommand.register(event.getDispatcher());
    }
}