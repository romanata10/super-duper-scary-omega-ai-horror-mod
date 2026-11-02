package com.omega.horror.client.renderer;

import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.entity.TornadoEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TornadoRenderer extends EntityRenderer<TornadoEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(OmegaHorrorMod.MODID, "textures/entity/tornado.png");

    public TornadoRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(TornadoEntity entity) {
        return TEXTURE;
    }
}
