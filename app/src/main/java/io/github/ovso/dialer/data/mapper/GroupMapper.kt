package io.github.ovso.dialer.data.mapper

import androidx.annotation.WorkerThread
import io.github.ovso.dialer.data.args.DialerArgs
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.github.ovso.dialer.data.view.GroupModel
import io.github.ovso.dialer.data.view.GroupModifyDialogModel
import io.github.ovso.dialer.view.ui.home.GroupModifyDialog


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

/*
    이 확장함수가 쓰이는 경우는 두 가지다. 그룹삭제와 그룹명을 수정할 때다.
    호출하는 쪽에서 인자를 넣지 않으면 그룹을 삭제하기 위한 `GroupEntity`를 반환한다.
    호출하는 쪽에서 인자를 넣으면 그룹명을 편집하기 위한 `GroupEntity`를 반환한다.
 */
@WorkerThread
fun GroupModel.toGroupEntity(name: String? = null): GroupEntity {
  return GroupEntity(
    groupId = groupId,
    name = name ?: this.name, // `name`은 편집할때 쓰이고, `this.name`은 삭제할 때 쓰인다.
  )
}

@WorkerThread
fun GroupModel.toGroupModifyDialogModel(): GroupModifyDialogModel {
  return GroupModifyDialogModel(
    groupId = groupId,
    name = name
  )
}

/*
    이 확장함수가 쓰이는 경우는 두 가지다. 그룹삭제와 그룹명을 수정할 때다.
    호출하는 쪽에서 인자를 넣지 않으면 그룹을 삭제하기 위한 `GroupEntity`를 반환한다.
    호출하는 쪽에서 인자를 넣으면 그룹명을 편집하기 위한 `GroupEntity`를 반환한다.
 */
@WorkerThread
fun GroupModifyDialogModel.toGroupEntity(name: String? = null): GroupEntity {
  return GroupEntity(
    groupId = groupId,
    name = name ?: this.name, // `name`은 편집할때 쓰이고, `this.name`은 삭제할 때 쓰인다.
  )
}
