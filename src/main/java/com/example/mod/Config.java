package com.example.mod;

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
}
