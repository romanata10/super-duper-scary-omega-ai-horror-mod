package com.omega.horror.registry;

import com.omega.horror.OmegaHorrorMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OmegaHorrorMod.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("omega_horror_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.omega_horror"))
                    .icon(() -> new ItemStack(ModItems.NULL_SPAWN_EGG.get()))
                    .displayItems((params, output) ->
                            ModItems.ITEMS.getEntries().forEach(entry -> output.accept(entry.get())))
                    .build());
}
