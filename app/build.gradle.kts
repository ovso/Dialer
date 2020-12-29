plugins {
  id("com.android.application")
  id("kotlin-android")
  id("kotlin-kapt")
  id("kotlin-parcelize")
  id("com.google.android.gms.oss-licenses-plugin")
//    id("com.google.firebase.crashlytics")
  id("com.google.gms.google-services")
  id("dagger.hilt.android.plugin")
  id("org.ajoberstar.grgit") version "4.0.2"
  id("androidx.navigation.safeargs.kotlin")
}

android {
  compileSdkVersion(30)

  defaultConfig {
    applicationId = "io.github.ovso.dialer"
    minSdkVersion(23)
    targetSdkVersion(30)
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
  }

  buildTypes {
    getByName("debug") {

    }
    getByName("release") {
      isMinifyEnabled = false
      proguardFile(getDefaultProguardFile("proguard-android.txt"))
      // global proguard settings
      proguardFile(file("proguard-rules.pro"))
      // library proguard settings
      val files = rootProject.file("proguard")
        .listFiles()
        ?.filter { it.name.startsWith("proguard") }
        ?.toTypedArray()

      proguardFiles(*files ?: arrayOf())

    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  buildFeatures {
    dataBinding = true
    viewBinding = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  lintOptions {
    disable("MissingTranslation")
  }

  packagingOptions {
    exclude("META-INF/kotlinx-io.kotlin_module")
    exclude("META-INF/atomicfu.kotlin_module")
    exclude("META-INF/kotlinx-coroutines-io.kotlin_module")
  }
}

dependencies {

  implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
  implementation("androidx.core:core-ktx:1.3.2")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("com.google.android.material:material:1.2.1")
  implementation("androidx.constraintlayout:constraintlayout:2.0.4")
  implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
  implementation("androidx.navigation:navigation-fragment-ktx:2.3.2")
  implementation("androidx.navigation:navigation-ui-ktx:2.3.2")
  implementation("androidx.activity:activity-ktx:1.2.0-rc01")
  implementation("androidx.fragment:fragment-ktx:1.3.0-rc01")

  // firebase
  implementation(platform("com.google.firebase:firebase-bom:26.1.1"))
//    implementation("com.google.firebase:firebase-crashlytics-ktx")
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-config-ktx")
  implementation("com.google.firebase:firebase-ads")

  // dagger hilt
  implementation("com.google.dagger:hilt-android:${Versions.hilt_android}")
  implementation("androidx.legacy:legacy-support-v4:1.0.0")
  kapt("com.google.dagger:hilt-android-compiler:${Versions.hilt_android}")

  // hilt
  implementation("androidx.hilt:hilt-common:${Versions.hilt}")
  implementation("androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}")
  kapt("androidx.hilt:hilt-compiler:${Versions.hilt}")

  // rx
  implementation("io.reactivex.rxjava3:rxjava:${Versions.rxjava3}")
  implementation("io.reactivex.rxjava3:rxandroid:${Versions.rxjava3_rxandroid}")
  implementation("io.reactivex.rxjava3:rxkotlin:${Versions.rxjava3_rxkotlin}")
  implementation("com.uber.autodispose2:autodispose:${Versions.autodispose}")

  // startup
  implementation("androidx.startup:startup-runtime:1.0.0")

  // json
  implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
  implementation("com.squareup.moshi:moshi:1.11.0")
  kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")
  implementation("com.google.code.gson:gson:2.8.6")

  // rxbinding
  implementation("com.jakewharton.rxbinding4:rxbinding:4.0.0")

  // log
  implementation("com.orhanobut:logger:2.2.0")
  implementation("com.jakewharton.timber:timber:4.7.1")

  // initializer
  implementation("androidx.startup:startup-runtime:1.0.0")

  // database
  implementation("androidx.room:room-runtime:${Versions.room}")
  kapt("androidx.room:room-compiler:${Versions.room}")
  implementation("androidx.room:room-ktx:${Versions.room}")
  implementation("androidx.room:room-rxjava2:${Versions.room}")

  implementation(project(":colorpicker"))

  testImplementation("junit:junit:4.13.1")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
