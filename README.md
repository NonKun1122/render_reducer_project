# Render Reducer Mod (Fabric 1.20.1)

มอดนี้ถูกออกแบบมาเพื่อเพิ่มประสิทธิภาพการทำงานของเกมโดยการลดการเรนเดอร์พื้นผิวบล็อกและซ่อนเอนทิตี (มอนสเตอร์และสัตว์)

## ฟีเจอร์หลัก
1. **Entity Hiding**: ซ่อนการเรนเดอร์ของมอนสเตอร์ (Hostile Entities) และสัตว์ (Passive Entities) ทั้งหมดในเกม
2. **Block Render Reduction**: ลดการเรนเดอร์พื้นผิวบล็อกลง 50% (สุ่ม) เพื่อลดภาระของ GPU

## โครงสร้างโปรเจกต์
- `src/main/java/com/example/mod/mixin/EntityRenderMixin.java`: จัดการการซ่อนเอนทิตี
- `src/main/java/com/example/mod/mixin/BlockRenderMixin.java`: จัดการการลดการเรนเดอร์บล็อก
- `src/main/resources/fabric.mod.json`: ไฟล์กำหนดค่ามอดสำหรับ Fabric
- `src/main/resources/render_reducer.mixins.json`: ไฟล์กำหนดค่า Mixin

## วิธีการ Build
1. ติดตั้ง [JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
2. ใช้ [Fabric Example Mod Template](https://github.com/FabricMC/fabric-example-mod) เป็นโครงสร้างหลัก
3. คัดลอกไฟล์ในโฟลเดอร์ `src` ไปวางในโปรเจกต์เทมเพลต
4. รันคำสั่ง `./gradlew build` ใน terminal
5. ไฟล์มอดจะอยู่ที่ `build/libs/render_reducer-1.0.0.jar`

## คำเตือน
- การลดการเรนเดอร์บล็อกแบบสุ่มอาจทำให้โลกดู "โปร่งใส" หรือมีรูโหว่ ซึ่งเป็นผลมาจากการลดภาระการเรนเดอร์ตามที่ต้องการ
- มอดนี้ทำงานเฉพาะฝั่ง Client เท่านั้น
