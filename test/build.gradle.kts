plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.test"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "com.example.test.TestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    androidTestImplementation(project(":app"))
    androidTestImplementation(project(":data"))
    androidTestImplementation(project(":domain"))
    androidTestImplementation(project(":presentation"))
    implementation(project(":presentation"))

    testImplementation(project(":app"))
    testImplementation(project(":data"))
    testImplementation(project(":domain"))
    testImplementation(project(":presentation"))

    implementation("androidx.compose.ui:ui")
    val composeBom = platform("androidx.compose:compose-bom:2023.06.01")
    implementation(composeBom)
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity-ktx:1.7.2")

    //hilt
    implementation("com.google.dagger:hilt-android:2.46")
    androidTestImplementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-compiler:2.46")
    //test
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.46")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.46")
    androidTestAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.46")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    implementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")

    //Local unit test
    testImplementation("com.google.dagger:hilt-android-testing:2.46")
    kaptTest("com.google.dagger:hilt-android-compiler:2.46")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("app.cash.turbine:turbine:0.12.1")
    testImplementation ("androidx.room:room-testing:2.4.1")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    testImplementation("io.reactivex.rxjava2:rxjava:2.2.20")
    testImplementation("androidx.room:room-rxjava2:2.6.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
}