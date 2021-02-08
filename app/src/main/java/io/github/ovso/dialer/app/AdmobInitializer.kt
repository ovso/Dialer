package io.github.ovso.dialer.app

import android.content.Context
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds

class AdmobInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    MobileAds.initialize(context)
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> {
    return mutableListOf()
  }
}
