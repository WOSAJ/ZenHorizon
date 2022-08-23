package com.wosaj.zenhorizon.common.networking;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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

    public ZenUpdatePacket(PacketBuffer buffer) {
        zen = buffer.readInt();
        maxZen = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(zen);
        buffer.writeInt(maxZen);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if(ZenHorizon.proxy.getPlayer() == null) return;
            Zen.get(ZenHorizon.proxy.getPlayer()).ifPresent(cap -> {
                cap.setZen(zen);
                cap.setMaxZen(maxZen);
            });
            context.get().setPacketHandled(true);
        });
    }
}
