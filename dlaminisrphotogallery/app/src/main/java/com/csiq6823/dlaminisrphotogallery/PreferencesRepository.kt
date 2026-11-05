package com.csiq6823.dlaminisrphotogallery

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository private constructor(private val context: Context) {
    companion object {
        private val SEARCH_QUERY_KEY = stringPreferencesKey("search_query")
        @Volatile private var INSTANCE: PreferencesRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) synchronized(this) {
                if (INSTANCE == null) INSTANCE = PreferencesRepository(context.applicationContext)
            }
        }

        fun get(): PreferencesRepository =
            checkNotNull(INSTANCE) { "PreferencesRepository must be initialized" }
    }

    val storedQuery: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[SEARCH_QUERY_KEY].orEmpty()
    }

    suspend fun setStoredQuery(query: String) {
        context.dataStore.edit { prefs ->
            if (query.isBlank()) prefs.remove(SEARCH_QUERY_KEY)
            else prefs[SEARCH_QUERY_KEY] = query.trim()
        }
    }
}
