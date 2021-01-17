// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  val kotlin_version by extra("1.4.21")
  repositories {
    google()
    jcenter()
    maven {
      url = uri("https://plugins.gradle.org/m2/")
    }
  }
  dependencies {
    classpath("com.android.tools.build:gradle:4.2.0-beta03")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    classpath("com.google.android.gms:oss-licenses-plugin:${Versions.licensesPlugin}")
    classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt_android}")
    classpath("com.google.gms:google-services:${Versions.googleService}")
    classpath("com.google.firebase:firebase-crashlytics-gradle:2.4.1")
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven {
      url = uri("https://jitpack.io")
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
