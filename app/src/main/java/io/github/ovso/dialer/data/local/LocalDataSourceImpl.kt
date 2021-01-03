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

  override suspend fun getContacts(): List<ContactEntity> {
    return database.contactDao().getContacts()
  }
}
