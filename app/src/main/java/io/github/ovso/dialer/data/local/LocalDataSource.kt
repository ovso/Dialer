package io.github.ovso.dialer.data.local

import androidx.lifecycle.LiveData
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {
  fun getGroups(): LiveData<List<GroupEntity>>
  suspend fun getContact(contactId: Long): ContactEntity
  fun getContacts(): LiveData<List<ContactEntity>>
  fun getContacts(groupId: Long): LiveData<List<ContactEntity>>
//  fun getContactsRx(groupId: Long): Single<List<ContactEntity>>
  suspend fun insertGroup(entity: GroupEntity)
  suspend fun updateGroup(entity: GroupEntity)
  suspend fun deleteGroup(entity: GroupEntity)
  suspend fun insertContact(entity: ContactEntity)
  suspend fun updateContact(entity: ContactEntity)
  suspend fun deleteContact(entity: ContactEntity)
}
