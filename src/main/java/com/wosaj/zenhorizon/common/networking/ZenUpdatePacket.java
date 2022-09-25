package com.wosaj.zenhorizon.common.networking;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ZenUpdatePacket {
    public int zen;
    public int maxZen;

    @SuppressWarnings("unused")
    public ZenUpdatePacket(int zen, int maxZen) {
        this.zen = zen;
        this.maxZen = maxZen;
    }

    public ZenUpdatePacket(Zen cap) {
        zen = cap.getZen();
        maxZen = cap.getMaxZen();
    }

    public ZenUpdatePacket(FriendlyByteBuf buffer) {
        zen = buffer.readInt();
        maxZen = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(zen);
        buffer.writeInt(maxZen);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(Minecraft.getInstance().player == null) return;
            Zen.get(Minecraft.getInstance().player).ifPresent(cap -> {
                cap.setZen(zen);
                cap.setMaxZen(maxZen);
            });
            context.get().setPacketHandled(true);
        });
    }
}
