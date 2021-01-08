package io.github.ovso.dialer.app

import android.app.Application
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import io.github.ovso.dialer.data.local.AppDatabase

@HiltAndroidApp
class DialerApplication : Application() {
  lateinit var database: AppDatabase
  override fun onCreate() {
    super.onCreate()
    database = AppInitializer
      .getInstance(this)
      .initializeComponent(DatabaseInitializer::class.java)
  }
}
