package ru.omega.horror;

import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;

public class MilkFluidType extends FluidType {
    public MilkFluidType() { super(FluidType.Properties.create().density(1000).viscosity(1000)); }
    @Override public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override public ResourceLocation getStillTexture() { return new ResourceLocation("minecraft:block/water_still"); }
            @Override public ResourceLocation getFlowingTexture() { return new ResourceLocation("minecraft:block/water_flow"); }
            @Override public int getTintColor() { return 0xFFFFFFFF; }
        });
    }
}
