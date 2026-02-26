package com.example.mod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelRenderer.class)
public class BlockRenderMixin {
    private final Random internalRandom = Random.create();

    // ทำให้ renderReductionChance เป็น private static final เพื่อหลีกเลี่ยงปัญหา Mixin apply failed
    // หากต้องการให้ปรับค่าได้ ควรใช้ระบบ Config ของ Fabric (เช่น Cloth Config API)
    private static final float renderReductionChance = 0.5f; 

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(
        BlockRenderView world,
        BakedModel model,
        BlockState state,
        BlockPos pos,
        MatrixStack matrices,
        VertexConsumer vertexConsumer,
        boolean cull,
        Random random,
        long seed,
        int overlay,
        CallbackInfo ci
    ) {
        if (this.internalRandom.nextFloat() < renderReductionChance) {
            ci.cancel();
        }
    }
}
