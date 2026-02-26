package com.example.mod;

import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * คลาสจำลองสำหรับการเชื่อมต่อกับ Sodium Options API
 * ในโปรเจกต์จริง คุณต้องเพิ่ม dependency: modImplementation "me.jellysquid.mods.sodium:sodium-fabric:${sodium_version}"
 * และใช้ Sodium's Option API ในการลงทะเบียน
 */
public class SodiumConfigIntegration {
    // นี่คือตัวอย่างการโครงสร้างที่ Sodium ใช้ในการเพิ่ม Option
    // เนื่องจากเราไม่สามารถรัน Sodium ใน sandbox ได้ ผมจะเขียนโค้ดที่เป็นโครงสร้างมาตรฐานให้คุณนำไปใช้
    
    /*
    public static void registerOptions(OptionGroup.Builder groups) {
        groups.add(OptionImpl.createBuilder(Double.class, storage)
                .setName(Text.translatable("render_reducer.options.distance.name"))
                .setTooltip(Text.translatable("render_reducer.options.distance.tooltip"))
                .setControl(s -> new SliderControl(s, 16, 256, 8, ControlValueFormatter.number()))
                .setBinding((options, value) -> { Config.renderDistance = value; Config.save(); },
                            options -> Config.renderDistance)
                .build());
        
        groups.add(OptionImpl.createBuilder(Boolean.class, storage)
                .setName(Text.translatable("render_reducer.options.items.name"))
                .setTooltip(Text.translatable("render_reducer.options.items.tooltip"))
                .setControl(TickBoxControl::new)
                .setBinding((options, value) -> { Config.reduceDroppedItemRendering = value; Config.save(); },
                            options -> Config.reduceDroppedItemRendering)
                .build());
    }
    */
}
