package io.github.ovso.dialer.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ContactEntity.TABLE_NAME)
data class ContactEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = COLUMN_CONTACT_ID)
  val contactId: Long,

  @ColumnInfo(name = COLUMN_NM)
  val name: String,

  @ColumnInfo(name = COLUMN_NO)
  val no: String,

  @ColumnInfo(name = COLUMN_COLOR)
  val color: String
) {
  companion object {
    const val TABLE_NAME = "contacts"
    const val COLUMN_CONTACT_ID = "contactId"
    const val COLUMN_NM = "nm"
    const val COLUMN_NO = "no"
    const val COLUMN_COLOR = "color"
  }
}
