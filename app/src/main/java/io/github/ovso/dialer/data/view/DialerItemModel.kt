package io.github.ovso.dialer.data.view

data class DialerItemModel(
  val contactId:Long,
  val name: String,
  val no: String,
  val footer:Boolean = false
)
