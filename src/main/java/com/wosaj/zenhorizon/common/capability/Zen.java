package com.wosaj.zenhorizon.common.capability;

import com.wosaj.zenhorizon.common.networking.Networking;
import com.wosaj.zenhorizon.common.networking.ZenUpdatePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;

public class Zen extends CapUtil.SerializableComponent {
    private int zen;
    private int maxZen = 20;

    public static LazyOptional<Zen> get(Entity entity) {
        var cap = entity.getCapability(Capabilities.ZEN_CAPABILITY);
        return cap.isPresent() ? cap : LazyOptional.empty();
    }

    public void sync(ServerPlayer entity) {
        Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity), new ZenUpdatePacket(this));
    }

    public int getZen() {
        return zen;
    }

    public void setZen(int zen) {
        this.zen = Math.min(zen, maxZen);
    }

    public int getMaxZen() {
        return maxZen;
    }

    public void setMaxZen(int maxZen) {
        this.maxZen = Math.max(maxZen, 0);
    }

    public boolean canHasZen() {
        return maxZen > 0;
    }

    public boolean hasZen() {
        return zen > 0;
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("zen", zen);
        tag.putInt("maxZen", maxZen);
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        zen = tag.getInt("zen");
        maxZen = tag.getInt("maxZen");
    }
}
