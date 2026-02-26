package com.example.mod;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.SliderWidget;

public class RenderReducerConfigScreen extends Screen {
    private final Screen parent;

    public RenderReducerConfigScreen(Screen parent) {
        super(Text.literal("Render Reducer Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int y = this.height / 4;

        // 1. Slider สำหรับ Render Distance
        this.addDrawableChild(new SliderWidget(this.width / 2 - 100, y, 200, 20, 
                Text.literal("Render Distance: " + (int)Config.renderDistance), Config.renderDistance / 256.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Render Distance: " + (int)(this.value * 256)));
            }
            @Override
            protected void applyValue() {
                Config.renderDistance = this.value * 256;
                Config.save();
            }
        });

        // 2. Slider สำหรับ Reduction Chance
        this.addDrawableChild(new SliderWidget(this.width / 2 - 100, y + 30, 200, 20, 
                Text.literal("Reduction Chance: " + (int)(Config.renderReductionChance * 100) + "%"), Config.renderReductionChance) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Reduction Chance: " + (int)(this.value * 100) + "%"));
            }
            @Override
            protected void applyValue() {
                Config.renderReductionChance = (float)this.value;
                Config.save();
            }
        });

        // 3. ปุ่มเปิด/ปิด Item Rendering
        this.addDrawableChild(CyclingButtonWidget.onOffBuilder(Config.reduceDroppedItemRendering)
                .build(this.width / 2 - 100, y + 60, 200, 20, Text.literal("Reduce Item Rendering"), (button, value) -> {
                    Config.reduceDroppedItemRendering = value;
                    Config.save();
                }));

        // ปุ่ม Done
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), (button) -> {
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, this.height - 40, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }
}
