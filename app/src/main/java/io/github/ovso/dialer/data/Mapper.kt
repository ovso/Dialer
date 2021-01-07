package io.github.ovso.dialer.data

import androidx.annotation.WorkerThread
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.view.DialerItemModel
import io.github.ovso.dialer.data.view.GroupModel


@WorkerThread
fun List<GroupEntity>.toGroupModels(): List<GroupModel> {
  return map {
    GroupModel(
      groupId = it.groupId,
      name = it.name,
      index = it.index
    )
  }
}

@WorkerThread
fun List<ContactEntity>.toDialerItemModels(): List<DialerItemModel> {
  return map {
    DialerItemModel(
      contactId = it.contactId,
      name = it.name,
      no = it.no,
    )
  }.apply {
    toMutableList().also {
      it.add(
        DialerItemModel(
          contactId = 1,
          name = "",
          no = "",
          footer = true
        )
      )
    }
  }
}
