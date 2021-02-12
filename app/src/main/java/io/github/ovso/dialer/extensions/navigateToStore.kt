package io.github.ovso.dialer.extensions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment

fun Fragment.navigateToStore() {
  Intent(Intent.ACTION_VIEW).apply {
    data =
      Uri.parse("https://play.google.com/store/apps/details?id=${requireContext().packageName}")
    setPackage("com.android.vending")
    startActivity(this)
  }
}

fun Activity.navigateToStore() {
  Intent(Intent.ACTION_VIEW).apply {
    data =
      Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")
    setPackage("com.android.vending")
    startActivity(this)
  }
}
