package com.omega.horror.client.renderer;

import com.omega.horror.OmegaHorrorMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.registries.ForgeRegistries;

public class HumanoidMonsterRenderer<T extends Mob> extends MobRenderer<T, HumanoidModel<T>> {
    public HumanoidMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        String name = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).getPath();
        return new ResourceLocation(OmegaHorrorMod.MODID, "textures/entity/" + name + ".png");
    }
}
