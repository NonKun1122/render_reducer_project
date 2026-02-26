package com.example.mod.mixin;

import com.example.mod.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        // ถ้าไอเทมอยู่ไกลเกินไป เราอาจจะลดความถี่ในการคำนวณฟิสิกส์ (Optional - เพิ่มความลื่นไหล)
        // ในที่นี้เน้นไปที่การเรนเดอร์ใน EntityRenderMixin แล้ว
    }
}
