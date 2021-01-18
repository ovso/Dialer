package io.github.ovso.dialer.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.ovso.dialer.data.local.model.GroupEntity

@Dao
interface GroupDao {
  @Query("SELECT * FROM groups")
  fun getGroups(): LiveData<List<GroupEntity>>

  @Insert
  suspend fun insertGroup(entity: GroupEntity)

  @Update
  suspend fun updateGroup(entity: GroupEntity)

  @Delete
  suspend fun deleteGroup(entity: GroupEntity)
}
