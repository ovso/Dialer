package io.github.ovso.dialer.extensions

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
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

fun Fragment.showInterstitialAd(
  onClose: (() -> Unit)? = null,
  onFailed: (() -> Unit)? = null
) {
  val interstitialAd = InterstitialAd(context)
  interstitialAd.adListener = object : AdListener() {
    override fun onAdLoaded() {
      super.onAdLoaded()
      interstitialAd.show()
    }

    override fun onAdClosed() {
      super.onAdClosed()
      onClose?.invoke()
    }

    override fun onAdFailedToLoad(p0: LoadAdError?) {
      super.onAdFailedToLoad(p0)
      onFailed?.invoke()
    }
  }
  interstitialAd.adUnitId = getString(R.string.ad_interstitial_unit_id)
  val adRequestBuilder = AdRequest.Builder()
  interstitialAd.loadAd(adRequestBuilder.build())
}
