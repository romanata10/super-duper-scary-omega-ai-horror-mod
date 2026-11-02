package com.omega.horror.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.omega.horror.entity.AirplaneEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class AirplaneModel extends EntityModel<AirplaneEntity> {
    private final ModelPart root;

    public AirplaneModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();
        part.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0f, -1.0f, -6.0f, 2, 2, 12),
                PartPose.ZERO);
        part.addOrReplaceChild("wing",
                CubeListBuilder.create().texOffs(0, 14).addBox(-6.0f, -0.5f, -2.0f, 12, 1, 4),
                PartPose.ZERO);
        part.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5f, 0.0f, 5.0f, 1, 3, 1),
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(AirplaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                          float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                               int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
