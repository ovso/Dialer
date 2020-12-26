package io.github.ovso.dialer.app

import android.content.Context
import androidx.startup.Initializer
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.github.ovso.dialer.DEBUG

class LoggerInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    Logger.addLogAdapter(object : AndroidLogAdapter() {
      override fun isLoggable(priority: Int, tag: String?): Boolean = DEBUG
    })
    Logger.d("create")
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}
