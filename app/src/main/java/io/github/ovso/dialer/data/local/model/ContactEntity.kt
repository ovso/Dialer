package io.github.ovso.dialer.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = ContactEntity.TABLE_NAME,
  foreignKeys = [ForeignKey(
    entity = GroupEntity::class,
    parentColumns = arrayOf(GroupEntity.COLUMN_GROUP_ID),
    childColumns = arrayOf(ContactEntity.COLUMN_PARENT)
  )]
)
data class ContactEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = COLUMN_CONTACT_ID)
  val contactId: Long,

  @ColumnInfo(name = COLUMN_NM)
  val name: String,

  @ColumnInfo(name = COLUMN_NO)
  val no: String,

  @ColumnInfo(name = COLUMN_COLOR)
  val color: String,

  @ColumnInfo(name = COLUMN_PARENT)
  val parent: Long

) {
  companion object {
    const val TABLE_NAME = "contacts"
    const val COLUMN_CONTACT_ID = "contactId"
    const val COLUMN_NM = "nm"
    const val COLUMN_NO = "no"
    const val COLUMN_COLOR = "color"
    const val COLUMN_PARENT = "parent"
  }
}
