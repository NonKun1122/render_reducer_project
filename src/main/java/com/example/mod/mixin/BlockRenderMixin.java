package com.example.mod.mixin;

import com.example.mod.Config;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
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
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // ตรวจสอบระยะห่างระหว่างผู้เล่นกับบล็อก
            double distanceSq = client.player.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ());
            double maxDistanceSq = Config.renderDistance * Config.renderDistance;

            // ถ้าไกลเกินระยะที่กำหนด ให้ยกเลิกการเรนเดอร์
            if (distanceSq > maxDistanceSq) {
                ci.cancel();
                return;
            }

            // ใช้โอกาสในการลดการเรนเดอร์เพื่อเพิ่มความลื่นไหล (สำหรับบล็อกที่อยู่ในระยะ)
            if (Config.renderReductionChance > 0 && this.internalRandom.nextFloat() < Config.renderReductionChance) {
                ci.cancel();
            }
        }
    }
}
