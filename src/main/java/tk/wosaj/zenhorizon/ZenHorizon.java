package tk.wosaj.zenhorizon;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.event.CurioEquipEvent;
import top.theillusivec4.curios.api.event.CurioUnequipEvent;

@Mod(ZenHorizon.MODID)
public class ZenHorizon {
    public static final String MODID = "zenhorizon";

    public ZenHorizon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onInterModEnqueue);
        MinecraftForge.EVENT_BUS.addListener(this::onCurioEquip);
        MinecraftForge.EVENT_BUS.addListener(this::onCurioUnequip);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDeath);
        MinecraftForge.EVENT_BUS.register(this);
        Registers.items.register(modEventBus);
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
    public void onCurioEquip(CurioEquipEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        if(event.getStack().getItem().equals(Registers.metawings.get()) && !player.isCreative() && !player.isSpectator())
            player.abilities.allowFlying = true;
    }

    @SubscribeEvent
    public void onCurioUnequip(CurioUnequipEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntity();
        if(event.getStack().getItem().equals(Registers.metawings.get()) && !player.isCreative() && !player.isSpectator()) {
            player.abilities.allowFlying = false;
            player.abilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if(event.getEntity().getType().equals(EntityType.PLAYER)) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (!player.isCreative() && !player.isSpectator() && !event.getEntity().getEntityWorld().getGameRules().get(GameRules.KEEP_INVENTORY).get())
                player.abilities.allowFlying = false;
        }
    }
}
