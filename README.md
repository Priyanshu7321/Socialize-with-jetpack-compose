# Socialize - Android Social Media App

A modern, feature-rich social media application built with Jetpack Compose and Kotlin. Socialize provides a comprehensive platform for users to connect, share content, and engage with others through various social features.

## 🚀 Features

### Core Features
- **User Authentication**: Secure sign-up and login system with email/password
- **Social Feed**: Discover and follow content from other users
- **Post Creation**: Share text posts and images with the community
- **User Profiles**: Detailed user profiles with posts, bookmarks, and followers
- **Real-time Chat**: Direct messaging with other users
- **Video Calling**: Built-in video calling functionality
- **User Discovery**: Find and connect with new users
- **Status/Stories**: Share temporary status updates
- **Notifications**: Real-time notification system

### UI/UX Features
- **Modern Design**: Material Design 3 with custom theming
- **Responsive Layout**: Optimized for different screen sizes
- **Smooth Navigation**: Bottom navigation with intuitive routing
- **Dark/Light Theme**: Theme support for different preferences
- **Image Loading**: Efficient image loading with Coil
- **Pull-to-Refresh**: Interactive content refresh

## 🛠 Tech Stack

### Frontend
- **Jetpack Compose**: Modern UI toolkit for Android
- **Material Design 3**: Latest Material Design components
- **Navigation Compose**: Type-safe navigation
- **Coil**: Image loading library
- **Kotlin Coroutines**: Asynchronous programming

### Backend & Data
- **Room Database**: Local data persistence
- **DataStore**: Key-value data storage
- **Retrofit**: HTTP client for API calls
- **Appwrite SDK**: Backend-as-a-Service integration
- **Hilt**: Dependency injection

### Architecture
- **MVVM Pattern**: Model-View-ViewModel architecture
- **Repository Pattern**: Data access abstraction
- **Clean Architecture**: Separation of concerns
- **Dependency Injection**: Hilt for dependency management

## 📱 Screenshots

The app includes the following main screens:
- **Login/Signup**: Authentication interface
- **Home Feed**: Main social media feed
- **Profile**: User profile management
- **Chat**: Messaging interface
- **Video Call**: Video calling feature
- **User Discovery**: Find new users
- **Post Creation**: Content sharing interface

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 25+ (API level 25)
- Kotlin 1.9.0+
- JDK 11

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/SocializeApp.git
   cd SocializeApp
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Open the project folder
   - Wait for Gradle sync to complete

3. **Configure Backend**
   - Update the `BASE_URL` in `app/build.gradle.kts` with your backend server URL
   - Configure Appwrite project settings if using Appwrite backend

4. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio
   - The app will install and launch on your device

### Configuration

#### Backend URL
Update the backend URL in `app/build.gradle.kts`:
```kotlin
buildConfigField("String", "BASE_URL", "\"http://your-backend-url:port/\"")
```

#### Appwrite Configuration (if using)
If you're using Appwrite as your backend, configure your project settings in the relevant service files.

## 📁 Project Structure

```
app/src/main/java/com/example/socialize/
├── Constants/
│   └── AppConstants.kt
├── dao/
│   ├── GroupChatDao.kt
│   ├── MessageDao.kt
│   └── UserDao.kt
├── database/
│   └── AppDatabase.kt
├── entity/
│   └── User.kt
├── module/
│   ├── DatabaseModule.kt
│   ├── DatastoreModule.kt
│   └── NetworkModule.kt
├── pojo/
│   ├── GroupChat.kt
│   ├── GroupMember.kt
│   ├── Message.kt
│   └── Post.kt
├── repository/
│   ├── AuthRepository.kt
│   ├── DatabaseRepository.kt
│   ├── DatastoreRepository.kt
│   └── NetworkRepository.kt
├── screens/
│   ├── Chats.kt
│   ├── Home.kt
│   ├── Members.kt
│   ├── Posting.kt
│   ├── Profile.kt
│   ├── status.kt
│   ├── Users.kt
│   └── VideoCall.kt
├── Service/
│   └── ApiService.kt
├── util/
│   └── Converters.kt
├── viewmodel/
│   ├── DatabaseViewModel.kt
│   ├── DatastoreViewModel.kt
│   └── NetworkViewModel.kt
├── MyApplication.kt
└── SignAndLogin.kt
```

## 🔧 Dependencies

### Core Dependencies
- **AndroidX Core KTX**: 1.12.0
- **Jetpack Compose BOM**: Latest version
- **Material Design 3**: Latest version
- **Navigation Compose**: 2.7.7
- **Room Database**: 2.6.1
- **DataStore**: 1.1.3
- **Hilt**: 2.51.1
- **Retrofit**: 2.9.0
- **Coil**: 2.5.0
- **Appwrite SDK**: 6.1.0

## 🎨 Customization

### Themes
The app uses Material Design 3 theming with custom colors. You can customize the theme in:
- `app/src/main/java/com/example/socialize/ui/theme/Color.kt`
- `app/src/main/java/com/example/socialize/ui/theme/Theme.kt`

### Colors
The app uses a custom color palette with:
- Primary: Magenta gradient
- Secondary: Orange accent
- Background: White/Light gray
- Text: Dark gray/Black

## 🔒 Permissions

The app requires the following permissions:
- **Internet**: For network communication and API calls

## 🧪 Testing

### Unit Tests
Run unit tests using:
```bash
./gradlew test
```

### Instrumented Tests
Run instrumented tests using:
```bash
./gradlew connectedAndroidTest
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

If you encounter any issues or have questions:
- Create an issue in the GitHub repository
- Contact the development team
- Check the documentation for common solutions

## 🔄 Version History

- **v1.0.0**: Initial release with core social media features
  - User authentication
  - Social feed
  - Profile management
  - Chat functionality
  - Video calling
  - User discovery

## 🙏 Acknowledgments

- Jetpack Compose team for the amazing UI toolkit
- Material Design team for the design system
- Appwrite team for the backend services
- All contributors and testers

---

**Note**: This is a personal project built for learning and demonstration purposes. The app includes comprehensive social media features and follows modern Android development best practices.
