# 📱 Designer Storage — Development Plan

---

## 📌 Phase 1: Project Setup

- Create New Android Project in Android Studio
  - Use **Jetpack Compose** template
  - Minimum SDK: **23 (Marshmallow)** or higher
  - Set up app permissions:
    - `CAMERA`
    - `READ_EXTERNAL_STORAGE` (if targeting Android < 13)
    - `READ_MEDIA_IMAGES` (Android 13+)

---

## 📌 Phase 2: Setup Bottom Navigation

- Implement **Bottom Navigation** with 2 screens:
  - 📸 **Camera**
  - 🖼️ **Viewer**
- Use **Navigation Compose** to navigate between them

---

## 📌 Phase 3: Implement Camera Functionality

- Integrate Camera
  - Use `ActivityResultContracts.TakePicture()` to capture image
  - Save image to **Blouse folder** in app’s external files directory:  
    `/Android/data/com.yourapp/files/Pictures/Blouse/`
  - If folder doesn’t exist → create it
- Show **"Take Photo" button** on Camera screen

---

## 📌 Phase 4: Viewer Screen Implementation

- Load all images from **Blouse folder**
  - List image files inside `/Pictures/Blouse/`
  - Show images using `LazyVerticalGrid`
- Add onClick for single image
  - Tap image → navigate to **Photo Preview** screen
  - Show image full-screen using `rememberAsyncImagePainter()`

---

## 📌 Phase 5: Single Image Sharing

- In **Photo Preview screen**
  - Add a **Share button** (`IconButton`)
  - On click → Share image via `Intent.ACTION_SEND`

---

## 📌 Phase 6: Multi-Select & Multiple Sharing

- In **Viewer screen**:
  - Implement **long-press to select multiple images**
  - Show checkboxes or selection overlays
  - Show **"Share" icon button** when one or more images are selected
- Use `Intent.ACTION_SEND_MULTIPLE` to share multiple images

---

## 📌 Phase 7: Polish UI and Error Handling

- Handle cases:
  - No images available → show **"No photos yet"**
  - Camera permission denied → show a prompt
- Add **loading indicators** while images are loading (if needed)

---

## 📌 Phase 8: Testing

- Test:
  - Image capture flow
  - Image storage in correct folder
  - Single image sharing
  - Multi-select sharing
  - Folder creation and reading permissions

---

## 📌 (Optional) Phase 9: Future Enhancements

- Add option to **create custom folders**
- Move images to other folders
- Add a **search bar** in Viewer
- Add **notes for each image**
- Backup to **cloud storage**

---
