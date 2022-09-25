package com.wosaj.zenhorizon.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The content is copied from Botania mod by Vazkii
 * @author Vazkii
 */
public class CapUtil {
    public static <T, U extends T> ICapabilityProvider makeProvider(Capability<T> cap, U instance) {
        LazyOptional<T> lazyInstanceButNotReally = LazyOptional.of(() -> instance);
        return new CapProvider<>(cap, lazyInstanceButNotReally);
    }

    public static <T extends SerializableComponent> ICapabilityProvider makeSavedProvider(Capability<T> cap, T instance) {
        return new CapProviderSerializable<>(cap, instance);
    }

    private static class CapProvider<T> implements ICapabilityProvider {
        protected final Capability<T> cap;
        protected final LazyOptional<T> lazyInstanceButNotReally;

        public CapProvider(Capability<T> cap, LazyOptional<T> instance) {
            this.cap = cap;
            this.lazyInstanceButNotReally = instance;
        }

        @Nonnull
        @Override
        public <C> LazyOptional<C> getCapability(@Nonnull Capability<C> queryCap, @Nullable Direction side) {
            return cap.orEmpty(queryCap, lazyInstanceButNotReally);
        }
    }

    private static class CapProviderSerializable<T extends SerializableComponent> extends CapProvider<T> implements INBTSerializable<CompoundTag> {
        public CapProviderSerializable(Capability<T> cap, T instance) {
            super(cap, LazyOptional.of(() -> instance));
        }

        @Override
        public CompoundTag serializeNBT() {
            return lazyInstanceButNotReally.map(SerializableComponent::serializeNBT).orElse(null);
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            lazyInstanceButNotReally.ifPresent(i -> i.deserializeNBT(nbt));
        }
    }

    public static abstract class SerializableComponent {
        public abstract void readFromNbt(CompoundTag tag);

        public abstract void writeToNbt(CompoundTag tag);

        @Nonnull
        public final CompoundTag serializeNBT() {
            var ret = new CompoundTag();
            writeToNbt(ret);
            return ret;
        }

        public final void deserializeNBT(CompoundTag nbt) {
            readFromNbt(nbt);
        }
    }
}
