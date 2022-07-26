package com.wosaj.zenhorizon.capability.storage.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;

public class Zen {
    public int zen;
    public int maxZen = 20;

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
