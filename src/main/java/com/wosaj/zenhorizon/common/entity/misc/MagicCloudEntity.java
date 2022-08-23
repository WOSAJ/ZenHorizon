package com.wosaj.zenhorizon.common.entity.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@ParametersAreNonnullByDefault
public class MagicCloudEntity extends Entity {
    @Nullable
    private UUID owner;
    private short lifetime;
    private short cooldown;

    public MagicCloudEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MagicCloudEntity(EntityType<?> entityTypeIn, @Nullable Entity owner, World worldIn) {
        super(entityTypeIn, worldIn);
        this.owner = owner == null ? null : owner.getUniqueID();
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            super.tick();
            if (cooldown >= 0) {
                world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(getPosX() - 2, getPosY(), getPosZ() - 2, getPosX() + 2, getPosY() + 1, getPosZ() + 2), null)
                        .stream()
                        .filter(Entity::isLiving)
                        .map(e -> (LivingEntity) e)
                        .filter(e -> e.attackable() && !e.getUniqueID().equals(owner))
                        .forEach(e -> e.attackEntityFrom(DamageSource.MAGIC, 1));
                cooldown = 10;
            } else cooldown--;
            ((ServerWorld) world).spawnParticle(ParticleTypes.WITCH, getPosX(), getPosY(), getPosZ(), 70, 1, 0, 1, 1);
            if (lifetime >= 200 || lifetime < 0) remove();
            lifetime++;
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if(compound.contains("owner")) owner = UUID.fromString(compound.getString("owner"));
        lifetime = compound.getShort("lifetime");
        cooldown = compound.getShort("cooldown");
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        if(owner != null) compound.putString("owner", owner.toString());
        compound.putShort("lifetime", lifetime);
        compound.putShort("cooldown", cooldown);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        int id = 0;
        if(owner != null) {
            PlayerEntity entity = world.getPlayerByUuid(owner);
            if(entity != null) id = entity.getEntityId();
        }
        return new SSpawnObjectPacket(this, id);
    }

    @Override
    protected void registerData() {}
}
