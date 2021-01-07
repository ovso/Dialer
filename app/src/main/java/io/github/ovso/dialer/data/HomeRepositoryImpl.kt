package io.github.ovso.dialer.data

import androidx.lifecycle.LiveData
import io.github.ovso.dialer.data.local.LocalDataSource
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
  private val local: LocalDataSource
) : HomeRepository {
  override fun getGroups(): LiveData<List<GroupEntity>> {
    return local.getGroups()
  }

  override suspend fun getContact(contactId: Long): ContactEntity {
    return withContext(Dispatchers.IO) {
      local.getContact(contactId)
    }
  }

  override fun getContacts(): LiveData<List<ContactEntity>> {
    return local.getContacts()
  }

  override suspend fun insertGroup(entity: GroupEntity) {
    return local.insertGroup(entity)
  }

  override suspend fun deleteGroup(entity: GroupEntity) {
    return local.deleteGroup(entity)
  }

  override suspend fun insertContact(entity: ContactEntity) {
    local.insertContact(entity)
  }

  override suspend fun deleteContact(entity: ContactEntity) {
    local.deleteContact(entity)
  }
}
