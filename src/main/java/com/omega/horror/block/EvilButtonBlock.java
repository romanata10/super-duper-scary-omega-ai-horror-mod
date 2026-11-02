package com.omega.horror.block;

import com.omega.horror.registry.ModSounds;

import com.omega.horror.entity.ButtonMonsterEntity;
import com.omega.horror.registry.ModEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.jetbrains.annotations.Nullable;

/**
 * Великая злая кнопка. Поставь 5 штук рядом — появится злой кнопочный монстр.
 */
public class EvilButtonBlock extends ButtonBlock {
    public EvilButtonBlock(Properties properties) {
        super(properties, BlockSetType.STONE, 30, false);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state,
                            @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            int count = 0;
            for (BlockPos p : BlockPos.betweenClosed(pos.offset(-8, -4, -8), pos.offset(8, 4, 8))) {
                if (level.getBlockState(p).is(this)) {
                    count++;
                }
            }
            if (count >= 5) {
                ButtonMonsterEntity monster = ModEntities.BUTTON_MONSTER.get().create(level);
                if (monster != null) {
                    monster.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                            level.random.nextFloat() * 360.0f, 0.0f);
                    level.addFreshEntity(monster);
                    level.playSound(null, pos, ModSounds.WITHER_SPAWN, SoundSource.HOSTILE, 2.0f, 0.6f);
                    if (level.getServer() != null) {
                        level.getServer().getPlayerList().broadcastSystemMessage(
                                Component.literal("😈 ПОЯВИЛСЯ ЗЛОЙ КНОПОЧНЫЙ МОНСТР! УБЕЙ ЕГО — ВЫПАДЕТ 240 КНОПОК!")
                                        .withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
                    }
                }
            }
        }
    }
}
