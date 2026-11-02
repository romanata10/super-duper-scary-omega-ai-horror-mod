package com.omega.horror.item;

import com.omega.horror.entity.AirplaneEntity;
import com.omega.horror.registry.ModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AirplaneItem extends Item {
    public AirplaneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            AirplaneEntity plane = ModEntities.AIRPLANE.get().create(level);
            if (plane != null) {
                Vec3 look = player.getLookAngle();
                plane.moveTo(player.getX() + look.x * 2.0, player.getY() + 1.0, player.getZ() + look.z * 2.0,
                        player.getYRot(), 0.0f);
                level.addFreshEntity(plane);
                player.getItemInHand(hand).shrink(1);
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
    }
}
