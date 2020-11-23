plugins {
    id(Plugin.application)
    id(Plugin.androidKotlin)
    id(Plugin.kotlinKapt)
    id(Plugin.hilt)
    id(Plugin.navArgs)
}

android {
    compileSdkVersion(Version.Build.compileSdkVersion)
    buildToolsVersion(Version.Build.buildToolsVersion)

    defaultConfig {
        applicationId = Version.Build.applicationId
        versionCode = Version.Build.versionCode
        versionName = Version.Build.versionName

        minSdkVersion(Version.Build.minSdkVersion)
        targetSdkVersion(Version.Build.targetSdkVersion)

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildFeatures {
        viewBinding =  true
    }

    buildTypes {
        getByName(BuildType.release) {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(ProGuard.optimizeRule),
                ProGuard.optimizeFile
            )
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    
    kotlinOptions {
        jvmTarget = Version.Build.jvmTarget
    }
}

dependencies {

    implementation(Dependencies.coreKtx)

    implementation(Dependencies.Kotlin.kotlin)
    implementation(Dependencies.Kotlin.Coroutines.coroutinesCore)
    implementation(Dependencies.Kotlin.Coroutines.coroutinesAndroid)

    implementation(Dependencies.UI.appCompat)
    implementation(Dependencies.UI.materialDesign)
    implementation(Dependencies.UI.constraintLayout)

    implementation(Dependencies.Jetpack.lifeCycle)
    implementation(Dependencies.Jetpack.liveDataKtx)
    implementation(Dependencies.Jetpack.viewModelKtx)
    implementation(Dependencies.Jetpack.viewModelRuntime)
    implementation(Dependencies.Jetpack.navigation)
    implementation(Dependencies.Jetpack.navigationUi)
    implementation(Dependencies.Jetpack.roomKtx)
    implementation(Dependencies.Jetpack.roomRuntime)
    implementation(Dependencies.Jetpack.dataStore)

    implementation(Dependencies.Logging.timber)
    implementation(Dependencies.Media.glide)

    implementation(Dependencies.Injection.dagger)
    implementation(Dependencies.Injection.viewModelInject)

    kapt(Dependencies.Jetpack.roomKapt)
    kapt(Dependencies.Injection.daggerCompiler)
    kapt(Dependencies.Injection.androidCompiler)
    kapt(Dependencies.Media.glideCompilerKapt)

    testImplementation(Dependencies.Testing.jUnit)
    androidTestImplementation(Dependencies.Testing.jUnitTest)
    androidTestImplementation(Dependencies.Testing.espresso)
}