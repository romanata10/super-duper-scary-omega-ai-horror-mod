package com.omega.horror.block;

import com.omega.horror.registry.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Слой блок Krita. Правый клик — рисует очередное зло на холсте.
 */
public class KritaBlock extends Block {
    public static final IntegerProperty DRAWING = IntegerProperty.create("drawing", 0, 7);

    public KritaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DRAWING, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DRAWING);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            int next = (state.getValue(DRAWING) + 1) % 8;
            level.setBlock(pos, state.setValue(DRAWING, next), 3);
            level.playSound(null, pos, ModSounds.BRUSH_GENERIC, SoundSource.BLOCKS,
                    1.0f, 0.5f + next * 0.1f);
            player.displayClientMessage(Component.literal("🎨 Ты нарисовал зло на Krita! (уровень " + next + "/7)"), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
