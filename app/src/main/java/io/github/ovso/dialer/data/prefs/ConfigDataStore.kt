package io.github.ovso.dialer.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConfigDataStore @Inject constructor(@ActivityContext context: Context) {
  private val dataStore = context.createDataStore(name = "config_prefs")

  suspend fun storeConfig(firstRun: Boolean) {
    dataStore.edit {
      it[FIRST_RUN_KEY] = firstRun
    }
  }

  val firstRunFlow: Flow<Boolean> = dataStore.data.map {
    it[FIRST_RUN_KEY] ?: true
  }

  companion object {
    val FIRST_RUN_KEY = booleanPreferencesKey(name = "FIRST_RUN")
  }
}
