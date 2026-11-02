package com.omega.horror.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.omega.horror.OmegaHorrorMod;
import com.omega.horror.client.ClientSetup;
import com.omega.horror.client.model.AirplaneModel;
import com.omega.horror.entity.AirplaneEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AirplaneRenderer extends EntityRenderer<AirplaneEntity> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(OmegaHorrorMod.MODID, "textures/entity/airplane.png");

    private final AirplaneModel model;

    public AirplaneRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8f;
        this.model = new AirplaneModel(context.bakeLayer(ClientSetup.AIRPLANE_LAYER));
    }

    @Override
    public void render(AirplaneEntity entity, float entityYaw, float partialTicks, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entityYaw));
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        this.model.setupAnim(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY,
                1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(AirplaneEntity entity) {
        return TEXTURE;
    }
}
