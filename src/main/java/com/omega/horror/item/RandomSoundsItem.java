package com.omega.horror.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RandomSoundsItem extends Item {
    private static final SoundEvent[] SOUNDS = {
            SoundEvents.CREEPER_PRIMED,
            SoundEvents.ENDERMAN_SCREAM,
            SoundEvents.GHAST_SCREAM,
            SoundEvents.WITHER_SPAWN,
            SoundEvents.ZOMBIE_AMBIENT,
            SoundEvents.SKELETON_AMBIENT,
            SoundEvents.BLAZE_AMBIENT,
            SoundEvents.GENERIC_EXPLODE,
            SoundEvents.AMBIENT_CAVE,
            SoundEvents.WARDEN_HEARTBEAT,
            SoundEvents.ANVIL_LAND,
            SoundEvents.EXPERIENCE_ORB_PICKUP
    };

    public RandomSoundsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            SoundEvent sound = SOUNDS[level.random.nextInt(SOUNDS.length)];
            level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 2.0f,
                    0.5f + level.random.nextFloat());
            player.displayClientMessage(Component.literal("🔊 Проигрывается рандомный звук: "
                    + sound.getLocation().getPath()), true);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
}
