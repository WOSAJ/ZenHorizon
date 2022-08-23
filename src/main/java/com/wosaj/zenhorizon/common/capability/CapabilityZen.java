package com.wosaj.zenhorizon.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityZen {
    @CapabilityInject(Zen.class)
    public static Capability<Zen> INSTANCE;

    public static void register() {
        CapabilityManager.INSTANCE.register(Zen.class, new Zen.ZenStorage(), Zen::new);
    }
}
