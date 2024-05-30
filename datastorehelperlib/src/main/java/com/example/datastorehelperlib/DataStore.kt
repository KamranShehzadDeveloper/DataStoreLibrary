package com.example.datastorehelperlib

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry

class DataStore(private val context: Context, private val dateStoreName: String) {

    val Context.dataStore by preferencesDataStore(name = this.dateStoreName)
    private suspend fun <T> writePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
    fun readBooleanPreference(
        key: String,
        defaultValue: Boolean
    ): Flow<Boolean> {
        return context.dataStore.data.map { preference ->
            preference[booleanPreferencesKey(key)] ?: defaultValue
        }
    }
    fun readStringPreference(
        key: String,
        defaultValue: String
    ): Flow<String> {
        return context.dataStore.data.map { preference ->
            preference[stringPreferencesKey(key)] ?: defaultValue
        }
    }
    fun readIntPreference(
        key: String,
        defaultValue: Int
    ): Flow<Int> {
        return context.dataStore.data.map { preference ->
            preference[intPreferencesKey(key)] ?: defaultValue
        }
    }
    fun readLongPreference(
        key: String,
        defaultValue: Long
    ): Flow<Long> {
        return context.dataStore.data.map { preference ->
            preference[longPreferencesKey(key)] ?: defaultValue
        }
    }
    fun readFloatPreference(
        key: String,
        defaultValue: Float
    ): Flow<Float> {
        return context.dataStore.data.map { preference ->
            preference[floatPreferencesKey(key)] ?: defaultValue
        }
    }
    fun readDoublePreference(
        key: String,
        defaultValue: Double
    ): Flow<Double> {
        return context.dataStore.data.map { preference ->
            preference[doublePreferencesKey(key)] ?: defaultValue
        }
    }
    fun readObjectPreference(
        key: String,
        defaultValue: String
    ): Flow<String> {
        return context.dataStore.data.map { preference ->
            preference[stringPreferencesKey(key)] ?: error("Not Ready Yet")
        }.retry(3) {
            delay(100)
            true
        }.catch {

        }
    }
    suspend fun setInt(key: String, value: Int) {
        writePreference(intPreferencesKey(key), value)
    }
    suspend fun setLong(key: String, value: Long) {
        writePreference(longPreferencesKey(key), value)
    }
    suspend fun setFloat(key: String, value: Float) {
        writePreference(floatPreferencesKey(key), value)
    }
    suspend fun setDouble(key: String, value: Double) {
        writePreference(doublePreferencesKey(key), value)
    }
    suspend fun setString(key: String, value: String) {
        writePreference(stringPreferencesKey(key), value)
    }
    suspend fun setBoolean(key: String, value: Boolean) {
        writePreference(booleanPreferencesKey(key), value)
    }
    suspend fun <T> setObject(key: String, value: T) {
        val userJson = Gson().toJson(value)
        writePreference(stringPreferencesKey(key), userJson)
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DataStore? = null

        fun initInstance(context: Context, dateStoreName: String): DataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStore(context, dateStoreName)
                INSTANCE = instance
                instance
            }
        }

        fun getInstance(): DataStore {
            return INSTANCE ?: error("DataStore not initialized!")
        }
    }
}