object Version {
    object Build {
        const val compileSdkVersion = 29
        const val buildToolsVersion = "29.0.3"
        const val jvmTarget = "1.8"

        const val applicationId = "android.paninti.todoapp"
        const val versionName = "1.0"

        const val minSdkVersion = 21
        const val targetSdkVersion = 29
        const val versionCode = 1
    }

    object ClassPath {
        const val navVersion = "2.3.0-alpha06"
        const val hiltVersion = "2.28-alpha"
        const val gradleVersion = "4.1.1"
    }

    const val kotlinVersion = "1.4.20"
    const val coreKtxVersion = "1.3.2"
    const val appCompatVersion = "1.2.0"
    const val materialDesignVersion = "1.2.1"
    const val constraintVersion = "2.0.4"

    const val jUnitVersion = "4.13.1"
    const val jUnitTestVersion = "1.1.2"
    const val espressoVersion = "3.3.0"

    const val lifecycleVersion = "2.2.0"
    const val navigationVersion = "2.3.0"
    const val pagingVersion = "3.0.0-alpha03"
    const val roomVersion = "2.2.5"
    const val dataStoreVersion = "1.0.0-alpha02"

    const val coroutinesVersion = "1.3.5"

    const val hiltVersion = "2.28-alpha"
    const val hiltViewModelVersion = "1.0.0-alpha01"

    const val glideVersion = "4.11.0"

    const val timberVersion = "4.7.1"
}

object Dependencies {
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtxVersion}"

    object ClassPath {
        const val gradle = "com.android.tools.build:gradle:${Version.ClassPath.gradleVersion}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
        const val navArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.ClassPath.navVersion}"
        const val hiltAndroid = "com.google.dagger:hilt-android-gradle-plugin:${Version.ClassPath.hiltVersion}"
    }

    object UI {
        const val appCompat = "androidx.appcompat:appcompat:${Version.appCompatVersion}"
        const val materialDesign = "com.google.android.material:material:${Version.materialDesignVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintVersion}"
    }

    object Logging {
        const val timber = "com.jakewharton.timber:timber:${Version.timberVersion}"
    }

    object Jetpack {
        const val viewModelKtx =  "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleVersion}"
        const val viewModelRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleVersion}"
        const val lifeCycle = "androidx.lifecycle:lifecycle-common-java8:${Version.lifecycleVersion}"
        const val liveDataKtx =  "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycleVersion}"
        const val paging = "androidx.paging:paging-runtime:${Version.pagingVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Version.roomVersion}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.roomVersion}"
        const val roomKapt = "androidx.room:room-compiler:${Version.roomVersion}"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:${Version.lifecycleVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.lifecycleVersion}"
        const val dataStore = "androidx.datastore:datastore-preferences:${Version.dataStoreVersion}"
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"

        object Coroutines {
            const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutinesVersion}"
            const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutinesVersion}"
        }
    }

    object Media {
        const val glide = "com.github.bumptech.glide:glide:${Version.glideVersion}"
        const val glideCompilerKapt = "com.github.bumptech.glide:compiler:${Version.glideVersion}"
    }

    object Injection {
        const val dagger = "com.google.dagger:hilt-android:${Version.hiltVersion}"
        const val daggerCompiler = "androidx.hilt:hilt-compiler:${Version.hiltViewModelVersion}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hiltVersion}"
        const val viewModelInject = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltViewModelVersion}"
    }

    object Testing {
        const val jUnit = "junit:junit:${Version.jUnitVersion}"
        const val jUnitTest = "androidx.test.ext:junit:${Version.jUnitTestVersion}"
        const val espresso = "androidx.test.espresso:espresso-core:${Version.espressoVersion}"
    }
}

object Config {
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildType {
    const val debug = "debug"
    const val release = "release"
}

object ProGuard {
    const val optimizeRule = "proguard-android-optimize.txt"
    const val optimizeFile = "proguard-rules.pro"
}

object Plugin {
    const val application = "com.android.application"
    const val androidKotlin = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val navArgs = "androidx.navigation.safeargs.kotlin"
    const val hilt = "dagger.hilt.android.plugin"
}

object Repositories {
    const val jitpackUrl = "https://jitpack.io"
}

object Task {
    const val clean = "clean"
}