package com.example.mod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedModel; // เพิ่ม Import เพื่อความสะอาดของโค้ด
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random; // เปลี่ยนจาก java.util.Random เป็นของ Minecraft
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockModelRenderer.class)
public class BlockRenderMixin {
    // ใช้ Random ของ Minecraft เพื่อความเสถียรและตรงตามมาตรฐาน 1.20.1
    private final Random internalRandom = Random.create(); 

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRenderBlock(
        BlockRenderView world, 
        BakedModel model, 
        BlockState state, 
        BlockPos pos, 
        MatrixStack matrices, 
        VertexConsumer vertexConsumer, 
        boolean cull, 
        Random random, // แก้ไข Type ตรงนี้ให้เป็น net.minecraft.util.math.random.Random [cite: 7]
        long seed, 
        int overlay, 
        CallbackInfo ci
    ) {
        // ลดการเรนเดอร์บล็อกลง 50%
        if (this.internalRandom.nextFloat() < 0.5f) {
            ci.cancel();
        }
    }
}
