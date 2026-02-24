package com.example.mod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Random;

@Mixin(BlockModelRenderer.class)
public class BlockRenderMixin {
    private final Random random = new Random();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(BlockRenderView world, net.minecraft.client.render.model.BakedModel model, BlockState state, BlockPos pos, MatrixStack matrices, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay, CallbackInfo ci) {
        // ลดการเรนเดอร์บล็อกลง 50% (สุ่มไม่เรนเดอร์) เพื่อลดภาระการประมวลผล
        // หมายเหตุ: นี่เป็นตัวอย่างการลดการเรนเดอร์แบบสุ่ม ซึ่งจะทำให้บล็อกบางส่วนหายไป
        if (this.random.nextFloat() < 0.5f) {
            ci.cancel();
        }
    }
}
