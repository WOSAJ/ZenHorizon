package com.wosaj.zenhorizon.common.entity.projectile;

import com.wosaj.zenhorizon.common.entity.EntityTypes;
import com.wosaj.zenhorizon.common.entity.misc.MagicCloudEntity;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MagicCloudShotEntity extends AbstractArrowEntity {
    public MagicCloudShotEntity(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @SuppressWarnings("unused")
    public MagicCloudShotEntity(EntityType<? extends AbstractArrowEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    public MagicCloudShotEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if(result.getType() == RayTraceResult.Type.ENTITY) {
            EntityRayTraceResult entityResult = (EntityRayTraceResult) result;
            if(entityResult.getEntity().isLiving()) {
                LivingEntity entity = (LivingEntity) entityResult.getEntity();
                if(entity != getShooter() && entity.attackable()) {
                    entity.attackEntityFrom(DamageSource.MAGIC, 4);
                    entity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 80, 2, true, true));
                    entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 80, 1, true, true));
                    entity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 80, 1, true, true));
                    remove(false);
                }
            }
        } else if(result.getType() == RayTraceResult.Type.BLOCK) groundEvent();
    }

    @Override
    public void tick() {
        if(onGround) remove(false);
        super.tick();
        if(!world.isRemote()) {
            ((ServerWorld) world).spawnParticle(
                    ParticleTypes.WITCH,
                    getPosX(),
                    getPosY(),
                    getPosZ(),
                    3,
                    0,
                    0,
                    0,
                    1
            );
        }
    }

    @Override
    public boolean getShotFromCrossbow() {
        return false;
    }

    @Override
    public boolean getIsCritical() {
        return false;
    }

    private void groundEvent() {
        try {
            MagicCloudEntity entity = new MagicCloudEntity(EntityTypes.MAGIC_CLOUD.get(), getShooter(), world);
            int y = (int) Math.floor(getPosY());
            for (int i = 0; i < 2; i++)
                if (!world.getBlockState(new BlockPos(getPosX(), y - 1, getPosZ())).isSolid()) y--;
            if (!world.getBlockState(new BlockPos(getPosX(), y - 1, getPosZ())).isSolid()) return;
            entity.setPosition(getPosX(), y, getPosZ());
            world.addEntity(entity);
            world.setEntityState(this, (byte) 0);
        } finally {
            remove();
        }
    }
}
