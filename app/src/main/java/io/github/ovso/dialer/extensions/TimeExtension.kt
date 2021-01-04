package io.github.ovso.dialer.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toStringTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
  val now = System.currentTimeMillis()
  val date = Date(now)
  return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}
