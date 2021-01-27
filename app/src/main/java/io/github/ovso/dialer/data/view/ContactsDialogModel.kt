package io.github.ovso.dialer.data.view

import io.github.ovso.dialer.view.ui.dialer.ContactsDialog

data class ContactsDialogModel(
  val nm: String,
  val no: String,
  val color: String,
  val type:ContactsDialog.Type,
  val groupId:Long
)
