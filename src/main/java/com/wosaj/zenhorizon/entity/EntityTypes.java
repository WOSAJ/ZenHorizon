package com.wosaj.zenhorizon.entity;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.entity.misc.MagicCloudEntity;
import com.wosaj.zenhorizon.entity.misc.projectile.MagicCloudShotEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class EntityTypes {
    private static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(ForgeRegistries.ENTITIES, ZenHorizon.MODID);
    public static void register(IEventBus bus) {REG.register(bus);}

    public static final RegistryObject<EntityType<MagicCloudShotEntity>> MAGIC_CLOUD_SHOT = REG.register("magic_cloud_shot", () -> EntityType.Builder.<MagicCloudShotEntity>create(MagicCloudShotEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).updateInterval(20).build(new ResourceLocation(ZenHorizon.MODID, "magic_cloud_shot").toString()));
    public static final RegistryObject<EntityType<MagicCloudEntity>> MAGIC_CLOUD = REG.register("magic_cloud", () -> EntityType.Builder.<MagicCloudEntity>create(MagicCloudEntity::new, EntityClassification.MISC).size(0.5F, 0.1F).updateInterval(20).build(new ResourceLocation(ZenHorizon.MODID, "magic_cloud").toString()));
}
