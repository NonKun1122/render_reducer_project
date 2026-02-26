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
    // ใช้ Random ของ Minecraft เพื่อความเสถียรและตรงตามมาตรฐาน 1.20.1
    private final Random internalRandom = Random.create();

    // เพิ่มตัวแปรสำหรับควบคุมอัตราการลดการเรนเดอร์ (ค่าเริ่มต้น 0.5f คือ 50%)
    // สามารถปรับค่านี้ได้ผ่านไฟล์คอนฟิกูเรชันของม็อด (ต้องมีการสร้างระบบคอนฟิกูเรชันเพิ่มเติม)
    private static float renderReductionChance = 0.5f; 

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
        // ลดการเรนเดอร์บล็อกตามค่า renderReductionChance ที่กำหนด
        if (this.internalRandom.nextFloat() < renderReductionChance) {
            ci.cancel();
        }
    }

    // เมธอดสำหรับตั้งค่าอัตราการลดการเรนเดอร์ (สามารถเรียกใช้จากภายนอกได้)
    public static void setRenderReductionChance(float chance) {
        // ตรวจสอบให้แน่ใจว่าค่าอยู่ในช่วง 0.0f ถึง 1.0f
        if (chance >= 0.0f && chance <= 1.0f) {
            renderReductionChance = chance;
        } else {
            System.err.println("Invalid render reduction chance: " + chance + ". Must be between 0.0f and 1.0f.");
        }
    }
}
