package com.wosaj.zenhorizon.common.capability;

import com.wosaj.zenhorizon.common.networking.Networking;
import com.wosaj.zenhorizon.common.networking.ZenUpdatePacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nonnull;

public class Zen {
    private int zen;
    private int maxZen = 20;

    public static LazyOptional<Zen> get(Entity entity) {
        return entity.getCapability(CapabilityZen.INSTANCE);
    }

    public void sync(ServerPlayerEntity entity) {
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

    public static final class ZenStorage implements Capability.IStorage<Zen> {
        @Nonnull
        @Override
        public INBT writeNBT(Capability<Zen> capability, Zen instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("Zen", instance.zen);
            nbt.putInt("MaxZen", instance.maxZen);
            return nbt;
        }

        @Override
        public void readNBT(Capability<Zen> capability, Zen instance, Direction side, INBT nbt) {
            if (!(nbt instanceof CompoundNBT)) return;
            CompoundNBT compound = (CompoundNBT) nbt;
            instance.zen = compound.getInt("Zen");
            instance.maxZen = compound.getInt("MaxZen");
        }
    }
}
