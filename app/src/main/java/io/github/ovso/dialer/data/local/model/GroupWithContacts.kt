package io.github.ovso.dialer.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithContacts(
  @Embedded
  val group: GroupEntity,
  @Relation(
    parentColumn = GroupEntity.COLUMN_GROUP_ID,
    entityColumn = ContactEntity.COLUMN_CONTACT_ID
  )
  val contacts: List<ContactEntity>
)
