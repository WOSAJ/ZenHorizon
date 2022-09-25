package com.wosaj.zenhorizon.common.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

@SuppressWarnings("all")
public class EntityHelper {
    private static final Random rand = new Random();
    public static boolean momentalHurt(LivingEntity livingEntity, DamageSource source, float amount) {
        if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(livingEntity, source, amount)) return false;
        if (livingEntity.isInvulnerableTo(source)) {
            return false;
        } else if (livingEntity.level.isClientSide) {
            return false;
        } else if (livingEntity.isDeadOrDying()) {
            return false;
        } else if (source.isFire() && livingEntity.hasEffect(MobEffects.FIRE_RESISTANCE)) {
            return false;
        } else {
            if (livingEntity.isSleeping() && !livingEntity.level.isClientSide) {
                livingEntity.stopSleeping();
            }

            livingEntity.noActionTime = 0;
            float f = amount;
            boolean flag = false;
            float f1 = 0.0F;
            if (amount > 0.0F && livingEntity.isDamageSourceBlocked(source)) {
                net.minecraftforge.event.entity.living.ShieldBlockEvent ev = net.minecraftforge.common.ForgeHooks.onShieldBlock(livingEntity, source, amount);
                if (!ev.isCanceled()) {
                    if (ev.shieldTakesDamage()) livingEntity.hurtCurrentlyUsedShield(amount);
                    f1 = ev.getBlockedDamage();
                    amount -= ev.getBlockedDamage();
                    if (!source.isProjectile()) {
                        Entity entity = source.getDirectEntity();
                        if (entity instanceof LivingEntity) {
                            livingEntity.blockUsingShield((LivingEntity) entity);
                        }
                    }

                    flag = true;
                }
            }

            livingEntity.animationSpeed = 1.5F;
            boolean flag1 = true;
            if ((float) livingEntity.invulnerableTime > 10.0F) {
                if (amount <= livingEntity.lastHurt) {
                    return false;
                }

                livingEntity.actuallyHurt(source, amount - livingEntity.lastHurt);
                livingEntity.lastHurt = amount;
                flag1 = false;
            } else {
                livingEntity.lastHurt = amount;
                livingEntity.invulnerableTime = 20;
                livingEntity.actuallyHurt(source, amount);
                livingEntity.hurtDuration = 10;
                livingEntity.hurtTime = livingEntity.hurtDuration;
            }

            if (source.isDamageHelmet() && !livingEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                livingEntity.hurtHelmet(source, amount);
                amount *= 0.75F;
            }

            livingEntity.hurtDir = 0.0F;
            Entity entity1 = source.getEntity();
            if (entity1 != null) {
                if (entity1 instanceof LivingEntity && !source.isNoAggro()) {
                    livingEntity.setLastHurtByMob((LivingEntity) entity1);
                }

                if (entity1 instanceof Player) {
                    livingEntity.lastHurtByPlayerTime = 100;
                    livingEntity.lastHurtByPlayer = (Player) entity1;
                } else if (entity1 instanceof net.minecraft.world.entity.TamableAnimal) {
                    net.minecraft.world.entity.TamableAnimal tamableEntity = (net.minecraft.world.entity.TamableAnimal) entity1;
                    if (tamableEntity.isTame()) {
                        livingEntity.lastHurtByPlayerTime = 100;
                        LivingEntity livingentity = tamableEntity.getOwner();
                        if (livingentity != null && livingentity.getType() == EntityType.PLAYER) {
                            livingEntity.lastHurtByPlayer = (Player) livingentity;
                        } else {
                            livingEntity.lastHurtByPlayer = null;
                        }
                    }
                }
            }

            if (flag1) {
                if (flag) {
                    livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 29);
                } else if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns()) {
                    livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 33);
                } else {
                    byte b0;
                    if (source == DamageSource.DROWN) {
                        b0 = 36;
                    } else if (source.isFire()) {
                        b0 = 37;
                    } else if (source == DamageSource.SWEET_BERRY_BUSH) {
                        b0 = 44;
                    } else if (source == DamageSource.FREEZE) {
                        b0 = 57;
                    } else {
                        b0 = 2;
                    }

                    livingEntity.level.broadcastEntityEvent(livingEntity, b0);
                }

                if (source != DamageSource.DROWN && (!flag || amount > 0.0F)) {
                    livingEntity.markHurt();
                }

                if (entity1 != null) {
                    double d1 = entity1.getX() - livingEntity.getX();

                    double d0;
                    for (d0 = entity1.getZ() - livingEntity.getZ(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                        d1 = (Math.random() - Math.random()) * 0.01D;
                    }

                    livingEntity.hurtDir = (float) (Mth.atan2(d0, d1) * (double) (180F / (float) Math.PI) - (double) livingEntity.getYRot());
                    livingEntity.knockback((double) 0.4F, d1, d0);
                } else {
                    livingEntity.hurtDir = (float) ((int) (Math.random() * 2.0D) * 180);
                }
            }

            if (livingEntity.isDeadOrDying()) {
                if (!livingEntity.checkTotemDeathProtection(source)) {
                    SoundEvent soundevent = livingEntity.getDeathSound();
                    if (flag1 && soundevent != null) {
                        livingEntity.playSound(soundevent, livingEntity.getSoundVolume(), livingEntity.getVoicePitch());
                    }

                    livingEntity.die(source);
                }
            } else if (flag1) {
                livingEntity.playHurtSound(source);
            }

            boolean flag2 = !flag || amount > 0.0F;
            if (flag2) {
                livingEntity.lastDamageSource = source;
                livingEntity.lastDamageStamp = livingEntity.level.getGameTime();
            }

            if (livingEntity instanceof ServerPlayer) {
                CriteriaTriggers.ENTITY_HURT_PLAYER.trigger((ServerPlayer) livingEntity, source, f, amount, flag);
                if (f1 > 0.0F && f1 < 3.4028235E37F) {
                    ((ServerPlayer) livingEntity).awardStat(Stats.CUSTOM.get(Stats.DAMAGE_BLOCKED_BY_SHIELD), Math.round(f1 * 10.0F));
                }
            }

            if (entity1 instanceof ServerPlayer) {
                CriteriaTriggers.PLAYER_HURT_ENTITY.trigger((ServerPlayer) entity1, livingEntity, source, f, amount, flag);
            }

            return flag2;
        }
    }
}
