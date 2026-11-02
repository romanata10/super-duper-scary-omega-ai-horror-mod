package ru.omega.horror;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

/** A simple paintable canvas: a dye paints the whole block, rather than individual pixels. */
public class KritaBlock extends Block {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public KritaBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(COLOR, DyeColor.WHITE));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
    }
    @Override public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(player.getItemInHand(hand).getItem() instanceof DyeItem dye)) return InteractionResult.PASS;
        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(COLOR, dye.getDyeColor()), 3);
            if (!player.getAbilities().instabuild) player.getItemInHand(hand).shrink(1);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
