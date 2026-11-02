package com.omega.horror.entity;

import com.omega.horror.registry.ModSounds;

import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/**
 * mart из Roblox-игры nullscape — жутковатый торгаш, бродит по ночам.
 */
public class MartEntity extends PathfinderMob {
    private static final String[] LINES = {
            "...добро пожаловать в mart...",
            "у нас today всё по Null-ценам...",
            "не смотри ему в глаза...",
            "молоко? у нас его нет. david забрал.",
            "осторожно с кнопками. их здесь 5..."
    };

    public MartEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob_createAttributes();
    }

    private static AttributeSupplier.Builder Mob_createAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 24.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 240 == 0) {
            Player player = this.level().getNearestPlayer(this, 24.0);
            if (player != null) {
                player.sendSystemMessage(Component.literal("🛒 mart: " + LINES[this.random.nextInt(LINES.length)])
                        .withStyle(ChatFormatting.YELLOW));
                this.level().playSound(null, this.blockPosition(), ModSounds.VILLAGER_AMBIENT,
                        SoundSource.NEUTRAL, 1.0f, 0.6f);
            }
        }
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() {
        return ModSounds.ENDERMAN_STARE;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.VILLAGER_HURT;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return ModSounds.VILLAGER_DEATH;
    }
}
