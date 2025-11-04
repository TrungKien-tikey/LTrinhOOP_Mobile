import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    // --- Các thư viện AndroidX và Compose (Giữ nguyên) ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // --- Thư viện Navigation (Cần cho NavHost trong MainActivity.kt) ---
    // (Tôi thêm vào vì bạn đang dùng NavHost)
    implementation("androidx.navigation:navigation-compose:2.7.7")


    // --- SỬA LỖI FIREBASE VÀ GOOGLE SIGN-IN ---

    // 1. Thêm Firebase BOM để quản lý phiên bản
    // (Giúp các thư viện Firebase tự động tương thích)
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))

    // 2. SỬA LỖI 'Unresolved reference 'auth'':
    //    Thêm 'firebase-auth-ktx' thay vì chỉ 'firebase-auth'
    implementation("com.google.firebase:firebase-auth-ktx")

    // 3. THÊM: Thư viện Google Sign-In (Cần cho 'GoogleSignIn' trong LoginScreen.kt)
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // 4. Các thư viện 'credentials' và 'googleid' (Giữ nguyên từ file của bạn)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // --- Các thư viện Test (Giữ nguyên) ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation("io.coil-kt:coil-compose:2.6.0")
}
