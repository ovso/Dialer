package io.github.ovso.dialer.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.ovso.dialer.data.local.model.ContactEntity

@Dao
interface ContactDao {
  @Query("SELECT * FROM contacts WHERE contactId LIKE :contactId")
  suspend fun getContact(contactId: Long): ContactEntity

  @Query("SELECT * FROM contacts")
  fun getContacts(): LiveData<List<ContactEntity>>

  @Query("SELECT * FROM contacts WHERE parent = :groupId")
  fun getContacts(groupId: Long): LiveData<List<ContactEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertContact(entity: ContactEntity)

  @Update
  suspend fun updateContact(entity: ContactEntity)

  @Delete
  suspend fun deleteContact(entity: ContactEntity)

}
