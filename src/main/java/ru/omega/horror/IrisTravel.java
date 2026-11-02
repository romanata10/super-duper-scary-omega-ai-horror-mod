package ru.omega.horror;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.ITeleporter;

public final class IrisTravel {
    private IrisTravel() {}
    public static final ResourceKey<Level> IRIS = ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, new ResourceLocation(OmegaHorror.ID, "iris"));

    public static boolean travel(ServerPlayer player) {
        boolean leaving = player.level().dimension().equals(IRIS);
        ServerLevel destination = leaving ? player.server.overworld() : player.server.getLevel(IRIS);
        if (destination == null) return false;
        BlockPos target;
        if (leaving) {
            target = destination.getSharedSpawnPos().above();
        } else {
            target = new BlockPos(0, 81, 0);
            for (int x = -3; x <= 3; x++) for (int z = -3; z <= 3; z++) {
                destination.setBlock(new BlockPos(x, 80, z), Blocks.END_STONE.defaultBlockState(), 3);
                for (int y = 81; y <= 83; y++) destination.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
            }
        }
        BlockPos finalTarget = target;
        player.changeDimension(destination, new ITeleporter() {
            @Override public Entity placeEntity(Entity entity, ServerLevel current, ServerLevel dest, float yaw, Function<Boolean, Entity> reposition) {
                Entity moved = reposition.apply(false);
                moved.moveTo(finalTarget.getX() + .5, finalTarget.getY(), finalTarget.getZ() + .5, yaw, 0);
                return moved;
            }
        });
        return true;
    }
}
