package com.omega.horror.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

/**
 * Собственные SoundEvent-обёртки над ванильными звуками.
 * (В 1.20.1 константы SoundEvents имеют смешанные типы SoundEvent/Holder,
 * поэтому используем createVariableRangeEvent — всегда SoundEvent.)
 */
public class ModSounds {
    public static final SoundEvent GENERIC_DRINK = vanilla("generic_drink");
    public static final SoundEvent PLAYER_LEVELUP = vanilla("entity.player.levelup");
    public static final SoundEvent ZOMBIE_AMBIENT = vanilla("entity.zombie.ambient");
    public static final SoundEvent ZOMBIE_HURT = vanilla("entity.zombie.hurt");
    public static final SoundEvent ZOMBIE_DEATH = vanilla("entity.zombie.death");
    public static final SoundEvent WARDEN_SONIC_BOOM = vanilla("entity.warden.sonic_boom");
    public static final SoundEvent WARDEN_HEARTBEAT = vanilla("entity.warden.heartbeat");
    public static final SoundEvent WARDEN_HURT = vanilla("entity.warden.hurt");
    public static final SoundEvent WARDEN_DEATH = vanilla("entity.warden.death");
    public static final SoundEvent STONE_BUTTON_CLICK_ON = vanilla("block.stone_button.click_on");
    public static final SoundEvent STONE_BUTTON_CLICK_OFF = vanilla("block.stone_button.click_off");
    public static final SoundEvent ANVIL_LAND = vanilla("block.anvil.land");
    public static final SoundEvent GENERIC_EXPLODE = vanilla("entity.generic.explode");
    public static final SoundEvent CHEST_OPEN = vanilla("block.chest.open");
    public static final SoundEvent WITHER_SPAWN = vanilla("entity.wither.spawn");
    public static final SoundEvent VILLAGER_AMBIENT = vanilla("entity.villager.ambient");
    public static final SoundEvent VILLAGER_HURT = vanilla("entity.villager.hurt");
    public static final SoundEvent VILLAGER_DEATH = vanilla("entity.villager.death");
    public static final SoundEvent ENDERMAN_STARE = vanilla("entity.enderman.stare");
    public static final SoundEvent ENDERMAN_SCREAM = vanilla("entity.enderman.scream");
    public static final SoundEvent CREEPER_PRIMED = vanilla("entity.creeper.primed");
    public static final SoundEvent GHAST_SCREAM = vanilla("entity.ghast.scream");
    public static final SoundEvent SKELETON_AMBIENT = vanilla("entity.skeleton.ambient");
    public static final SoundEvent BLAZE_AMBIENT = vanilla("entity.blaze.ambient");
    public static final SoundEvent AMBIENT_CAVE = vanilla("ambient.cave");
    public static final SoundEvent NOTE_BLOCK_BASS = vanilla("block.note_block.bass");
    public static final SoundEvent NOTE_BLOCK_PLING = vanilla("block.note_block.pling");
    public static final SoundEvent NOTE_BLOCK_DIDGERIDOO = vanilla("block.note_block.didgeridoo");
    public static final SoundEvent DOLPHIN_AMBIENT = vanilla("entity.dolphin.ambient");
    public static final SoundEvent GENERIC_SWIM = vanilla("entity.generic.swim");
    public static final SoundEvent COD_AMBIENT = vanilla("entity.cod.ambient");
    public static final SoundEvent COD_HURT = vanilla("entity.cod.hurt");
    public static final SoundEvent COD_DEATH = vanilla("entity.cod.death");
    public static final SoundEvent EXPERIENCE_ORB_PICKUP = vanilla("entity.experience_orb.pickup");
    public static final SoundEvent ARMOR_EQUIP_NETHERITE = vanilla("item.armor.equip_netherite");
    public static final SoundEvent BRUSH_GENERIC = vanilla("item.brush.generic");

    private static SoundEvent vanilla(String path) {
        return SoundEvent.createVariableRangeEvent(new ResourceLocation("minecraft", path));
    }
}
