package io.github.ovso.dialer.data.local.model

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ContactEntity.TABLE_NAME)
data class ContactEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(index = true, name = COLUMN_ID)
  val id: Long,

  @ColumnInfo(name = COLUMN_NM)
  val name: String,

  @ColumnInfo(name = COLUMN_NO)
  val no: String,

  @ColumnInfo(name = COLUMN_COLOR)
  val color: String
) {
  companion object {
    const val TABLE_NAME = "contacts"
    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_NM = "nm"
    const val COLUMN_NO = "no"
    const val COLUMN_COLOR = "color"
  }
}
