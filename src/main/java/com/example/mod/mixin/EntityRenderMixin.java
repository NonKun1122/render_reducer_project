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

    // ทำให้ตัวแปรควบคุมการซ่อนเอนทิตีเป็น private static final เพื่อหลีกเลี่ยงปัญหา Mixin apply failed
    // หากต้องการให้ปรับค่าได้ ควรใช้ระบบ Config ของ Fabric (เช่น Cloth Config API)
    private static final boolean hideHostileEntities = true;
    private static final boolean hidePassiveEntities = true;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(
        T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci
    ) {
        // ซ่อนเอนทิตีตามการตั้งค่า
        if ((hideHostileEntities && entity instanceof HostileEntity) ||
            (hidePassiveEntities && entity instanceof PassiveEntity)) {
            ci.cancel();
        }
    }
}
