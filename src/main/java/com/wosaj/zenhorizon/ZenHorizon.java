package com.wosaj.zenhorizon;

import com.wosaj.zenhorizon.common.item.Items;
import com.wosaj.zenhorizon.common.networking.Networking;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nonnull;

@Mod(ZenHorizon.MODID)
public final class ZenHorizon {
    public static final String MODID = "zenhorizon";

    public static final CreativeModeTab GROUP = new CreativeModeTab(new TranslatableComponent("zenhorizon").getString()) {
        @OnlyIn(Dist.CLIENT)
        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.POTION_MASK.get());
        }
    };

    public ZenHorizon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Networking.register();
        modEventBus.addListener(this::onInterModEnqueue);
        MinecraftForge.EVENT_BUS.register(this);
        //CONTENT REGISTRATION
        Items.register(modEventBus);
        //EntityTypes.register(modEventBus);
    }

    @SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BACK.getMessageBuilder().size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BELT.getMessageBuilder().size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BODY.getMessageBuilder().size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.BRACELET.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.HEAD.getMessageBuilder().size(1).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.HANDS.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(2).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.RING.getMessageBuilder().size(4).cosmetic().build());
        InterModComms.sendTo("curios", "register_type",
                () -> SlotTypePreset.CHARM.getMessageBuilder().size(2).cosmetic().build());
    }

    //UTIL
    public static ResourceLocation rl(String val) {
        return new ResourceLocation(MODID, val);
    }
}
