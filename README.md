# 🌟 ZunoKids — Children's Educational App

A complete Android application in Kotlin + Jetpack Compose for children ages 3–7. Designed for families in the United States including non-English-speaking households.

---

## 📦 Project Structure

```
app/src/main/
├── java/com/kidslearn/app/
│   ├── KidsLearnApp.kt              ← @HiltAndroidApp Application
│   ├── MainActivity.kt              ← Single activity entry point
│   ├── navigation/
│   │   └── AppNavGraph.kt           ← Compose Navigation setup
│   ├── ui/
│   │   ├── theme/
│   │   │   ├── Theme.kt             ← Material3 dark theme + brand colors
│   │   │   └── Typography.kt        ← Large kid-friendly type scale (min 24sp)
│   │   ├── shared/
│   │   │   └── SharedComposables.kt ← ModuleScaffold, ProgressDots, CountdownRing, BouncyCard
│   │   ├── home/
│   │   │   ├── HomeScreen.kt        ← Module grid + language selector
│   │   │   └── HomeViewModel.kt     ← Language & completed modules state
│   │   ├── numbers/
│   │   │   ├── NumbersScreen.kt     ← Animated number + emoji display
│   │   │   └── NumbersViewModel.kt
│   │   ├── alphabet/
│   │   │   └── AlphabetScreen.kt    ← Letter cards with spring animations
│   │   ├── fruits/
│   │   │   └── FruitsScreen.kt      ← 2-column grid, tap-to-speak
│   │   ├── colors/
│   │   │   └── ColorsScreen.kt      ← Full-screen HorizontalPager color swipe
│   │   └── objects/
│   │       └── ObjectsScreen.kt     ← Everyday objects grid + TTS
│   ├── data/
│   │   ├── LearningContent.kt       ← All hardcoded data (no network)
│   │   ├── UserPreferencesRepo.kt   ← DataStore: language, completed modules
│   │   └── TtsManager.kt            ← TextToSpeech wrapper (5 languages)
│   └── di/
│       └── AppModule.kt             ← Hilt dependency injection
├── res/
│   ├── values/strings.xml           ← English
│   ├── values-es/strings.xml        ← Spanish
│   ├── values-zh/strings.xml        ← Mandarin
│   ├── values-hi/strings.xml        ← Hindi
│   ├── values-fr/strings.xml        ← French
│   └── raw/README.md                ← Instructions for chime.mp3
```

---

## 🚀 Setup Instructions

### 1. Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 11+
- Android SDK 35

### 2. Open the Project
```bash
# Clone or extract the project
cd kidslearn
# Open in Android Studio: File → Open → select kidslearn/
```

### 3. Add the chime sound (optional)
Place a cheerful MP3 file at:
```
app/src/main/res/raw/chime.mp3
```
Free sources: freesound.org, pixabay.com, mixkit.co

### 4. Build & Run
```bash
./gradlew assembleDebug
# Or press Run ▶ in Android Studio
```

---

## 📚 Learning Modules

| Module   | Items | Interaction         | Auto-advance |
|----------|-------|---------------------|--------------|
| Numbers  | 1–10  | Tap card, swipe     | 5 sec timer  |
| Alphabet | A–Z   | Tap card, swipe     | 6 sec timer  |
| Fruits   | 10    | Tap card grid → TTS | No           |
| Colors   | 10    | Swipe (HorizPager)  | No           |
| Objects  | 10    | Tap card grid → TTS | No           |

---

## 🌍 Languages

| Language  | Code | TTS Locale | Flag |
|-----------|------|------------|------|
| English   | en   | en-US      | 🇺🇸  |
| Spanish   | es   | es-ES      | 🇪🇸  |
| Mandarin  | zh   | zh-CN      | 🇨🇳  |
| Hindi     | hi   | hi-IN      | 🇮🇳  |
| French    | fr   | fr-FR      | 🇫🇷  |

Language selection is stored in DataStore and persists across app restarts.

---

## ✨ Key Features

- **MVVM + Hilt** — clean architecture with dependency injection
- **DataStore Preferences** — persists language and module completion
- **TextToSpeech** — speaks item names in the selected language
- **Spring animations** — bouncy card press (scale 1.0→1.12→1.0)
- **animateIntAsState** — smooth number count-up animation
- **AnimatedVisibility** — slideInVertically + fadeIn per card reveal
- **HorizontalPager** — smooth color swiping with animated background
- **CountdownRing** — Canvas-drawn circular timer
- **ProgressDots** — animated size-change indicator dots
- **Completed badges** — ✅ on home screen for finished modules
- **Edge-to-edge** — uses systemBarsPadding throughout

---

## 🎨 Design System

```kotlin
// Brand colors (Theme.kt)
val CoralRed       = Color(0xFFFF6B6B)
val SkyBlue        = Color(0xFF4ECDC4)
val SunshineYellow = Color(0xFFFFE66D)
val MintGreen      = Color(0xFF95E1D3)
val Lavender       = Color(0xFFA8DADC)
val DeepNavy       = Color(0xFF1A1A2E)  // background
```

- All corners: `RoundedCornerShape(24.dp)` minimum
- All cards: min `150dp` height — easy for small fingers
- All text: minimum `22.sp`, numbers/letters up to `140.sp`
- Dark navy background (`#1A1A2E`) with vivid gradient card surfaces

---

## 🔧 Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| Compose BOM | 2024.08.00 | UI framework |
| Navigation Compose | 2.7.7 | Screen routing |
| Hilt Android | 2.51.1 | DI |
| DataStore Preferences | 1.1.1 | Persistent storage |
| Lottie Compose | 6.4.1 | Confetti animations |
| Coroutines Android | 1.8.1 | Async/StateFlow |

---

## 🔮 Extending the App

### Add a new module
1. Add data class + list in `LearningContent.kt`
2. Create screen in `ui/newmodule/`
3. Add route in `AppNavGraph.kt` and `Screen` sealed class
4. Add `ModuleInfo` entry in `HomeScreen.kt`
5. Add translations in all 5 `strings.xml` files

### Add Lottie confetti on completion
```kotlin
// In any module screen, when index == lastIndex:
val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
LottieAnimation(composition, iterations = 1, modifier = Modifier.fillMaxSize())
```
Add a Lottie JSON file at `res/raw/confetti.json` (free at lottiefiles.com)

---

## 📝 Notes

- **No internet required** — all content is hardcoded
- **Portrait only** — `screenOrientation="portrait"` in manifest
- **Min SDK 24** (Android 7.0) — covers ~98% of active devices
- TTS availability depends on device language packs; app gracefully falls back to English
