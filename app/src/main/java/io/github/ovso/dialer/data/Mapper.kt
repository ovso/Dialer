package io.github.ovso.dialer.data

import androidx.annotation.WorkerThread
import io.github.ovso.dialer.data.local.model.GroupEntity
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
