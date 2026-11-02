package com.omega.horror.entity;

import com.omega.horror.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.ChatFormatting;

import java.util.List;

/**
 * Страшный монстр Дэвид. Крадёт твоих коров и всё твоё молоко.
 */
public class DavidEntity extends Monster {
    public DavidEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Cow.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.tickCount % 30 != 0) {
            return;
        }
        // Крадёт коров
        List<Cow> cows = this.level().getEntitiesOfClass(Cow.class, this.getBoundingBox().inflate(24.0));
        for (Cow cow : cows) {
            if (this.distanceToSqr(cow) < 4.0) {
                BlockPos pos = cow.blockPosition();
                cow.discard();
                this.level().playSound(null, pos, SoundEvents.GENERIC_DRINK, SoundSource.HOSTILE, 2.0f, 0.4f);
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.SPLASH,
                        cow.getX(), cow.getY() + 0.5, cow.getZ(), 40, 0.5, 0.5, 0.5, 0.1);
                if (this.level().getServer() != null) {
                    this.level().getServer().getPlayerList().broadcastSystemMessage(
                            Component.literal("🐄 David украл твою корову!").withStyle(ChatFormatting.RED), false);
                }
                break;
            }
        }
        // Крадёт молоко у ближайшего игрока
        Player player = this.level().getNearestPlayer(this, 16.0);
        if (player != null) {
            boolean stole = false;
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack stack = player.getInventory().items.get(i);
                if (stack.is(ModItems.MILK_BUCKET.get())) {
                    player.getInventory().items.set(i, new ItemStack(Items.BUCKET));
                    stole = true;
                } else if (stack.is(ModItems.GLASS_OF_MILK.get())) {
                    stack.shrink(1);
                    stole = true;
                }
            }
            if (stole) {
                player.sendSystemMessage(Component.literal("🥛 David украл всё твоё молоко!")
                        .withStyle(ChatFormatting.DARK_RED));
                this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_DRINK,
                        SoundSource.HOSTILE, 2.0f, 0.3f);
            }
        }
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
}
