package io.minemate;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.Identifier;

public class MineMateClient implements ClientModInitializer {

    public static final ModelLayerLocation MINE_MATE_LAYER =
            new ModelLayerLocation(
                    Identifier.fromNamespaceAndPath("minemate", "minemate"),
                    "main"
            );

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(
                MINE_MATE_LAYER,
                MineMateModel::createBodyLayer
        );

        EntityRenderers.register(
                MineMate.MINE_MATE,
                MineMateRenderer::new
        );
    }
}
