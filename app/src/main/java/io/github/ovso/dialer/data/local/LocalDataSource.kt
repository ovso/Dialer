package io.github.ovso.dialer.data.local

import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity

interface LocalDataSource {
  suspend fun getGroups(): List<GroupEntity>
  suspend fun getContact(contactId: Long): ContactEntity
  suspend fun getContacts(): List<ContactEntity>
}
