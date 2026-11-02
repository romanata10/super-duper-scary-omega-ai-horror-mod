package com.omega.horror.item;

import com.omega.horror.registry.ModSounds;

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
            ModSounds.CREEPER_PRIMED,
            ModSounds.ENDERMAN_SCREAM,
            ModSounds.GHAST_SCREAM,
            ModSounds.WITHER_SPAWN,
            ModSounds.ZOMBIE_AMBIENT,
            ModSounds.SKELETON_AMBIENT,
            ModSounds.BLAZE_AMBIENT,
            ModSounds.GENERIC_EXPLODE,
            ModSounds.AMBIENT_CAVE,
            ModSounds.WARDEN_HEARTBEAT,
            ModSounds.ANVIL_LAND,
            ModSounds.EXPERIENCE_ORB_PICKUP
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
