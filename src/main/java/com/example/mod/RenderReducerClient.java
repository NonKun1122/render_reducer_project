package com.example.mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RenderReducerClient implements ClientModInitializer {
    private static KeyBinding configKeyBinding;

    @Override
    public void onInitializeClient() {
        Config.load();

        // ลงทะเบียนปุ่มลัด (ค่าเริ่มต้นคือตัว O)
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.renderreducer.config", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_O, 
                "category.renderreducer"
        ));

        // ตรวจสอบการกดปุ่มในแต่ละ Tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.wasPressed()) {
                client.setScreen(new RenderReducerConfigScreen(client.currentScreen));
            }
        });
    }
}
