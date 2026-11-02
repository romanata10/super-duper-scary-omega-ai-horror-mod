package com.omega.horror.registry;

import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.entity.AirplaneEntity;
import com.omega.horror.entity.ButtonMonsterEntity;
import com.omega.horror.entity.DavidEntity;
import com.omega.horror.entity.EvilFishEntity;
import com.omega.horror.entity.MartEntity;
import com.omega.horror.entity.NullEntity;
import com.omega.horror.entity.SoundpadEntity;
import com.omega.horror.entity.TornadoEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, OmegaHorrorMod.MODID);

    public static final RegistryObject<EntityType<DavidEntity>> DAVID = ENTITIES.register("david",
            () -> EntityType.Builder.of(DavidEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f).build("david"));

    public static final RegistryObject<EntityType<NullEntity>> NULL = ENTITIES.register("null",
            () -> EntityType.Builder.of(NullEntity::new, MobCategory.MONSTER)
                    .sized(1.4f, 3.8f).build("null"));

    public static final RegistryObject<EntityType<ButtonMonsterEntity>> BUTTON_MONSTER = ENTITIES.register("button_monster",
            () -> EntityType.Builder.of(ButtonMonsterEntity::new, MobCategory.MONSTER)
                    .sized(0.7f, 2.2f).build("button_monster"));

    public static final RegistryObject<EntityType<MartEntity>> MART = ENTITIES.register("mart",
            () -> EntityType.Builder.of(MartEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.95f).build("mart"));

    public static final RegistryObject<EntityType<SoundpadEntity>> SOUNDPAD = ENTITIES.register("soundpad",
            () -> EntityType.Builder.of(SoundpadEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.2f).build("soundpad"));

    public static final RegistryObject<EntityType<EvilFishEntity>> EVIL_FISH = ENTITIES.register("evil_fish",
            () -> EntityType.Builder.of(EvilFishEntity::new, MobCategory.MONSTER)
                    .sized(0.5f, 0.4f).build("evil_fish"));

    public static final RegistryObject<EntityType<TornadoEntity>> TORNADO = ENTITIES.register("tornado",
            () -> EntityType.Builder.of(TornadoEntity::new, MobCategory.MISC)
                    .sized(2.5f, 5.0f).build("tornado"));

    public static final RegistryObject<EntityType<AirplaneEntity>> AIRPLANE = ENTITIES.register("airplane",
            () -> EntityType.Builder.of(AirplaneEntity::new, MobCategory.MISC)
                    .sized(2.0f, 1.0f).build("airplane"));

    public static void registerAttributes(IEventBus bus) {
        bus.addListener(ModEntities::onAttributeCreation);
    }

    private static void onAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(DAVID.get(), DavidEntity.createAttributes().build());
        event.put(NULL.get(), NullEntity.createAttributes().build());
        event.put(BUTTON_MONSTER.get(), ButtonMonsterEntity.createAttributes().build());
        event.put(MART.get(), MartEntity.createAttributes().build());
        event.put(SOUNDPAD.get(), SoundpadEntity.createAttributes().build());
        event.put(EVIL_FISH.get(), EvilFishEntity.createAttributes().build());
    }
}
