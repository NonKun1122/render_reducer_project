package com.example.mod.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRenderMixin<T extends Entity> {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // ซ่อนมอนสเตอร์ (HostileEntity) และสัตว์ (PassiveEntity)
        if (entity instanceof HostileEntity || entity instanceof PassiveEntity) {
            ci.cancel();
        }
    }
}
