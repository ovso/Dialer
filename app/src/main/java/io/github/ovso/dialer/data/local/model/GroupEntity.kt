package io.github.ovso.dialer.data.local.model

import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = GroupEntity.TABLE_NAME)
data class GroupEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(index = true, name = COLUMN_ID)
  val id: Int,
  @ColumnInfo(name = COLUMN_NAME)
  val name: String,
  @ColumnInfo(name = COLUMN_INDEX)
  val index: Int
) {
  companion object {
    const val TABLE_NAME = "groups"
    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_NAME = "name"
    const val COLUMN_INDEX = "index"
  }
}
