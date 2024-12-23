package com.sample.itunes.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

class AppPreference @Inject constructor(context: Context) {

    companion object {
        private const val PREFERENCE_NAME = "APP_PREFERENCE"
        private val TERM = stringPreferencesKey("Term")
        private val USERLISTVALUES = stringSetPreferencesKey("UserListValue")

    }

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCE_NAME
    )

    private val dataStore = context.userPreferencesDataStore

    /*----------------------------------------------- Clear Preference -----------------------------------------------*/

    suspend fun clearAppPreference() {
        dataStore.edit { preference ->
            preference.clear()
        }
    }

    /*----------------------------------------------- Get Term -----------------------------------------------*/

    val getTerm: Flow<String>
        get() = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            (preference[TERM] ?: "").toString()
        }


    suspend fun setTerm(token: String) {
        dataStore.edit { preference ->
            preference[TERM] = token
        }
    }

    /*----------------------------------------------- Get MediaList -----------------------------------------------*/

    suspend fun saveListToDataStore(dataList: List<String>) {
        dataStore.edit { preferences ->
            preferences[USERLISTVALUES] = dataList.toSet()
        }
    }

    fun getListFromDataStore(): Flow<Set<String>> {
        return dataStore.data
            .map { preferences ->
                preferences[USERLISTVALUES] ?: emptySet()
            }
    }

}