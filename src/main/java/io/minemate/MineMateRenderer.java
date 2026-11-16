
package io.minemate;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

public class MineMateRenderer extends MobRenderer<
        MineMateEntity,
        LivingEntityRenderState,
        MineMateModel
> {

    private static final Identifier TEXTURE =
            Identifier.fromNamespaceAndPath(
                    "minemate",
                    "textures/entity/minemate.png"
            );

    public MineMateRenderer(EntityRendererProvider.Context context) {
        super(
                context,
                new MineMateModel(
                        context.bakeLayer(MineMateClient.MINE_MATE_LAYER)
                ),
                0.5f
        );
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return TEXTURE;
    }
}

