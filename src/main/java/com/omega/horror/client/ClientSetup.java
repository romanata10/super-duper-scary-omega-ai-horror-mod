package com.omega.horror.client;

import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.client.model.AirplaneModel;
import com.omega.horror.client.renderer.AirplaneRenderer;
import com.omega.horror.client.renderer.HumanoidMonsterRenderer;
import com.omega.horror.client.renderer.TornadoRenderer;
import com.omega.horror.registry.ModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ClientSetup {
    public static final ModelLayerLocation AIRPLANE_LAYER =
            new ModelLayerLocation(new ResourceLocation(OmegaHorrorMod.MODID, "airplane"), "main");

    public static void register(IEventBus bus) {
        bus.addListener(ClientSetup::registerLayerDefinitions);
        bus.addListener(ClientSetup::registerRenderers);
    }

    private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AIRPLANE_LAYER, AirplaneModel::createBodyLayer);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.DAVID.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.NULL.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.BUTTON_MONSTER.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.MART.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.SOUNDPAD.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.EVIL_FISH.get(), HumanoidMonsterRenderer::new);
        event.registerEntityRenderer(ModEntities.TORNADO.get(), TornadoRenderer::new);
        event.registerEntityRenderer(ModEntities.AIRPLANE.get(), AirplaneRenderer::new);
    }
}
