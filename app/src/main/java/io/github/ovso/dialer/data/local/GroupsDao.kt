package io.github.ovso.dialer.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.github.ovso.dialer.data.local.model.GroupWithContacts

@Dao
interface GroupsDao {
  @Transaction
  @Query("SELECT * FROM groups")
  fun getGroupsWithContacts(): List<GroupWithContacts>
}
