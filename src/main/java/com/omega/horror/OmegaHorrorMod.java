package com.omega.horror;

import com.omega.horror.client.ClientSetup;
import com.omega.horror.event.ModEvents;
import com.omega.horror.registry.ModBlocks;
import com.omega.horror.registry.ModCreativeTabs;
import com.omega.horror.registry.ModEntities;
import com.omega.horror.registry.ModFluids;
import com.omega.horror.registry.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OmegaHorrorMod.MODID)
public class OmegaHorrorMod {
    public static final String MODID = "omega_horror";

    public OmegaHorrorMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModEntities.registerAttributes(bus);
        ModFluids.FLUIDS.register(bus);
        ModFluids.FLUID_TYPES.register(bus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(bus);

        MinecraftForge.EVENT_BUS.register(ModEvents.class);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientSetup.register(bus));
    }
}
