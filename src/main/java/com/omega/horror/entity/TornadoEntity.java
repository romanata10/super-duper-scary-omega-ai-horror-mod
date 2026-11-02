package com.omega.horror.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Торнадо. Разрушает блоки, подбрасывает и калечит существ.
 */
public class TornadoEntity extends Entity {
    public TornadoEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.tickCount > 1200) {
                this.discard();
                return;
            }
            if (this.tickCount % 40 == 0) {
                double angle = this.random.nextDouble() * Math.PI * 2.0;
                this.setDeltaMovement(Math.cos(angle) * 0.4, 0.05, Math.sin(angle) * 0.4);
            }
            this.move(MoverType.SELF, this.getDeltaMovement());

            // Кусает и подбрасывает существ рядом
            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(3.0), LivingEntity::isAlive)) {
                entity.hurt(this.level().damageSources().generic(), 4.0f);
                entity.setDeltaMovement(entity.getDeltaMovement().add(
                        (this.random.nextDouble() - 0.5) * 0.6, 0.9, (this.random.nextDouble() - 0.5) * 0.6));
                entity.hasImpulse = true;
            }

            // Вырывает блоки и швыряет их
            if (this.tickCount % 15 == 0) {
                BlockPos pos = this.blockPosition().offset(
                        this.random.nextInt(9) - 4, -this.random.nextInt(5) - 1, this.random.nextInt(9) - 4);
                BlockState state = this.level().getBlockState(pos);
                if (!state.isAir() && !state.is(Blocks.BEDROCK)
                        && state.getDestroySpeed(this.level(), pos) >= 0.0f) {
                    this.level().destroyBlock(pos, false);
                    FallingBlockEntity falling = FallingBlockEntity.fall(this.level(), pos, state);
                    falling.setDeltaMovement((this.random.nextDouble() - 0.5) * 0.8, 0.6,
                            (this.random.nextDouble() - 0.5) * 0.8);
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                double angle = this.random.nextDouble() * Math.PI * 2.0;
                double radius = 1.5 + this.random.nextDouble() * 2.0;
                this.level().addParticle(ParticleTypes.CLOUD,
                        this.getX() + Math.cos(angle) * radius,
                        this.getY() + this.random.nextDouble() * this.getBbHeight(),
                        this.getZ() + Math.sin(angle) * radius, 0.0, 0.05, 0.0);
            }
        }
    }
}
