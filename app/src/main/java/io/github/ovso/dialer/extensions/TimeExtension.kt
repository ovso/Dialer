@file:Suppress("SpellCheckingInspection")

package io.github.ovso.dialer.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toStringTime(pattern: String = "yyyyMMddHHmmss"): String {
  val now = System.currentTimeMillis()
  val date = Date(now)
  return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}
