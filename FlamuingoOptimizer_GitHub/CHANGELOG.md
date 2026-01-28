# 📜 Change Log - FlamiungoOptimizer

Tüm sürüm güncellemeleri ve yapılan geliştirmeler burada listelenmektedir.

---

## [v1.1.0] - Ultimate V2 (Son Güncelleme) - 2026-01-29

### 🚀 Yeni Özellikler
- **Ultimate HUD Editor:** `Insert` tuşu ile açılan, tüm HUD öğelerini ekranda serbestçe sürükleyip bırakabileceğiniz gelişmiş editör sistemi eklendi.
- **Improved TargetHUD:** 
    - Rakibin giydiği setleri (Kask, Göğüslük vb.) ve elindeki eşyayı gösteren ikonlar eklendi.
    - Rakibin kalan canı artık hem bar hem de yüzdelik (%) olarak görünüyor.
    - "Winning / Losing" durumu algoritması geliştirildi.
- **China Hat V2:** Daha estetik, çift katmanlı ve kendi ekseninde dönen 3D şapka efekti eklendi.
- **Reach Display:** Rakibe kaç bloktan vurduğunuzu anlık hesaplayan yeni HUD modülü eklendi.
- **Keystrokes:** WASD ve Space tuşlarına basışlarınızı ekranda şık bir şekilde gösteren modül eklendi.
- **Smart Totem Counter:** Totem sayacı XP barının hemen üstüne, envanter takibini zorlaştırmayacak bir konuma taşındı.

### 🛡️ PvP & Optimizasyon
- **Durability System:** Zırhların dayanıklılık sayıları artık her zaman görünür (full can olsa bile). Dayanıklılık azaldıkça sayı rengi Beyaz -> Sarı -> Kırmızı şeklinde değişir.
- **Crystal Optimizer Fix:** Kristal kırma hızı ve paket yakalama algoritması optimize edildi.
- **NoRender Improvements:** Patlama partikülleri ve gereksiz efektlerin engellenmesi sayesinde FPS artışı sağlandı.

### 🔧 Teknik Düzeltmeler & Uyumluluk
- **Geniş Sürüm Desteği:** Mod artık Minecraft 1.21.1 ile 1.21.10 arasındaki tüm sürümlerde sorunsuz çalışmaktadır.
- **Build Fix:** `remapJar` aşamasında yaşanan `ClosedFileSystemException` hatası giderildi.
- **API Update:** 1.21.1 Yarn mapping'leri ile tam uyumluluk sağlandı (`DrawContext` metodları güncellendi).

---

## [v1.0.0] - İlk Sürüm - 2026-01-28
- Temiz Crystal Optimizer mantığı.
- Temel Fullbright, Low Fire ve NoWeather özellikleri.
- İlk TargetHUD ve InventoryHUD versiyonu.
- Minecraft 1.21.1 Fabric desteği.
