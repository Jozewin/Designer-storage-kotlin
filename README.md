# Designer Storage - Kotlin Camera App

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2021+-green.svg)](https://developer.android.com/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A modern Android camera application built with **Jetpack Compose** and **Kotlin** for tailors and designers to capture, store, and manage their work photos. The app provides an intuitive interface for taking photos and organizing them in a dedicated storage system.

![App Banner](screenshots/app_banner.png)

## 📱 Screenshots

| Camera Screen | Photo Viewer | Photo Preview | Permission Screen |
|--------------|-------------|---------------|------------------|
| ![Camera](screenshots/camera.jpeg) | ![Viewer](screenshots/gallery.jpeg) | ![Preview](screenshots/viewer.jpeg) | ![Permission](screenshots/cameraa.jpeg) |

## ✨ Features

### 📸 Camera Functionality
- **High-Quality Photo Capture**: Take professional photos with camera controls
- **Flash Control**: Toggle flash on/off for different lighting conditions
- **Tap to Focus**: Touch anywhere on screen to focus on specific areas
- **Front/Back Camera Switch**: Switch between front and rear cameras
- **Real-time Preview**: Live camera feed with smooth performance

### 📁 Photo Management
- **Organized Storage**: All photos saved in dedicated "Tailor" folder
- **Grid View**: Beautiful grid layout for easy photo browsing
- **Photo Preview**: Full-screen photo viewing with zoom capabilities
- **Multi-Selection**: Select multiple photos for batch operations
- **Share Functionality**: Share single or multiple photos instantly
- **Delete Options**: Remove unwanted photos with confirmation

### 🎨 Modern UI/UX
- **Jetpack Compose**: Built with Google's modern UI toolkit
- **Material Design 3**: Follows latest Material Design guidelines
- **Dark/Light Theme**: Automatic theme switching based on system settings
- **Smooth Animations**: Fluid transitions and interactions
- **Responsive Design**: Optimized for different screen sizes

### 🔐 Privacy & Permissions
- **Runtime Permissions**: Handles camera and storage permissions gracefully
- **Permission Education**: Clear explanations for why permissions are needed
- **Secure Storage**: Photos stored locally on device

## 🏗️ Project Structure

```
Designer-storage-kotlin/
├── app/src/main/java/com/josewin/designer_storage/
│   ├── MainActivity.kt                           # Main entry point with Compose setup
│   ├── MyApplication.kt                          # Application class with Hilt
│   │
│   ├── data/
│   │   ├── model/
│   │   │   └── ImageItem.kt                      # Data model for images
│   │   └── repository/
│   │       ├── CameraRepository.kt               # Camera operations
│   │       └── ViewerRepository.kt               # Image viewing/management
│   │
│   ├── navigation/
│   │   ├── AppNavigation.kt                      # Navigation setup
│   │   └── Screen.kt                            # Screen definitions
│   │
│   ├── presentation/
│   │   ├── state/
│   │   │   ├── CameraUiState.kt                 # Camera screen state
│   │   │   └── ViewerUiState.kt                 # Viewer screen state
│   │   │
│   │   ├── ui/
│   │   │   ├── components/
│   │   │   │   ├── BottomNavigationBar.kt       # Bottom navigation
│   │   │   │   ├── CameraControls.kt            # Camera control buttons
│   │   │   │   ├── CameraPreview.kt             # Camera preview with focus
│   │   │   │   ├── ImageGridItem.kt             # Grid item for photos
│   │   │   │   └── PermissionHandler.kt         # Permission management
│   │   │   │
│   │   │   ├── screens/
│   │   │   │   ├── CameraScreen.kt              # Main camera interface
│   │   │   │   ├── ViewerScreen.kt              # Photo gallery
│   │   │   │   └── PhotoPreviewScreen.kt        # Full-screen photo view
│   │   │   │
│   │   │   └── theme/
│   │   │       ├── Color.kt                     # App colors
│   │   │       ├── Theme.kt                     # Theme configuration
│   │   │       └── Type.kt                      # Typography
│   │   │
│   │   └── viewmodel/
│   │       ├── CameraViewModel.kt               # Camera logic
│   │       └── ViewerViewModel.kt               # Viewer logic
│   │
│   └── util/
│       ├── FileUtils.kt                         # File operations
│       ├── LogUtils.kt                          # Logging utilities
│       └── PermissionUtils.kt                   # Permission handling
│
├── screenshots/                                 # App screenshots
├── build.gradle.kts                            # App-level Gradle config
└── README.md                                   # This file
```

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Flamingo (2022.2.1) or newer
- **JDK**: Version 17 or higher
- **Android SDK**: API level 21 (Android 5.0) or higher
- **Kotlin**: Version 1.9.0 or newer
- **Compose Compiler**: Latest stable version

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Jozewin/Designer-storage-kotlin.git
   cd Designer-storage-kotlin
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Sync the project**
   - Android Studio will automatically sync the project
   - Wait for the Gradle sync to complete

4. **Run the application**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## 🛠️ Technology Stack

### Core Technologies
- **Language**: 100% Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Hilt/Dagger
- **Camera**: CameraX API
- **Navigation**: Compose Navigation

## 🎨 UI Components

### Camera Screen
![Camera Screen](screenshots/camera_screen.png)

The main camera interface with professional controls and real-time preview.

**Key Features:**
- Live camera preview with tap-to-focus
- Flash toggle with visual feedback
- Capture button with loading states
- Success/error snackbar notifications
- Permission handling integration

### Photo Viewer Screen
![Viewer Screen](screenshots/viewer_screen.png)

Grid-based photo gallery for browsing captured images.

**Key Features:**
- Responsive grid layout (2 columns)
- Long-press for multi-selection mode
- Batch sharing and deletion
- Empty state handling
- Pull-to-refresh functionality

### Photo Preview Screen
![Preview Screen](screenshots/photo_preview_screen.png)

Full-screen photo viewing with sharing capabilities.

**Key Features:**
- High-resolution image display
- Zoom and pan gestures
- Share functionality
- Navigation back to gallery
- System UI integration

### Permission Handler
![Permission Screen](screenshots/permission_screen.png)

Elegant permission request flow with clear explanations.

**Key Features:**
- Educational permission rationale
- One-tap permission granting
- Settings redirect for denied permissions
- Material Design 3 styling
- Accessibility compliance

## 📦 Building & Release

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Signing Configuration
Create `keystore.properties` in the root directory:
```properties
storeFile=path/to/keystore.jks
storePassword=your_store_password
keyAlias=your_key_alias
keyPassword=your_key_password
```

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Commit your changes**
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
5. **Open a Pull Request**

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Josewin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👨‍💻 Author

**Josewin**
- GitHub: [@Jozewin](https://github.com/Jozewin)
- Email: [josewinjosewin@gmail.com](mailto:josewinjosewin@gmail.com)

## 🌟 Show Your Support

If you found this project helpful, please consider:
- ⭐ Starring the repository
- 🐛 Reporting bugs
- 💡 Suggesting new features
- 🤝 Contributing code
- 📢 Sharing with others

---

**Happy Coding! 📸✨**
