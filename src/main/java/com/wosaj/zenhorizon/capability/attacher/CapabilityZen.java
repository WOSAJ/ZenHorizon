package com.wosaj.zenhorizon.capability.attacher;

import com.wosaj.zenhorizon.capability.storage.player.Zen;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityZen {
    @CapabilityInject(Zen.class)
    public static Capability<Zen> INSTANCE = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(Zen.class, new Zen.ZenStorage(), Zen::new);
    }
}
