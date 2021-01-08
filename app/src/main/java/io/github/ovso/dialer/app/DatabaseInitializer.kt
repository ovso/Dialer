package io.github.ovso.dialer.app

import android.content.Context
import androidx.room.Room
import androidx.startup.Initializer
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.data.local.AppDatabase

class DatabaseInitializer : Initializer<AppDatabase> {
  override fun create(context: Context): AppDatabase {
    Logger.d("DatabaseInitializer: create")
    return Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      "database.db"
    ).fallbackToDestructiveMigration().build()
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}
