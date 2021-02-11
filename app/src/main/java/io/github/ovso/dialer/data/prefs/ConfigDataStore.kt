package io.github.ovso.dialer.data.prefs

import android.app.Activity
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConfigDataStore @Inject constructor(act: Activity) {
  private val dataStore = act.createDataStore(name = "config_prefs")

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
