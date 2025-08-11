
https://github.com/user-attachments/assets/dc5f3664-d8ba-4d65-8cd9-6c3bf5701555


# 🧭 Compass - Android Compass Application

A modern, feature-rich Android compass application built with Jetpack Compose, featuring real-time compass readings, location tracking, and a beautiful Material Design 3 interface.

## ✨ Features

- **Real-time Compass**: Smooth, animated compass needle that responds to device orientation
- **Location Services**: GPS coordinates, elevation, and reverse geocoding for address information
- **Modern UI**: Built with Jetpack Compose and Material Design 3
- **Smooth Animations**: Spring-based animations for natural compass movement
- **Sensor Integration**: Utilizes device rotation vector sensor for accurate readings
- **Responsive Design**: Adapts to different screen orientations and sizes
- **Clean Architecture**: Built with MVVM pattern and dependency injection

## 🏗️ Architecture

This project follows modern Android development best practices:

- **MVVM Architecture**: Separation of concerns with ViewModels and UI state management
- **Jetpack Compose**: Modern declarative UI toolkit
- **Dependency Injection**: Hilt for clean dependency management
- **Coroutines & Flow**: Asynchronous programming with reactive streams
- **Clean Architecture**: Modular design with clear separation of layers

### Project Structure

```
app/src/main/java/com/attyran/compass/
├── di/                    # Dependency injection modules
├── model/                 # Data models and entities
├── sensor/                # Sensor implementations
│   ├── CompassSensor.kt  # Compass sensor handling
│   └── LocationSensor.kt # GPS and location services
├── ui/                    # User interface components
│   ├── CompassScreen.kt  # Main compass display
│   ├── CompassViewModel.kt # UI state management
│   └── theme/            # Material Design theming
└── MainActivity.kt       # Application entry point
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or later
- Android SDK 35 (API level 35)
- Minimum SDK: 24 (Android 7.0)
- Java 11 or later

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/compass.git
   cd compass
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and select it

3. **Sync and Build**
   - Wait for Gradle sync to complete
   - Build the project (Build → Make Project)

4. **Run on Device/Emulator**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

### Required Permissions

The app requires the following permissions:

- **Location Permission**: For GPS coordinates and address information
- **Sensor Access**: For compass readings (automatically granted)

## 🛠️ Technology Stack

- **Language**: Kotlin 2.1.0
- **UI Framework**: Jetpack Compose 2025.05.01
- **Architecture Components**: 
  - ViewModel
  - Lifecycle
  - Coroutines & Flow
- **Dependency Injection**: Hilt 2.56.2
- **Location Services**: Google Play Services Location 21.2.0
- **Build System**: Gradle with Kotlin DSL
- **Testing**: JUnit, Mockito, Robolectric, Turbine

## 📱 Usage

1. **Launch the App**: Open the Compass app on your device
2. **Grant Permissions**: Allow location access when prompted
3. **Calibrate**: Hold your device flat and rotate it in a figure-8 pattern
4. **Navigate**: The compass needle will point north, and you'll see:
   - Current direction (N, NE, E, SE, S, SW, W, NW)
   - Degrees from north (0-359°)
   - GPS coordinates
   - Elevation in feet
   - Current address (if available)

## 🧪 Testing

The project includes comprehensive testing:

- **Unit Tests**: Core logic and sensor data processing
- **Integration Tests**: Sensor interactions and data flow
- **UI Tests**: Compose UI component testing
- **Test Coverage**: Includes mocking, coroutine testing, and architecture testing

Run tests with:
```bash
./gradlew test          # Unit tests
./gradlew connectedCheck # Instrumented tests
```

## 🔧 Configuration

### Build Variants

- **Debug**: Development build with debugging enabled
- **Release**: Production build with optimizations

### Customization

You can customize the app by modifying:
- `app/src/main/res/values/colors.xml` - Color scheme
- `app/src/main/res/values/themes.xml` - App theme
- `app/src/main/java/com/attyran/compass/ui/theme/` - Compose theming

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

If you encounter any issues or have questions:

- Create an issue on GitHub
- Check the existing issues for solutions
- Review the code documentation

## 🙏 Acknowledgments

- Android team for Jetpack Compose
- Google for Material Design 3
- The open-source community for testing libraries and tools

---

**Note**: This app requires a device with a magnetometer sensor for compass functionality. Some emulators may not provide accurate sensor readings.
