# 🌳 Sylva - AI-Powered Tree Recognition Platform

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple?logo=kotlin)](https://kotlinlang.org)
[![API Level](https://img.shields.io/badge/API%20Level-24+-brightgreen)](https://developer.android.com)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM-blue)](https://developer.android.com/architecture)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

> **Bridging Nature and Technology**: An intelligent mobile application that revolutionizes how users interact with and learn about trees through AI-powered recognition and ecological insights.

---

## 🎯 Vision & Mission

Sylva democratizes tree identification and environmental education by combining cutting-edge computer vision with generative AI. Our mission is to inspire environmental awareness and stewardship by making tree science accessible to everyone.

**The Problem**: Most people cannot identify trees, missing opportunities to connect with nature and understand urban ecosystems.

**The Solution**: Sylva provides instant, AI-powered tree identification with rich ecological context through a seamless mobile experience.

---

## ✨ Core Features

### 🎬 Image Capture & Upload
- **CameraX Integration**: Real-time camera feed with optimized capture
- **Gallery Integration**: Upload existing tree images for analysis
- **Image Optimization**: Automatic compression and processing for API compatibility

### 🤖 AI-Powered Recognition
- **Plant.id Integration**: State-of-the-art tree species detection (99%+ accuracy)
- **Multi-Language Support**: Retrieve both scientific and common names
- **Confidence Scoring**: Transparent confidence metrics for each prediction

### 🌍 Ecological Insights
- **Gemini API Integration**: Contextual environmental information
- **Scientific Classification**: Detailed taxonomic data and characteristics
- **Environmental Impact**: Urban pollution resilience and biodiversity contribution
- **Geographic Intelligence**: Native region identification and climate preferences
- **Contextual Facts**: Engaging, educational fun facts about each species

### 📊 Rich Tree Profiles
- Species identification with multiple common names
- Confidence scores and detection reliability
- High-resolution imagery and botanical illustrations
- Ecological role and carbon sequestration potential
- Regional distribution maps

---

## 🏗️ Architecture

### System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Android UI Layer                         │
│                    (Jetpack Compose)                         │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐       │
│  │   Camera     │  │   Gallery    │  │    Tree      │       │
│  │   Screen     │  │   Upload     │  │   Details    │       │
│  └──────┬───────┘  └──────┬───────┘  │   Screen     │       │
│         │                 │          └──────────────┘       │
└─────────┼─────────────────┼───────────────────────────────────┘
          │                 │
          └────────┬────────┘
                   │
        ┌──────────▼──────────┐
        │  ViewModel Layer    │
        │  (MVVM Pattern)     │
        │  - State Management │
        │  - Business Logic   │
        └──────────┬──────────┘
                   │
        ┌──────────▼──────────────┐
        │   Repository Layer      │
        │  - API Coordination     │
        │  - Data Caching        │
        │  - Error Handling      │
        └──────────┬──────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
   ┌────▼────┐          ┌────▼────┐
   │          │          │          │
   │ Plant.id │          │ Gemini   │
   │   API    │          │   API    │
   │(Detection)         │(Insights)│
   └──────────┘          └──────────┘
```

### MVVM Pattern Implementation

```
View (Jetpack Compose)
    ↓ (observes)
ViewModel (StateFlow)
    ↓ (calls)
Repository (Use Cases)
    ↓ (calls)
API Services (Network Requests)
    ↓ (returns)
Data Models (JSON Parsing)
```

---

## 📲 API Workflow

### Step-by-Step Recognition Process

```
User Action
    ↓
[Capture/Upload Image]
    ↓
Image Processing & Validation
    ↓
Send to Plant.id API
    ├─ Request: Image + API Key
    └─ Response: Species details (name, confidence, botanical info)
    ↓
Parse Species Data
    ↓
Send to Gemini API
    ├─ Prompt: Species name + context request
    └─ Response: Ecological insights, fun facts, environmental data
    ↓
Combine Results
    ↓
Display Rich Tree Profile
    ↓
[User Reviews Results & Saves/Shares]
```

---

## 🛠️ Tech Stack

### Frontend Architecture
| Layer | Technology | Purpose |
|-------|-----------|---------|
| **UI Framework** | Jetpack Compose | Modern declarative UI |
| **Image Capture** | CameraX | Camera integration & optimization |
| **HTTP Client** | Retrofit + OkHttp | API communication |
| **Async Processing** | Kotlin Coroutines | Non-blocking operations |
| **Image Loading** | Coil | Efficient image management |
| **State Management** | StateFlow/ViewModel | Reactive data flow |
| **Pattern** | MVVM | Scalable architecture |

### External APIs
| Service | Purpose | Integration |
|---------|---------|-------------|
| **Plant.id API** | Tree species recognition | REST API with image upload |
| **Google Gemini API** | Ecological insights generation | REST API with text prompts |

### Build & Tooling
- **Language**: Kotlin 1.9+
- **Gradle**: Kotlin DSL (build.gradle.kts)
- **Minimum API**: Level 24 (Android 7.0)
- **Target API**: Level 34+ (Android 14+)

---

## 📁 Suggested Project Structure

```
Sylva/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/sylva/
│   │   │   │   ├── di/                          # Dependency Injection (Hilt)
│   │   │   │   │   └── ApiModule.kt
│   │   │   │   ├── data/
│   │   │   │   │   ├── api/
│   │   │   │   │   │   ├── PlantIdService.kt
│   │   │   │   │   │   ├── GeminiService.kt
│   │   │   │   │   │   └── ApiClient.kt
│   │   │   │   │   ├── models/
│   │   │   │   │   │   ├── TreeIdentification.kt
│   │   │   │   │   │   ├── PlantIdResponse.kt
│   │   │   │   │   │   └── GeminiResponse.kt
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── TreeRepository.kt
│   │   │   │   │   └── local/
│   │   │   │   │       ├── database/
│   │   │   │   │       └── SharedPreferences.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/
│   │   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   │   ├── CameraScreen.kt
│   │   │   │   │   │   ├── GalleryScreen.kt
│   │   │   │   │   │   ├── TreeDetailsScreen.kt
│   │   │   │   │   │   └── LoadingScreen.kt
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── TreeCard.kt
│   │   │   │   │   │   ├── InsightCard.kt
│   │   │   │   │   │   └── ImagePreview.kt
│   │   │   │   │   ├── theme/
│   │   │   │   │   │   ├── Color.kt
│   │   │   │   │   │   ├── Theme.kt
│   │   │   │   │   │   └── Type.kt
│   │   │   │   │   └── navigation/
│   │   │   │   │       └── NavGraph.kt
│   │   │   │   ├── viewmodel/
│   │   │   │   │   ├── TreeViewModel.kt
│   │   │   │   │   └── SharedViewModel.kt
│   │   │   │   ├── utils/
│   │   │   │   │   ├── ImageProcessor.kt
│   │   │   │   │   ├── CameraUtils.kt
│   │   │   │   │   ├── Logger.kt
│   │   │   │   │   └── Constants.kt
│   │   │   │   ├── domain/
│   │   │   │   │   ├── usecase/
│   │   │   │   │   │   ├── IdentifyTreeUseCase.kt
│   │   │   │   │   │   └── FetchEcologicalInsightsUseCase.kt
│   │   │   │   │   └── model/
│   │   │   │   │       └── Tree.kt
│   │   │   │   └── MainActivity.kt
│   │   │   └── res/
│   │   │       ├── drawable/
│   │   │       ├── mipmap-*/
│   │   │       └── values/
│   │   ├── androidTest/
│   │   └── test/
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── local.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/
├── README.md
├── LICENSE
└── .gitignore
```

---

## 🚀 Installation & Setup

### Prerequisites
- Android Studio Flamingo or later
- Kotlin 1.9+
- Gradle 8.0+
- Java 11 or later
- API Keys for:
  - Plant.id API
  - Google Gemini API

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/Sylva.git
cd Sylva
```

### Step 2: Configure Local Properties

Create or update `local.properties` in the project root:

```properties
sdk.dir=/path/to/android/sdk
```

### Step 3: Add API Keys

Create a `local.properties` file with your API credentials:

```properties
PLANT_ID_API_KEY=your_plant_id_api_key_here
GEMINI_API_KEY=your_gemini_api_key_here
```

**Alternatively**, add to `build.gradle.kts`:

```kotlin
android {
    buildTypes {
        release {
            buildConfigField("String", "PLANT_ID_API_KEY", "\"${project.property("PLANT_ID_API_KEY")}\"")
            buildConfigField("String", "GEMINI_API_KEY", "\"${project.property("GEMINI_API_KEY")}\"")
        }
        debug {
            buildConfigField("String", "PLANT_ID_API_KEY", "\"${project.property("PLANT_ID_API_KEY")}\"")
            buildConfigField("String", "GEMINI_API_KEY", "\"${project.property("GEMINI_API_KEY")}\"")
        }
    }
}
```

### Step 4: Build and Run

```bash
# Build the project
./gradlew build

# Run on emulator/device
./gradlew installDebug
```

---

## 🔐 API Key Configuration

### Obtaining API Keys

#### Plant.id API
1. Visit [Plant.id Developer Portal](https://plant.id/api)
2. Create a free account
3. Generate API key from dashboard
4. Choose appropriate tier (free tier: 100 requests/day)

#### Google Gemini API
1. Visit [Google AI Studio](https://ai.google.dev/)
2. Click "Get API Key"
3. Create new API key for your project
4. Enable Generative Language API in Google Cloud Console

### Secure Storage Best Practices

```kotlin
// ✅ DO: Use BuildConfig for sensitive data
val apiKey = BuildConfig.GEMINI_API_KEY

// ❌ DON'T: Hardcode keys in source
val apiKey = "sk-1234567890abcdef"

// ✅ DO: Rotate keys regularly
// ✅ DO: Use different keys for dev/prod
// ✅ DO: Monitor API usage and implement rate limiting
```

---

## 📊 API Specifications

### Plant.id API Integration

```kotlin
POST /api/v3/identification
Content-Type: multipart/form-data

Headers:
- Api-Key: Your API key

Multipart fields:
- images: Image file (JPEG/PNG)

Response:
{
  "results": [
    {
      "classification": {
        "suggestions": [
          {
            "name": "Quercus robur",
            "probability": 0.95,
            "details": {
              "common_names": ["English Oak"],
              "taxonomy": { ... }
            }
          }
        ]
      },
      "query": { ... }
    }
  ]
}

Note: the app scans all returned `results` entries and uses the highest-probability suggestion it finds.
```

### Gemini API Integration

```kotlin
POST /v1beta/models/gemini-pro:generateContent
Content-Type: application/json

Request:
{
  "contents": [
    {
      "parts": [
        {
          "text": "Provide ecological insights about [Tree Species]..."
        }
      ]
    }
  ]
}

Response:
{
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "Ecological insights and information..."
          }
        ]
      }
    }
  ]
}
```

---

## 🎯 Scalability Considerations

### Performance Optimization

#### Image Processing
- **Compression**: Reduce image size before API submission (target: 500-800KB)
- **Caching**: Store identification results locally
- **Parallel Processing**: Use Coroutines for concurrent API calls

#### API Rate Limiting
```kotlin
// Implement exponential backoff
suspend fun retryWithBackoff(
    maxRetries: Int = 3,
    initialDelayMs: Long = 100
) {
    repeat(maxRetries) { attempt ->
        try {
            // Make API call
        } catch (e: Exception) {
            delay(initialDelayMs * (2 shl attempt))
        }
    }
}
```

#### Caching Strategy
- **Memory Cache**: TreeLRUCache for recent identifications
- **Local Database**: Room Database for search history
- **API Response Caching**: OkHttp interceptor with cache headers

### Architecture for Scale

```
Single Device (MVP)
    ↓
Multiple Users (Local Caching)
    ↓
Backend Services (Optional)
    - User Sync & History
    - Custom ML Model Training
    - Advanced Analytics
    ↓
Distributed System
    - Microservices
    - Real-time Database
    - Analytics Pipeline
```

### Server Infrastructure (Future)

```
API Gateway
    ↓
├─ Authentication Service
├─ Tree Identification Service
├─ Insights Generation Service
├─ User Data Service
└─ Analytics Service
    ↓
Cache Layer (Redis)
    ↓
Database Layer (PostgreSQL)
    ↓
Storage (Cloud Storage)
```

---

## 🗓️ Development Roadmap

### Phase 1: MVP (Current)
- ✅ Camera integration
- ✅ Gallery upload
- ✅ Plant.id integration
- ✅ Gemini integration
- ✅ Tree profile display

### Phase 2: Enhancement (Q3 2024)
- 🔄 User authentication
- 🔄 Search history
- 🔄 Favorites/Bookmarks
- 🔄 Share functionality
- 🔄 Offline mode

### Phase 3: Gamification (Q4 2024)
- 🎮 Achievement badges
- 🎮 Tree discovery journal
- 🎮 Leaderboards
- 🎮 Daily challenges

### Phase 4: Community (Q1 2025)
- 👥 Social sharing
- 👥 Community comments
- 👥 Tree spotting locations
- 👥 Environmental contributions map

### Phase 5: Advanced AI (Q2 2025)
- 🧠 Custom ML models
- 🧠 Real-time video identification
- 🧠 Multi-tree batch detection
- 🧠 Seasonal variations

### Phase 6: Enterprise Features (Q3 2025)
- 🏢 API for third-party apps
- 🏢 Advanced analytics dashboard
- 🏢 Institutional accounts
- 🏢 White-label solutions

---

## 💡 Why Sylva Matters

### Environmental Impact
- **Biodiversity Awareness**: Educates users about local ecosystems
- **Urban Green Spaces**: Promotes tree planting initiatives
- **Climate Action**: Highlights carbon sequestration benefits
- **Conservation**: Supports endangered species identification

### Social Value
- **Accessibility**: Makes botanical knowledge available to everyone
- **Education**: Interactive learning for students and nature enthusiasts
- **Community**: Connects people through shared environmental interests
- **Health**: Promotes outdoor activity and nature connection

### Technical Innovation
- **AI Integration**: Demonstrates practical computer vision application
- **Mobile Excellence**: Shows modern Android development practices
- **Scalability**: Architectured for millions of users
- **User Experience**: Seamless, intuitive interface

---

## 🤝 Contributing

We welcome contributions! Please see our [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

### Development Workflow

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Code Standards
- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Write unit tests for business logic
- Document complex functions with KDoc
- Use meaningful commit messages

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 📞 Contact & Support

- **Project Lead**: [Your Name]
- **Email**: your.email@example.com
- **LinkedIn**: [Your Profile]
- **Issues**: [GitHub Issues](../../issues)
- **Discussions**: [GitHub Discussions](../../discussions)

---

## 🙏 Acknowledgments

- **Plant.id** for providing excellent plant recognition API
- **Google Gemini** for powerful generative AI capabilities
- **Jetpack Compose** team for revolutionary UI framework
- **Android community** for invaluable resources and support

---

## 📚 Resources

### Documentation
- [Android Developer Documentation](https://developer.android.com)
- [Jetpack Compose Guide](https://developer.android.com/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs)
- [Plant.id API Docs](https://plant.id/api)
- [Gemini API Guide](https://ai.google.dev/docs)

### Useful Links
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Coroutines Best Practices](https://kotlinlang.org/docs/coroutines-guide.html)
- [Material Design 3](https://m3.material.io)

---

<div align="center">

### Made with 🌳 for nature enthusiasts and developers


</div>

