package io.github.ovso.dialer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity

@Database(entities = [GroupEntity::class, ContactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun groupsDao(): GroupsDao
  abstract fun groupDao(): GroupDao
  abstract fun contactDao(): ContactDao
}
