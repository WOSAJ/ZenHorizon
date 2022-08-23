package com.wosaj.zenhorizon.common.networking;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel INSTANCE;
    public static int ID;

    public static int nextID() {
        return ID++;
    }

    public static void register() {
        INSTANCE  = NetworkRegistry.newSimpleChannel(new ResourceLocation(ZenHorizon.MODID, "network"), () -> "1.0", s -> true, s -> true);
        INSTANCE.registerMessage(nextID(), ZenUpdatePacket.class, ZenUpdatePacket::encode, ZenUpdatePacket::new, ZenUpdatePacket::handle);
    }
}
