package com.example.mod.mixin;

import com.example.mod.Config;
import me.jellysquid.mods.sodium.client.gui.SodiumVideoOptionsScreen;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SodiumVideoOptionsScreen.class)
public class SodiumVideoOptionsMixin {
    @Shadow(remap = false)
    @Final
    private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        // สร้างกลุ่มตัวเลือกสำหรับ Render Reducer
        OptionGroup.Builder groupBuilder = OptionGroup.createBuilder();
        groupBuilder.setName(Text.literal("Render Reducer Settings"));

        // 1. ระยะการเรนเดอร์ (Slider)
        groupBuilder.add(OptionImpl.createBuilder(Double.class, new MinecraftOptionsStorage())
                .setName(Text.literal("Custom Render Distance"))
                .setTooltip(Text.literal("Set the maximum distance for rendering blocks and entities."))
                .setControl(opt -> new SliderControl(opt, 16, 256, 8, value -> Text.literal(value + " blocks")))
                .setBinding((opt, value) -> { Config.renderDistance = value; Config.save(); },
                            opt -> Config.renderDistance)
                .build());

        // 2. โอกาสในการลดการเรนเดอร์ (Slider %)
        groupBuilder.add(OptionImpl.createBuilder(Float.class, new MinecraftOptionsStorage())
                .setName(Text.literal("Render Reduction Chance"))
                .setTooltip(Text.literal("Chance to skip rendering a block to improve FPS."))
                .setControl(opt -> new SliderControl(opt, 0, 100, 5, value -> Text.literal(value + "%")))
                .setBinding((opt, value) -> { Config.renderReductionChance = value / 100f; Config.save(); },
                            opt -> (float)(Config.renderReductionChance * 100))
                .build());

        // 3. เปิด/ปิด การซ่อนไอเทมบนพื้น (Checkbox)
        groupBuilder.add(OptionImpl.createBuilder(Boolean.class, new MinecraftOptionsStorage())
                .setName(Text.literal("Reduce Dropped Items"))
                .setTooltip(Text.literal("Hide items on the ground when far away."))
                .setControl(TickBoxControl::new)
                .setBinding((opt, value) -> { Config.reduceDroppedItemRendering = value; Config.save(); },
                            opt -> Config.reduceDroppedItemRendering)
                .build());

        // เพิ่มหน้าการตั้งค่าใหม่เข้าไปใน Sodium
        pages.add(new OptionPage(Text.literal("Render Reducer"), List.of(groupBuilder.build())));
    }
}
