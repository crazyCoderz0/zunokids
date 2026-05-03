package com.crazycoderz.zunokids.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kids_learn_prefs")

@Singleton
class UserPreferencesRepo @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
        val NUMBERS_COMPLETED  = booleanPreferencesKey("numbers_completed")
        val ALPHABET_COMPLETED = booleanPreferencesKey("alphabet_completed")
        val FRUITS_COMPLETED   = booleanPreferencesKey("fruits_completed")
        val COLORS_COMPLETED   = booleanPreferencesKey("colors_completed")
        val OBJECTS_COMPLETED  = booleanPreferencesKey("objects_completed")
        val LAST_MODULE        = stringPreferencesKey("last_module")
        val STARS              = intPreferencesKey("stars")
        val STREAK_COUNT       = intPreferencesKey("streak_count")
        val LAST_SEEN_DATE     = stringPreferencesKey("last_seen_date")
        val PARENT_PIN         = stringPreferencesKey("parent_pin")
        val TOTAL_TIME_SPENT   = intPreferencesKey("total_time_spent_minutes")
    }

    val userStats: Flow<UserStats> = context.dataStore.data.map { prefs ->
        UserStats(
            stars = prefs[STARS] ?: 0,
            streakCount = prefs[STREAK_COUNT] ?: 0,
            lastSeenDate = prefs[LAST_SEEN_DATE] ?: "",
            totalTimeSpent = prefs[TOTAL_TIME_SPENT] ?: 0
        )
    }

    val parentPin: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[PARENT_PIN] ?: "1234" }

    val selectedLanguage: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[SELECTED_LANGUAGE] ?: "en" }

    val completedModules: Flow<Set<String>> = context.dataStore.data
        .map { prefs ->
            buildSet {
                if (prefs[NUMBERS_COMPLETED]  == true) add("numbers")
                if (prefs[ALPHABET_COMPLETED] == true) add("alphabet")
                if (prefs[FRUITS_COMPLETED]   == true) add("fruits")
                if (prefs[COLORS_COMPLETED]   == true) add("colors")
                if (prefs[OBJECTS_COMPLETED]  == true) add("objects")
            }
        }

    val lastModule: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[LAST_MODULE] ?: "home" }

    suspend fun setLanguage(code: String) {
        context.dataStore.edit { it[SELECTED_LANGUAGE] = code }
    }

    suspend fun markCompleted(module: String) {
        context.dataStore.edit { prefs ->
            val key = when (module) {
                "numbers"  -> NUMBERS_COMPLETED
                "alphabet" -> ALPHABET_COMPLETED
                "fruits"   -> FRUITS_COMPLETED
                "colors"   -> COLORS_COMPLETED
                "objects"  -> OBJECTS_COMPLETED
                else       -> return@edit
            }
            prefs[key] = true
        }
    }

    suspend fun setLastModule(module: String) {
        context.dataStore.edit { it[LAST_MODULE] = module }
    }

    suspend fun addStars(count: Int) {
        context.dataStore.edit { prefs ->
            prefs[STARS] = (prefs[STARS] ?: 0) + count
        }
    }

    suspend fun updateStreak(date: String) {
        context.dataStore.edit { prefs ->
            val lastDateStr = prefs[LAST_SEEN_DATE] ?: ""
            if (lastDateStr.isEmpty()) {
                prefs[STREAK_COUNT] = 1
                prefs[LAST_SEEN_DATE] = date
                return@edit
            }

            if (lastDateStr == date) return@edit

            try {
                val lastDate = java.time.LocalDate.parse(lastDateStr)
                val currentDate = java.time.LocalDate.parse(date)
                
                when {
                    currentDate == lastDate.plusDays(1) -> {
                        prefs[STREAK_COUNT] = (prefs[STREAK_COUNT] ?: 0) + 1
                    }
                    currentDate.isAfter(lastDate.plusDays(1)) -> {
                        prefs[STREAK_COUNT] = 1
                    }
                }
                prefs[LAST_SEEN_DATE] = date
            } catch (e: Exception) {
                // Fallback for parsing errors
                prefs[STREAK_COUNT] = 1
                prefs[LAST_SEEN_DATE] = date
            }
        }
    }

    suspend fun setParentPin(pin: String) {
        context.dataStore.edit { it[PARENT_PIN] = pin }
    }

    suspend fun addTimeSpent(minutes: Int) {
        context.dataStore.edit { prefs ->
            prefs[TOTAL_TIME_SPENT] = (prefs[TOTAL_TIME_SPENT] ?: 0) + minutes
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { it.clear() }
    }
}

data class UserStats(
    val stars: Int,
    val streakCount: Int,
    val lastSeenDate: String,
    val totalTimeSpent: Int
)
