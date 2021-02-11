package io.github.ovso.dialer.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import io.github.ovso.dialer.data.local.AppDatabase
import io.github.ovso.dialer.data.prefs.ConfigDataStore

@HiltAndroidApp
class DialerApplication : Application() {
  lateinit var database: AppDatabase
  lateinit var configDataStore: ConfigDataStore

  override fun onCreate() {
    super.onCreate()
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    database = AppInitializer
      .getInstance(this)
      .initializeComponent(DatabaseInitializer::class.java)
    configDataStore = ConfigDataStore(this)
  }
}
