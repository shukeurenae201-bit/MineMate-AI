
package io.minemate;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;

public class MineMateModel extends EntityModel<LivingEntityRenderState> {

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public MineMateModel(ModelPart root) {
        super(root);

        head = root.getChild("head");
        body = root.getChild("body");
        rightArm = root.getChild("right_arm");
        leftArm = root.getChild("left_arm");
        rightLeg = root.getChild("right_leg");
        leftLeg = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4, -8, -4, 8, 8, 8),
            PartPose.ZERO
        );

        root.addOrReplaceChild(
            "body",
            CubeListBuilder.create()
                .texOffs(16, 16)
                .addBox(-4, 0, -2, 8, 12, 4),
            PartPose.ZERO
        );

        root.addOrReplaceChild(
            "right_arm",
            CubeListBuilder.create()
                .texOffs(40, 16)
                .addBox(-3, -2, -2, 4, 12, 4),
            PartPose.offset(-5, 2, 0)
        );

        root.addOrReplaceChild(
            "left_arm",
            CubeListBuilder.create()
                .texOffs(32, 48)
                .addBox(-1, -2, -2, 4, 12, 4),
            PartPose.offset(5, 2, 0)
        );

        root.addOrReplaceChild(
            "right_leg",
            CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-2, 0, -2, 4, 12, 4),
            PartPose.offset(-1.9f, 12, 0)
        );

        root.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create()
                .texOffs(16, 48)
                .addBox(-2, 0, -2, 4, 12, 4),
            PartPose.offset(1.9f, 12, 0)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntityRenderState state) {
        super.setupAnim(state);

        // Keep the head facing forward
        head.xRot = 0;
        head.yRot = 0;

        float speed = state.walkAnimationSpeed;
        float position = state.walkAnimationPos;

        rightArm.xRot = Mth.cos(position * 0.6662f + Mth.PI) * 1.4f * speed;
        leftArm.xRot = Mth.cos(position * 0.6662f) * 1.4f * speed;

        rightLeg.xRot = Mth.cos(position * 0.6662f) * 1.4f * speed;
        leftLeg.xRot = Mth.cos(position * 0.6662f + Mth.PI) * 1.4f * speed;
    }
}
