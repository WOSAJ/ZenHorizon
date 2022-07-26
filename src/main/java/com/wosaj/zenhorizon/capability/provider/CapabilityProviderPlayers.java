package com.wosaj.zenhorizon.capability.provider;

import com.wosaj.zenhorizon.capability.attacher.CapabilityZen;
import com.wosaj.zenhorizon.capability.storage.player.Zen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("all")
public class CapabilityProviderPlayers implements ICapabilitySerializable<INBT> {

    private final Zen zen = new Zen();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityZen.INSTANCE) return (LazyOptional<T>) LazyOptional.of(() -> zen);
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        if(CapabilityZen.INSTANCE == null) return nbt;
        INBT zenNBT = CapabilityZen.INSTANCE.writeNBT(zen, null);
        nbt.put("zen", zenNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if(CapabilityZen.INSTANCE == null) return;
        if(!(nbt instanceof CompoundNBT)) return;
        CompoundNBT compound = (CompoundNBT) nbt;
        CapabilityZen.INSTANCE.readNBT(zen, null, compound.get("zen"));
    }
}
