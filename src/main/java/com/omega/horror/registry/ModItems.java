package com.omega.horror.registry;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.item.AirplaneItem;
import com.omega.horror.item.RandomSoundsItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, OmegaHorrorMod.MODID);

    /** Ведро молока — молоко можно вылить как жидкость. */
    public static final RegistryObject<Item> MILK_BUCKET = ITEMS.register("milk_bucket",
            () -> new BucketItem(ModFluids.MILK, new Item.Properties()
                    .craftRemainder(Items.BUCKET).stacksTo(1)));

    /** Стакан молока, который дарит David, если выкупаться в молоке. */
    public static final RegistryObject<Item> GLASS_OF_MILK = ITEMS.register("glass_of_milk",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.8f).alwaysEat().build())
                    .stacksTo(16)));

    public static final RegistryObject<Item> EVIL_BUTTON = ITEMS.register("evil_button",
            () -> new BlockItem(ModBlocks.EVIL_BUTTON.get(), new Item.Properties()));

    public static final RegistryObject<Item> KRITA = ITEMS.register("krita_block",
            () -> new BlockItem(ModBlocks.KRITA.get(), new Item.Properties()));

    public static final RegistryObject<Item> BROWSER_PC = ITEMS.register("browser_pc",
            () -> new BlockItem(ModBlocks.BROWSER_PC.get(), new Item.Properties()));

    /** Предмет рандомных звуков — выпадает из soundpad-моба. */
    public static final RegistryObject<Item> RANDOM_SOUNDS = ITEMS.register("random_sounds",
            () -> new RandomSoundsItem(new Item.Properties().stacksTo(1)));

    /** Самолёт. */
    public static final RegistryObject<Item> AIRPLANE = ITEMS.register("airplane",
            () -> new AirplaneItem(new Item.Properties().stacksTo(1)));

    /** Носки фембойчика: +1000 HP и +100 силы. */
    public static final RegistryObject<Item> FEMBOY_SOCKS = ITEMS.register("femboy_socks",
            () -> new ArmorItem(ModArmorMaterials.FEMBOY, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)) {
                private static final UUID HEALTH_UUID = UUID.fromString("7b3f0c2a-1e2d-4a5b-9c8d-2f6e1a0b5c77");
                private static final UUID STRENGTH_UUID = UUID.fromString("9d1e4f7b-3c6a-4b8d-a2e5-7f0c3d1b8a44");

                @Override
                public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
                    if (slot != EquipmentSlot.LEGS) {
                        return super.getDefaultAttributeModifiers(slot);
                    }
                    ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                    builder.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_UUID,
                            "Femboy socks health boost", 1000.0, AttributeModifier.Operation.ADDITION));
                    builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(STRENGTH_UUID,
                            "Femboy socks strength boost", 100.0, AttributeModifier.Operation.ADDITION));
                    return builder.build();
                }
            });

    public static final RegistryObject<Item> DAVID_SPAWN_EGG = ITEMS.register("david_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DAVID, 0x14141f, 0xcc0000, new Item.Properties()));

    public static final RegistryObject<Item> NULL_SPAWN_EGG = ITEMS.register("null_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.NULL, 0x000000, 0xffffff, new Item.Properties()));

    public static final RegistryObject<Item> BUTTON_MONSTER_SPAWN_EGG = ITEMS.register("button_monster_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BUTTON_MONSTER, 0x555555, 0xff2222, new Item.Properties()));

    public static final RegistryObject<Item> MART_SPAWN_EGG = ITEMS.register("mart_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MART, 0x3a2b1d, 0xffcc00, new Item.Properties()));

    public static final RegistryObject<Item> SOUNDPAD_SPAWN_EGG = ITEMS.register("soundpad_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SOUNDPAD, 0x222244, 0x66eeff, new Item.Properties()));

    public static final RegistryObject<Item> EVIL_FISH_SPAWN_EGG = ITEMS.register("evil_fish_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.EVIL_FISH, 0x880000, 0xffff00, new Item.Properties()));
}
