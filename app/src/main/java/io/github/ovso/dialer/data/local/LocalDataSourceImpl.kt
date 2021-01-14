package io.github.ovso.dialer.data.local

import android.content.Context
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ActivityContext
import io.github.ovso.dialer.app.DialerApplication
import io.github.ovso.dialer.data.local.model.ContactEntity
import io.github.ovso.dialer.data.local.model.GroupEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(@ActivityContext context: Context) : LocalDataSource {
  private val database = (context.applicationContext as DialerApplication).database

  override fun getGroups(): LiveData<List<GroupEntity>> {
    return database.groupDao().getGroups()
  }

  override suspend fun getContact(contactId: Long): ContactEntity {
    return database.contactDao().getContact(contactId)
  }

  override fun getContacts(): LiveData<List<ContactEntity>> {
    return database.contactDao().getContacts()
  }

  override fun getContacts(groupId: Long): LiveData<List<ContactEntity>> {
    return database.contactDao().getContacts(groupId)
  }

  override suspend fun insertGroup(entity: GroupEntity) {
    database.groupDao().insertGroup(entity)
  }

  override suspend fun deleteGroup(entity: GroupEntity) {
    database.groupDao().deleteGroup(entity)
  }

  override suspend fun insertContact(entity: ContactEntity) {
    database.contactDao().insertContact(entity)
  }

  override suspend fun updateContact(entity: ContactEntity) {
    database.contactDao().updateContact(entity)
  }

  override suspend fun deleteContact(entity: ContactEntity) {
    database.contactDao().deleteContact(entity)
  }

}
