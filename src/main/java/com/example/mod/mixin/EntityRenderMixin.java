package com.example.mod.mixin;

import com.example.mod.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRenderMixin<T extends Entity> {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(
        T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci
    ) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // 1. จัดการไอเทมที่ตกพื้น (Dropped Items)
        if (entity instanceof ItemEntity) {
            if (Config.reduceDroppedItemRendering) {
                double distanceSq = client.player.squaredDistanceTo(entity);
                if (distanceSq > (Config.itemRenderDistance * Config.itemRenderDistance)) {
                    ci.cancel();
                    return;
                }
            }
        }

        // 2. จัดการเอนทิตีอื่นๆ (Hostile/Passive)
        if ((Config.hideHostileEntities && entity instanceof HostileEntity) ||
            (Config.hidePassiveEntities && entity instanceof PassiveEntity)) {
            ci.cancel();
            return;
        }
        
        // 3. ปรับระยะการมองเห็นเอนทิตีทั่วไป (เพื่อความลื่นไหล)
        double distanceSq = client.player.squaredDistanceTo(entity);
        if (distanceSq > (Config.renderDistance * Config.renderDistance)) {
            ci.cancel();
        }
    }
}
