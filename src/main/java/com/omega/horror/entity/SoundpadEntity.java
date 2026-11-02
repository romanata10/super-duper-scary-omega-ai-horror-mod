package com.omega.horror.entity;

import com.omega.horror.registry.ModSounds;

import com.omega.horror.registry.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/**
 * Моб в виде soundpad: сам производит рандомные звуки.
 * При убийстве выпадает предмет рандомных звуков.
 */
public class SoundpadEntity extends Monster {
    private static final SoundEvent[] SOUNDS = {
            ModSounds.CREEPER_PRIMED,
            ModSounds.ENDERMAN_SCREAM,
            ModSounds.GHAST_SCREAM,
            ModSounds.WITHER_SPAWN,
            ModSounds.ZOMBIE_AMBIENT,
            ModSounds.SKELETON_AMBIENT,
            ModSounds.BLAZE_AMBIENT,
            ModSounds.AMBIENT_CAVE,
            ModSounds.WARDEN_HEARTBEAT,
            ModSounds.NOTE_BLOCK_BASS,
            ModSounds.NOTE_BLOCK_PLING,
            ModSounds.DOLPHIN_AMBIENT
    };

    public SoundpadEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.tickCount % 50 == 0 && this.random.nextInt(3) == 0) {
            SoundEvent sound = SOUNDS[this.random.nextInt(SOUNDS.length)];
            this.level().playSound(null, this.blockPosition(), sound, SoundSource.HOSTILE,
                    2.0f, 0.6f + this.random.nextFloat() * 0.8f);
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        this.spawnAtLocation(new ItemStack(ModItems.RANDOM_SOUNDS.get()));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.NOTE_BLOCK_PLING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.NOTE_BLOCK_BASS;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.NOTE_BLOCK_DIDGERIDOO;
    }
}
