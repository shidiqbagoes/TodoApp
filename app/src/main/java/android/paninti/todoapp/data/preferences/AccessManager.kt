package android.paninti.todoapp.data.preferences

import android.content.Context
import androidx.datastore.DataStoreFactory
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.*

class AccessManager(context: Context) {
    private val logDebug = Timber.DebugTree()
    private val dataStore = context.createDataStore(name = AccessOption.HAS_ACCESS.name)

    suspend fun setAccess(accessOption: AccessOption) {
        dataStore.edit { preferences ->
            preferences[loginKey] = when(accessOption) {
                AccessOption.HAS_ACCESS -> true
                AccessOption.NO_ACCESS -> false
            }
        }
    }

    val access: Flow<AccessOption> = dataStore.data
            .catch { throwable ->
                emit(emptyPreferences())
                logDebug.e(throwable)
            }.map { preferences ->
                when(preferences[loginKey] ?: false) {
                    true -> AccessOption.HAS_ACCESS
                    false -> AccessOption.NO_ACCESS
                }
    }
}

private val loginKey = preferencesKey<Boolean>(AccessOption.HAS_ACCESS.name)

enum class AccessOption {
    HAS_ACCESS, NO_ACCESS
}