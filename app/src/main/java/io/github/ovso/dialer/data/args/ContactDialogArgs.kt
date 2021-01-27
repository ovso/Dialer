package io.github.ovso.dialer.data.args

import androidx.lifecycle.LifecycleCoroutineScope
import io.github.ovso.dialer.view.ui.dialer.ContactsDialog

data class ContactDialogArgs(
  val name: String,
  val no: String,
  val color: String,
  val type: ContactsDialog.Type,
  val lifecycleCoroutineScope: LifecycleCoroutineScope,
  val groupId:Long
)
