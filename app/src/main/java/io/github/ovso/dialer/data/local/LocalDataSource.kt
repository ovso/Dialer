package io.github.ovso.dialer.data.local

import androidx.lifecycle.LiveData
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity

interface LocalDataSource {
  fun getGroups(): LiveData<List<GroupEntity>>
  suspend fun getContact(contactId: Long): ContactEntity
  suspend fun getContacts(): List<ContactEntity>
}
