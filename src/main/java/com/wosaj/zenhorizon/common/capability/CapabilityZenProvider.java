package com.wosaj.zenhorizon.common.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("all")
public class CapabilityZenProvider implements ICapabilitySerializable<INBT> {
    //Capability can be null, but what prevents me from using a ZenStorage instance? X_X
    private static final Zen.ZenStorage STORAGE = new Zen.ZenStorage();
    private final Zen zen = new Zen();
    private final LazyOptional<Zen> lazyOptional = LazyOptional.of(() -> zen);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityZen.INSTANCE) return (LazyOptional<T>) LazyOptional.of(() -> zen);
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        INBT zenNBT = STORAGE.writeNBT(null, zen, null);
        nbt.put("zen", zenNBT);
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if(!(nbt instanceof CompoundNBT)) return;
        CompoundNBT compound = (CompoundNBT) nbt;
        STORAGE.readNBT(null, zen, null, compound.get("zen"));
    }
}
