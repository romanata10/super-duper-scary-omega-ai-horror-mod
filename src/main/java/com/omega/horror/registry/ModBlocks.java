package com.omega.horror.registry;

import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.block.BrowserPcBlock;
import com.omega.horror.block.EvilButtonBlock;
import com.omega.horror.block.KritaBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, OmegaHorrorMod.MODID);

    /** Великая злая кнопка. Если поставить 5 штук рядом — появится кнопочный монстр. */
    public static final RegistryObject<Block> EVIL_BUTTON = BLOCKS.register("evil_button",
            () -> new EvilButtonBlock(BlockBehaviour.Properties.of()
                    .noCollission().strength(0.5f).sound(SoundType.STONE).noOcclusion()));

    /** Слой блок Krita — на нём можно рисовать зло (клик переключает рисунки). */
    public static final RegistryObject<Block> KRITA = BLOCKS.register("krita_block",
            () -> new KritaBlock(BlockBehaviour.Properties.of()
                    .strength(1.5f).sound(SoundType.WOOL)));

    /** Блок браузера в виде ПК с «рабочим» браузером. */
    public static final RegistryObject<Block> BROWSER_PC = BLOCKS.register("browser_pc",
            () -> new BrowserPcBlock(BlockBehaviour.Properties.of()
                    .strength(2.0f).sound(SoundType.METAL)));

    /** Жидкое молоко (льётся из ведра). */
    public static final RegistryObject<Block> MILK_BLOCK = BLOCKS.register("milk",
            () -> new LiquidBlock(ModFluids.MILK, BlockBehaviour.Properties.of()
                    .noCollission().strength(100.0f).noLootTable().noOcclusion()));
}
