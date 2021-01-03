package io.github.ovso.dialer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.ovso.dialer.data.local.model.GroupEntity

@Dao
interface GroupDao {
  @Query("SELECT * FROM groups")
  suspend fun getGroups(): List<GroupEntity>

  @Insert
  suspend fun insertGroup(entity: GroupEntity)

  @Delete
  suspend fun deleteGroup(entity: GroupEntity)
}
