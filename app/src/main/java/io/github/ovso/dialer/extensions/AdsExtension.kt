package io.github.ovso.dialer.extensions

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import dagger.hilt.android.internal.managers.ViewComponentManager
import io.github.ovso.dialer.R

fun Activity.adaptiveBannerAdSize(): AdSize {
  val display = windowManager.defaultDisplay
  val outMetrics = DisplayMetrics()
  display.getMetrics(outMetrics)

  val density = outMetrics.density

  var adWidthPixels = 0f
  if (adWidthPixels == 0f) {
    adWidthPixels = outMetrics.widthPixels.toFloat()
  }

  val adWidth = (adWidthPixels / density).toInt()
  return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
}


fun ViewGroup.loadAdaptiveBanner() {

  fun activityContext(): Context {
    return if (context is ViewComponentManager.FragmentContextWrapper) {
      return (context as ViewComponentManager.FragmentContextWrapper).baseContext
    } else context
  }

  val adView = AdView(context)
  addView(adView)

  fun load() {
    adView.adUnitId = context.getString(R.string.ad_banner_unit_id)
    adView.adSize = (activityContext() as Activity).adaptiveBannerAdSize()
    val adRequest = AdRequest.Builder().build()
    adView.loadAd(adRequest)
  }

  load()
}
