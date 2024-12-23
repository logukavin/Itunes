plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = libs.versions.nameSpace.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.nameSpace.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
        testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile(libs.versions.proguardAndroidOptimize.get()),
                libs.versions.proguardRulesPro.get()
            )
            buildConfigField("String", "BASE_URL", "\"https://itunes.apple.com/\"")
        }


        debug {
            buildConfigField("String", "API_DOMAIN", "\"itunes.apple.com\"")
            buildConfigField("String", "SHA_KEY", "\"sha256//jUo8jOXMTRp18hAtq1CpkTwBuw0/ChVLAyEIPhKR3s=\"")
            buildConfigField("String", "BASE_URL", "\"https://itunes.apple.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.testing)
    testImplementation(libs.androidx.turbine)
    testImplementation(libs.jupiter)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlin.mockk)
    testImplementation(libs.jupiter.engine)

    // Lifecycle
    implementation(libs.lifecycle.common)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.fragment)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime)

    //Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.retrofit.kotlin.coroutines.adapter)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Lottie
    implementation(libs.lottie)

    // Gson
    implementation(libs.gson)

    // Coil
    implementation(libs.coil)

    // Datastore
    implementation(libs.datastore)

}