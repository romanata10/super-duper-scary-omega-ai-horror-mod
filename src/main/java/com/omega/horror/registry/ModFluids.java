package com.omega.horror.registry;

import com.omega.horror.OmegaHorrorMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeProperties;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Consumer;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, OmegaHorrorMod.MODID);
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.FLUID_TYPES, OmegaHorrorMod.MODID);

    public static final RegistryObject<FlowingFluid> MILK =
            FLUIDS.register("milk", () -> new ForgeFlowingFluid.Source(MILK_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MILK_FLOWING =
            FLUIDS.register("flowing_milk", () -> new ForgeFlowingFluid.Flowing(MILK_PROPERTIES));

    public static final RegistryObject<FluidType> MILK_TYPE = FLUID_TYPES.register("milk",
            () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("fluid.omega_horror.milk")
                    .motionScale(0.014D)
                    .canConvertToSource(true)) {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeProperties> consumer) {
                    consumer.accept(new IClientFluidTypeProperties() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(OmegaHorrorMod.MODID, "block/milk_still");
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(OmegaHorrorMod.MODID, "block/milk_flow");
                        }

                        @Override
                        public ResourceLocation getOverlayTexture() {
                            return new ResourceLocation("minecraft", "block/water_overlay");
                        }

                        @Override
                        public int getTintColor() {
                            return 0xFFFFFFFF;
                        }
                    });
                }
            });

    public static final ForgeFlowingFluid.Properties MILK_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> MILK_TYPE.get(), () -> MILK.get(), () -> MILK_FLOWING.get())
            .slopeFindDistance(4)
            .levelDecreasePerBlock(2)
            .block(() -> ModBlocks.MILK_BLOCK.get())
            .bucket(() -> ModItems.MILK_BUCKET.get());
}
