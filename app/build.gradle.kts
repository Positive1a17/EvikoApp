// Подключаем необходимые плагины
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    // Конфигурация приложения
    namespace = "com.eviko.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.eviko.app"
        minSdk = 24 // Минимальная поддерживаемая версия Android
        targetSdk = 34 // Целевая версия Android
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true // Поддержка векторной графики
        }
    }

    // Конфигурация типов сборки
    buildTypes {
        release {
            isMinifyEnabled = true // Включаем минификацию для релизной версии
            isShrinkResources = true // Удаление неиспользуемых ресурсов
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug" // Суффикс для отличия debug-версии
        }
    }
    
    // Настройка совместимости с Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true // Поддержка новых Java API на старых версиях Android
    }
    
    // Настройки Kotlin
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
    
    // Настройки Compose
    buildFeatures {
        compose = true
        buildConfig = true // Генерация BuildConfig
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    
    // Настройки упаковки
    packagingOptions {
        resources {
            excludes += listOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE*",
                "META-INF/NOTICE*"
            )
        }
    }

    // Настройка тестов
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

// Определение версий зависимостей
val composeBomVersion = "2023.10.01"
val roomVersion = "2.6.1"
val hiltVersion = "2.48"
val filamentVersion = "1.45.0"

dependencies {
    // Compose BOM для согласованных версий Compose-зависимостей
    val composeBom = platform("androidx.compose:compose-bom:$composeBomVersion")
    implementation(composeBom)
    
    // Основные зависимости Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose UI компоненты
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    
    // Навигация
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Dependency Injection - Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // База данных - Room
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    implementation("androidx.sqlite:sqlite:2.4.0")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")
    
    // JSON парсинг
    implementation("com.google.code.gson:gson:2.10.1")
    
    // Загрузка изображений
    implementation("io.coil-kt:coil-compose:2.5.0")

    // 3D рендеринг - Filament
    implementation("com.google.android.filament:filament-android:$filamentVersion")
    implementation("com.google.android.filament:filament-utils-android:$filamentVersion")
    implementation("com.google.android.filament:gltfio-android:$filamentVersion")
    implementation("com.google.android.filament:filament-ktx:$filamentVersion")
    implementation("com.google.android.filament:filament-utils-ktx:$filamentVersion")
    implementation("com.google.android.filament:gltfio-ktx:$filamentVersion")

    // Поддержка новых Java API
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    
    // Тестирование
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    
    // Android тестирование
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    
    // Debug инструменты
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Логирование
    implementation("com.jakewharton.timber:timber:5.0.1")
    
    // SplashScreen API
    implementation("androidx.core:core-splashscreen:1.0.1")
    
    // AppCompat для поддержки темной темы
    implementation("androidx.appcompat:appcompat:1.6.1")
} 