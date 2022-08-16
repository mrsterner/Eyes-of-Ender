package dev.mrsterner.eyesofender.client.renderer.block;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.mrsterner.eyesofender.EyesOfEnder;
import dev.mrsterner.eyesofender.client.EOESpriteIdentifiers;
import dev.mrsterner.eyesofender.common.block.CoffinBlock;
import dev.mrsterner.eyesofender.common.registry.EOEObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import static net.minecraft.block.BedBlock.PART;

public class CoffinBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>{
    public static final EntityModelLayer COFFIN_LAYER = new EntityModelLayer(EyesOfEnder.id("coffin"), "main");
    public static float openProgress = 0;
    private final ModelPart top;
    private final ModelPart base;
    private final ModelPart bone;

    public CoffinBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(COFFIN_LAYER);
        this.bone = modelPart.getChild("bone");
        this.top = bone.getChild("top");
        this.base = bone.getChild("base");
    }



    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData partdefinition = modelData.getRoot();

        ModelPartData bone = partdefinition.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 14.0F, -22.0F));

        ModelPartData top = bone.addChild("top", ModelPartBuilder.create().uv(0, 35).cuboid(0.0F, -1.0F, -15.0F, 14.0F, 1.0F, 30.0F, new Dilation(0.0F))
                .uv(64, 0).cuboid(1.0F, -2.0F, -14.0F, 12.0F, 1.0F, 28.0F, new Dilation(0.0F))
                .uv(58, 35).cuboid(1.0F, -0.5F, -14.0F, 12.0F, 1.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, -1.0F, 14.0F));

        ModelPartData base = bone.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, 5.0F, 0.0F, 16.0F, 3.0F, 32.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-2.0F, -2.0F, 1.5F, 1.0F, 7.0F, 29.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-15.0F, -2.0F, 1.5F, 1.0F, 7.0F, 29.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.5F, -2.0F, 1.0F, 13.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-14.5F, -2.0F, 30.0F, 13.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(61, 64).cuboid(-13.5F, 4.0F, 2.5F, 11.0F, 1.0F, 27.0F, new Dilation(0.0F))
                .uv(31, 92).cuboid(-2.5F, -2.5F, 1.5F, 1.0F, 7.0F, 29.0F, new Dilation(0.0F))
                .uv(31, 92).cuboid(-14.5F, -2.5F, 1.5F, 1.0F, 7.0F, 29.0F, new Dilation(0.0F))
                .uv(0, 35).cuboid(-13.5F, -2.5F, 1.5F, 11.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 35).cuboid(-13.5F, -2.5F, 29.5F, 11.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(64, 29).cuboid(-16.0F, -3.0F, 0.0F, 16.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(64, 29).cuboid(-16.0F, -3.0F, 30.0F, 16.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-2.0F, -3.0F, 2.0F, 2.0F, 1.0F, 28.0F, new Dilation(0.0F))
                .uv(0, 66).cuboid(-16.0F, -3.0F, 2.0F, 2.0F, 1.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, 2.0F, -2.0F));

        ModelPartData detailsNorth = base.addChild("detailsNorth", ModelPartBuilder.create().uv(0, 15).mirrored().cuboid(-14.5F, -3.0F, 6.5F, 13.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(5, 22).cuboid(-5.5F, -5.0F, 6.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(5, 22).mirrored().cuboid(-12.5F, -5.0F, 6.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(24, 22).cuboid(-1.5F, -7.0F, 6.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(27, 17).cuboid(-8.5F, -1.0F, 6.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 26).cuboid(-8.5F, -6.0F, 6.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 22).cuboid(-15.5F, -7.0F, 6.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-14.5F, -7.0F, 6.5F, 13.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, -6.0F));

        ModelPartData detailsSouth = base.addChild("detailsSouth", ModelPartBuilder.create().uv(0, 15).cuboid(-14.5F, -3.0F, 6.5F, 13.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(5, 22).cuboid(-5.5F, -5.0F, 6.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(5, 22).cuboid(-12.5F, -5.0F, 6.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 22).cuboid(-1.5F, -7.0F, 6.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(27, 13).cuboid(-8.5F, -1.0F, 6.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 26).cuboid(-8.5F, -6.0F, 6.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 22).cuboid(-15.5F, -7.0F, 6.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 20).cuboid(-14.5F, -7.0F, 6.5F, 13.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 24.0F));

        ModelPartData detailWest = base.addChild("detailWest", ModelPartBuilder.create().uv(16, 36).cuboid(-1.5F, -7.0F, 7.5F, 1.0F, 1.0F, 29.0F, new Dilation(0.0F))
                .uv(3, 3).cuboid(-1.5F, -3.0F, 7.5F, 1.0F, 2.0F, 29.0F, new Dilation(0.0F))
                .uv(0, 22).cuboid(-1.5F, -6.0F, 20.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(18, 26).cuboid(-1.5F, -1.0F, 20.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(24, 8).cuboid(-1.5F, -6.0F, 29.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(27, 11).cuboid(-1.5F, -1.0F, 29.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 8).cuboid(-1.5F, -6.0F, 13.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(27, 7).cuboid(-1.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, -6.0F));

        ModelPartData detailEast = base.addChild("detailEast", ModelPartBuilder.create().uv(16, 36).cuboid(-1.5F, -7.0F, 7.5F, 1.0F, 1.0F, 29.0F, new Dilation(0.0F))
                .uv(3, 3).mirrored().cuboid(-1.5F, -3.0F, 7.5F, 1.0F, 2.0F, 29.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 22).cuboid(-1.5F, -6.0F, 20.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(15, 24).cuboid(-1.5F, -1.0F, 20.5F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
                .uv(24, 8).mirrored().cuboid(-1.5F, -6.0F, 29.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(24, 12).cuboid(-1.5F, -1.0F, 29.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 8).mirrored().cuboid(-1.5F, -6.0F, 13.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(24, 12).cuboid(-1.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-14.0F, 5.0F, -6.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }


    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : EOEObjects.COFFIN.getDefaultState().with(CoffinBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();
        if (block instanceof CoffinBlock) {
            if (world != null && world.isClient()){
                matrices.push();
                float f = (blockState.get(ChestBlock.FACING)).asRotation();
                matrices.translate(0.5, 0.5, 0.5);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-f));
                matrices.translate(-0.5, -0.5, -0.5);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
                matrices.translate(0.5,-1.5,-0.5);
                VertexConsumer vertexConsumer = EOESpriteIdentifiers.COFFIN_SPRITE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
                boolean g = blockState.get(CoffinBlock.OPEN);
                this.render(matrices, vertexConsumer, this.base, this.top, g, light, overlay, blockState, tickDelta);
                matrices.pop();
            }
        }
    }

    public float degToRad(float f){
        return f * (float) Math.PI / 180F;
    }

    public double easeInOut(float x){
        int v = 45;
        int c = -v/2;
        int g = -2;
        int s = 16;
        return (v/(1 + Math.pow(10, g*(c + x)/s)));
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, ModelPart base, ModelPart lid, boolean openFactor, int light, int overlay, BlockState blockState, float tickDelta) {
        if(blockState.get(PART) == BedPart.FOOT){
            if(openFactor && openProgress < 45.0F){
                openProgress = openProgress + 1F;
            }else if(!openFactor && openProgress > 0.0F){
                openProgress = openProgress - 1F;
            }
            lid.roll = -degToRad((float)easeInOut(openProgress));
            bone.render(matrices, vertices, light, overlay);
        }
    }
}