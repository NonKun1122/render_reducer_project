package com.example.mod;

import java.io.*;
import java.util.Properties;

public class Config {
    // ระยะการเรนเดอร์ (บล็อก)
    public static double renderDistance = 64.0;
    
    // โอกาสในการลดการเรนเดอร์บล็อก (0.0 - 1.0)
    public static float renderReductionChance = 0.2f;
    
    // ตั้งค่าการซ่อนเอนทิตี
    public static boolean hideHostileEntities = false;
    public static boolean hidePassiveEntities = false;
    
    // ลดการเรนเดอร์ไอเทมที่ตกพื้น
    public static boolean reduceDroppedItemRendering = true;
    public static double itemRenderDistance = 16.0;

    private static final File CONFIG_FILE = new File("config/render_reducer.properties");

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            save();
            return;
        }
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(input);
            renderDistance = Double.parseDouble(prop.getProperty("renderDistance", "64.0"));
            renderReductionChance = Float.parseFloat(prop.getProperty("renderReductionChance", "0.2"));
            hideHostileEntities = Boolean.parseBoolean(prop.getProperty("hideHostileEntities", "false"));
            hidePassiveEntities = Boolean.parseBoolean(prop.getProperty("hidePassiveEntities", "false"));
            reduceDroppedItemRendering = Boolean.parseBoolean(prop.getProperty("reduceDroppedItemRendering", "true"));
            itemRenderDistance = Double.parseDouble(prop.getProperty("itemRenderDistance", "16.0"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile().mkdirs();
            try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
                Properties prop = new Properties();
                prop.setProperty("renderDistance", String.valueOf(renderDistance));
                prop.setProperty("renderReductionChance", String.valueOf(renderReductionChance));
                prop.setProperty("hideHostileEntities", String.valueOf(hideHostileEntities));
                prop.setProperty("hidePassiveEntities", String.valueOf(hidePassiveEntities));
                prop.setProperty("reduceDroppedItemRendering", String.valueOf(reduceDroppedItemRendering));
                prop.setProperty("itemRenderDistance", String.valueOf(itemRenderDistance));
                prop.store(output, "Render Reducer Config");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
