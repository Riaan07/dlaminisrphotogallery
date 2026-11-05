# dlaminisrphotogallery

An Android Studio Kotlin project combining the supplied **Chapter 20 (Network Requests)** and **Chapter 21 (SearchView + DataStore)** instructions.

## What is implemented
- Retrieves photo metadata as JSON from the no-API-key `https://picsum.photos/v2/list` endpoint using Retrofit + Moshi.
- Renders a three-column RecyclerView grid with Coil-loaded, cached photo thumbnails.
- Offers a toolbar SearchView: submit a photographer's name to filter the first 100 downloaded photos.
- Saves the submitted search query with Jetpack DataStore so it remains after restarting the app.
- Clear Search menu action removes the saved query and resets the gallery.
- ViewModel + StateFlow preserve UI state across screen rotation; progress/error/empty states and Retry are included.

## Open and run in Android Studio
1. Extract the project ZIP, then in **Android Studio > Open**, select the **dlaminisrphotogallery** folder (the directory containing `settings.gradle.kts`).
2. Allow Android Studio to import and sync Gradle. Install **Android SDK Platform 35** and use **JDK 17** if prompted.
3. If Android Studio requests a **Gradle wrapper**, use installed **Gradle 8.9** to generate one (`gradle wrapper --gradle-version 8.9`) or
4. configure Android Studio to use a local Gradle 8.9 installation. The provided project includes wrapper properties but not the wrapper JAR.
5. Launch an Android emulator (Android 8.0 / API 26 or above) or connect a real Android phone with USB debugging enabled. Press Run.
6. Ensure the device has internet access. Search is **by photographer/author**, not image content, and is limited to the first 100 Picsum records.

## Validation checklist
- [ ] App launches and fetches a three-column photo grid with network connected.
- [ ] Search for a photographer from returned photos, tap search on keyboard: grid filters.
- [ ] Close and relaunch the app: saved query still filters photos.
- [ ] Overflow menu > **Clear Search** restores all photos.
- [ ] Turn off device internet and launch/press Retry to check error handling.
- [ ] Rotate device: gallery state persists.
- [ ] Take screenshots required by your lecturer and submit in the requested format.

## Important
This environment does not provide the Android SDK or Gradle installation needed to build/test an APK. The source project has been assembled and XML/source consistency checked, but **not executed on an emulator or phone**. Screenshots, builds, and lecturer-specific submission steps must be completed in Android Studio.
