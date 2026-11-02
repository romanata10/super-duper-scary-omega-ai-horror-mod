package com.omega.horror.entity;

import com.omega.horror.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Самолёт. Садись на него и лети туда, куда смотришь.
 */
public class AirplaneEntity extends Entity {
    public AirplaneEntity(EntityType<?> type, Level level) {
        super(type, level);
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
    public Entity getControllingPassenger() {
        return this.getFirstPassenger() instanceof LivingEntity living ? living : null;
    }

    @Override
    public void tick() {
        super.tick();
        Entity pilot = this.getControllingPassenger();
        if (pilot instanceof LivingEntity living) {
            Vec3 look = living.getLookAngle();
            double speed = 1.1;
            this.setDeltaMovement(look.x * speed, look.y * speed, look.z * speed);
            this.setYRot(living.getYRot());
            this.yRotO = this.getYRot();
        } else {
            this.setDeltaMovement(this.getDeltaMovement().x * 0.9,
                    this.getDeltaMovement().y - 0.04, this.getDeltaMovement().z * 0.9);
        }
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && !player.isPassenger()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!this.level().isClientSide && !this.isRemoved()) {
            this.ejectPassengers();
            this.spawnAtLocation(new ItemStack(ModItems.AIRPLANE.get()));
            this.discard();
        }
        return true;
    }
}
