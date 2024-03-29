plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.pokesearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pokesearch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.pokesearch.HiltAndroidTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Libraries support

    // Compose
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
}

dependencies {

    // KTX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Compose
    val composeBomVersion = "2024.02.00"
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Hilt
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")

    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kspAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // Coil
    val coilVersion = "2.5.0"
    implementation("io.coil-kt:coil-compose:$coilVersion")
    implementation("io.coil-kt:coil-gif:$coilVersion")

    // Tests

    // JUnit
    testImplementation("junit:junit:4.13.2")

    // Android X tests
    val junitKtxVersion = "1.1.5"
    testImplementation("androidx.test.ext:junit-ktx:$junitKtxVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitKtxVersion")

    // AssertJ
    val assertJVersion = "3.24.2"
    testImplementation("org.assertj:assertj-core:$assertJVersion")

    // Mockito
    val mockitoKotlinVersion = "5.2.1"
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    androidTestImplementation("org.mockito:mockito-android:5.8.0")

    // Coroutine tests
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Robolectric
    testImplementation("org.robolectric:robolectric:4.11.1")

    // Okhttp MockWebServer
    val mockWebServerVersion = "4.12.0"
    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebServerVersion")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}