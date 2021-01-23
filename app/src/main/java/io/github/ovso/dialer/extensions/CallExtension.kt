package io.github.ovso.dialer.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.makeCall(no: String) {
  Intent(Intent.ACTION_CALL, Uri.parse("tel:$no")).apply {
    startActivity(this)
  }
}
