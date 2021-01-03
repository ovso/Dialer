package io.github.ovso.dialer.data

import io.github.ovso.dialer.data.local.LocalDataSource
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
  private val local: LocalDataSource
) : HomeRepository {
  override suspend fun getGroups(): List<GroupEntity> {
    return local.getGroups()
  }

  override suspend fun getContact(contactId: Long): ContactEntity {
    return local.getContact(contactId)
  }

  override suspend fun getContacts(): List<ContactEntity> {
    return local.getContacts()
  }

}
