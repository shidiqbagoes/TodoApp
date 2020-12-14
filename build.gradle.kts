buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Dependencies.ClassPath.gradle)
        classpath(Dependencies.ClassPath.kotlinGradle)
        classpath(Dependencies.ClassPath.navArgs)
        classpath(Dependencies.ClassPath.hiltAndroid)
        classpath(Dependencies.ClassPath.googleGms)
    }
}

allprojects {
    repositories {
        google()
        jcenter()

        mavenCentral()
        maven { url = uri(Repositories.jitpackUrl) }
    }
}

tasks.register(Task.clean, Delete::class) {
    delete(rootProject.buildDir)
}