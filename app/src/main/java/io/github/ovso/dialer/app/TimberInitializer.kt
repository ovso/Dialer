package io.github.ovso.dialer.app

import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.DEBUG
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    if (DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    Logger.d("create")
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf(LoggerInitializer::class.java)
  }
}
