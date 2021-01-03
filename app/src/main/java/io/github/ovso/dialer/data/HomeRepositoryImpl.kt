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

  override suspend fun getContacts(): List<ContactEntity> {
    return withContext(Dispatchers.IO) {
      local.getContacts()
    }
  }

}
