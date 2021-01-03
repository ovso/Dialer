package io.github.ovso.dialer.data.local

import androidx.room.Dao
import androidx.room.Query
import io.github.ovso.dialer.data.local.model.ContactEntity

@Dao
interface ContactDao {
  @Query("SELECT * FROM contacts WHERE contactId LIKE :contactId")
  suspend fun getContact(contactId: Long): ContactEntity

  @Query("SELECT * FROM contacts")
  suspend fun getContacts(): List<ContactEntity>
}