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
        private val USERTOKEN = stringPreferencesKey("UserToken")
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

    /*----------------------------------------------- Get Token -----------------------------------------------*/

    val getToken: Flow<String>
        get() = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preference ->
            (preference[USERTOKEN] ?: "").toString()
        }


    suspend fun setToken(token: String) {
        dataStore.edit { preference ->
            preference[USERTOKEN] = token
        }
    }

//    var getOverallValue: Flow<String> = dataStore.data.map {
//        it[USERLISTVALUES] ?: ""
//    }
//
//    suspend fun setOverAllValues(listName: String) {
//        dataStore.edit {
//            it[USERLISTVALUES] = listName
//        }
//    }



    suspend fun saveListToDataStore(dataList: List<String>) {
        dataStore.edit { preferences ->
            preferences[USERLISTVALUES] = dataList.toSet() // Convert List to Set
        }
    }

    fun getListFromDataStore(): Flow<Set<String>> {
        return dataStore.data
            .map { preferences ->
                preferences[USERLISTVALUES] ?: emptySet() // Default to empty set if the key doesn't exist
            }
    }

}