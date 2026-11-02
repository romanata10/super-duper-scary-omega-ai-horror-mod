package com.omega.horror.entity;

import com.omega.horror.registry.ModSounds;

import com.omega.horror.registry.ModBlocks;
import com.omega.horror.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.sounds.SoundEvents;

/**
 * Злой кнопочный монстр. Появляется, когда поставлено 5 злых кнопок,
 * и расставляет кнопки везде. При смерти выпадает 240 кнопок.
 */
public class ButtonMonsterEntity extends Monster {
    public ButtonMonsterEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide || this.tickCount % 25 != 0) {
            return;
        }
        // Расставляет злые кнопки вокруг себя
        for (int attempt = 0; attempt < 4; attempt++) {
            BlockPos solid = this.blockPosition().offset(
                    this.random.nextInt(13) - 6, -this.random.nextInt(6) - 1, this.random.nextInt(13) - 6);
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(this.random);
            BlockPos air = solid.relative(dir);
            if (this.level().getBlockState(solid).isSolidRender(this.level(), solid)
                    && this.level().getBlockState(air).isAir()) {
                this.level().setBlock(air, ModBlocks.EVIL_BUTTON.get().defaultBlockState()
                        .setValue(ButtonBlock.FACING, dir)
                        .setValue(ButtonBlock.FACE, AttachFace.WALL), 3);
                this.level().playSound(null, air, ModSounds.STONE_BUTTON_CLICK_ON,
                        net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 0.5f);
                break;
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        // Выпадает ровно 240 кнопок
        int left = 240;
        while (left > 0) {
            int count = Math.min(64, left);
            this.spawnAtLocation(new ItemStack(ModItems.EVIL_BUTTON.get(), count));
            left -= count;
        }
        this.level().playSound(null, this.blockPosition(), ModSounds.CHEST_OPEN,
                net.minecraft.sounds.SoundSource.HOSTILE, 1.0f, 0.7f);
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getAmbientSound() {
        return ModSounds.STONE_BUTTON_CLICK_OFF;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.ANVIL_LAND;
    }

    @Override
    protected net.minecraft.sounds.SoundEvent getDeathSound() {
        return ModSounds.GENERIC_EXPLODE;
    }
}
