package com.omega.horror.registry;

import com.omega.horror.registry.ModSounds;

import com.omega.horror.OmegaHorrorMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public enum ModArmorMaterials implements ArmorMaterial {
    FEMBOY("femboy", 100, new int[]{3, 6, 8, 3}, 30, ModSounds.ARMOR_EQUIP_NETHERITE, 5.0f, 0.2f,
            Ingredient.of(Items.PINK_WOOL));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] defense;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier, int[] defense, int enchantmentValue,
                       SoundEvent equipSound, float toughness, float knockbackResistance,
                       Ingredient repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.defense = defense;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return durabilityMultiplier * switch (type) {
            case HELMET -> 11;
            case CHESTPLATE -> 16;
            case LEGGINGS -> 15;
            case BOOTS -> 13;
        };
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return defense[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public String getName() {
        return OmegaHorrorMod.MODID + ":" + name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
