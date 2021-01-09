package io.github.ovso.dialer.data.mapper

import androidx.annotation.WorkerThread
import io.github.ovso.dialer.data.args.DialerArgs
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.view.GroupModel


@WorkerThread
fun List<GroupEntity>.toGroupModels(): List<GroupModel> {
  return map {
    GroupModel(
      groupId = it.groupId,
      name = it.name
    )
  }
}

@WorkerThread
fun GroupModel.toDialerArgs(): DialerArgs {
  return DialerArgs(
    groupId = groupId
  )
}

@WorkerThread
fun GroupModel.toGroupEntity(): GroupEntity {
  return GroupEntity(
    groupId = groupId,
    name = name,
  )
}
