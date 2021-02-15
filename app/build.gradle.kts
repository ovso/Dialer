@file:Suppress("SpellCheckingInspection")
import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

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

val keystorePropertiesFile = rootProject.file("../jks/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
  compileSdkVersion(DefaultConfig.compileSdk)

  defaultConfig {
    applicationId = DefaultConfig.appId
    minSdkVersion(DefaultConfig.minSdk)
    targetSdkVersion(DefaultConfig.targetSdk)
    versionCode = DefaultConfig.versionCode
    versionName = DefaultConfig.versionName
    setProperty("archivesBaseName", "Dialer-$versionName-$versionCode")
    testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
  }

  signingConfigs {
    getByName("debug") {

    }

    create("release") {
      keyAlias = keystoreProperties.getProperty("keyAlias")
      keyPassword = keystoreProperties.getProperty("keyPassword")
      storeFile = file(keystoreProperties.getProperty("storeFile"))
      storePassword = keystoreProperties.getProperty("storePassword")
    }
  }

  buildTypes {
    getByName("debug") {
      signingConfig = signingConfigs.getByName("debug")
    }

    getByName("release") {
      signingConfig = signingConfigs.getByName("release")
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

  implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
  implementation("com.google.android.material:material:${Versions.material}")
  implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}")

  implementation("androidx.core:core-ktx:${Versions.ktx_core}")

  implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ktx_livedata}")
  implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktx_viewmodel}")

  implementation("androidx.navigation:navigation-fragment-ktx:${Versions.ktx_navigation}")
  implementation("androidx.navigation:navigation-ui-ktx:${Versions.ktx_navigation}")

  implementation("androidx.activity:activity-ktx:${Versions.ktx_activity}")
  implementation("androidx.fragment:fragment-ktx:${Versions.ktx_fragment}")

  // firebase
  implementation(platform("com.google.firebase:firebase-bom:${Versions.firebase_bom}"))
//    implementation("com.google.firebase:firebase-crashlytics-ktx")
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-config-ktx")
  implementation("com.google.firebase:firebase-ads")

  // dagger hilt
  implementation("com.google.dagger:hilt-android:${Versions.hilt_android}")
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

  // initializer
  implementation("androidx.startup:startup-runtime:${Versions.startup}")

  // json
  implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
  implementation("com.squareup.moshi:moshi:${Versions.moshi}")
  kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}")
  implementation("com.google.code.gson:gson:${Versions.gson}")

  // rxbinding
  implementation("com.jakewharton.rxbinding4:rxbinding:${Versions.rxbinding4}")

  // log
  implementation("com.orhanobut:logger:${Versions.logger}")
  implementation("com.jakewharton.timber:timber:${Versions.timber}")

  // database
  implementation("androidx.room:room-runtime:${Versions.room}")
  kapt("androidx.room:room-compiler:${Versions.room}")
  implementation("androidx.room:room-ktx:${Versions.room}")
  implementation("androidx.room:room-rxjava2:${Versions.room}")

  // colorpicker
  implementation(project(":colorpicker"))

  // tutorial
//  implementation("com.github.takusemba:spotlight:2.0.3")

  // datastore
  implementation("androidx.datastore:datastore-preferences:${Versions.datastore_prefs}")
`
  // native ad
  implementation(project(":nativetemplates"))

  implementation("com.google.android.gms:play-services-oss-licenses:${Versions.licenses}")

  testImplementation("junit:junit:${Versions.junit}")
  androidTestImplementation("androidx.test.ext:junit:${Versions.atsl_junit}")
  androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.atsl_espresso}")
}
