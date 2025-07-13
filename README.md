# Compass

A simple and beautiful compass application for Android, built with modern development practices.

## 🚀 Features

*   **Real-time Orientation**: Displays the device's real-time orientation.
*   **True & Magnetic Heading**: Toggle between true north and magnetic north readings.
*   **Magnetic Declination**: Automatically calculates and displays magnetic declination based on your location.
*   **Modern UI**: Clean and minimalist user interface built with Jetpack Compose.
*   **Location**: Shows current latitude, longitude, and elevation.
*   **Built with Kotlin**: Fully written in Kotlin, following official guidelines.

## 🧭 True vs Magnetic Heading

### Magnetic Heading
- Points toward Earth's magnetic north pole
- Affected by local magnetic anomalies and interference
- Varies by location due to magnetic declination
- More susceptible to device interference and nearby metal objects

### True Heading
- Points toward geographic north (true north)
- More accurate for navigation and mapping
- Requires magnetic declination calculation
- Better for outdoor activities and navigation

The app automatically calculates magnetic declination based on your location and allows you to toggle between true and magnetic heading modes.

## 🛠️ Getting Started

### Prerequisites

*   Android Studio (latest stable version)
*   JDK 17 or higher

### Building the Project

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/compass.git
    ```
2.  **Open in Android Studio:**
    *   Open Android Studio.
    *   Select "Open an Existing Project".
    *   Navigate to the cloned directory and select it.
3.  **Build the project:**
    Android Studio should automatically sync the Gradle project. To build the app manually, you can use the command line:
    ```bash
    ./gradlew assembleDebug
    ```
    The debug APK will be located in `app/build/outputs/apk/debug/`.

## 🏃 Running the App

You can run the app directly from Android Studio on an emulator or a physical device.

Alternatively, you can install the generated debug APK using ADB:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 🧪 Running Tests

To run all unit tests from the command line:
```bash
./gradlew test
```

## 💻 Tech Stack

*   **Kotlin**: First-party and official programming language for Android development.
*   **Jetpack Compose**: Android’s modern toolkit for building native UI.
*   **Hilt**: For dependency injection.
*   **Gradle Kotlin DSL**: For build scripts.

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m '''Add some AmazingFeature'''`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
