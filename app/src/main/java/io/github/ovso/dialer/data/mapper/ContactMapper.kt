package io.github.ovso.dialer.data.mapper

import androidx.annotation.WorkerThread
import io.github.ovso.dialer.data.args.ContactDialogArgs
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.view.ContactsDialogModel
import io.github.ovso.dialer.data.view.DialerItemModel

@WorkerThread
fun List<ContactEntity>.toDialerItemModels(): List<DialerItemModel> {
  return when (count() > 0) {
    true -> {
      map {
        DialerItemModel(
          contactId = it.contactId,
          name = it.name,
          no = it.no,
          color = it.color
        )
      }.toMutableList().apply {
        add(DialerItemModel.empty())
      }.toList()
    }
    else -> {
      listOf(DialerItemModel.empty())
    }
  }
}

@WorkerThread
fun ContactDialogArgs.toContactDialogModel(): ContactsDialogModel {
  return ContactsDialogModel(
    nm = name,
    no = no,
    color = color,
    type = type
  )
}
