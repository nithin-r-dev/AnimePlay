package org.anime.assessment.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager private constructor(context: Context) {
    private val prefManager: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_MAIN, Context.MODE_PRIVATE)

    fun setStringValue(keyName: String?, value: String?) {
        prefManager.edit().putString(keyName, value).apply()
    }

    fun setLongValue(keyName: String?, value: Long) {
        prefManager.edit().putLong(keyName, value).apply()
    }

    fun getStringValue(keyName: String?): String? {
        return prefManager.getString(keyName, "")
    }

    fun getLongValue(keyName: String?): Long {
        return prefManager.getLong(keyName, 0L)
    }

    fun getLanguageStringValue(keyName: String?): String? {
        return prefManager.getString(keyName, "en")
    }

    fun setBooleanValue(keyName: String?, value: Boolean) {
        prefManager.edit().putBoolean(keyName, value).apply()
    }

    fun getBooleanValue(keyName: String?): Boolean {
        return prefManager.getBoolean(keyName, false)
    }

    fun setIntValue(keyName: String?, value: Int) {
        prefManager.edit().putInt(keyName, value).apply()
    }

    fun getIntValue(keyName: String?): Int {
        return prefManager.getInt(keyName, 0)
    }

    fun setNotificationBooleanValue(keyName: String?, value: Boolean) {
        prefManager.edit().putBoolean(keyName, value).apply()
    }

    fun getNotificationBooleanValue(keyName: String?): Boolean {
        return prefManager.getBoolean(keyName, true)
    }

    fun setIntegerValue(keyName: String?, value: Int) {
        prefManager.edit().putInt(keyName, value).apply()
    }

    fun getIntegerValue(keyName: String?): Int {
        return prefManager.getInt(keyName, -1)
    }

    fun removePref(context: Context) {
        val preferences = context.getSharedPreferences(PREFERENCE_MAIN, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun removePref(`val`: String?) {
        val editor = prefManager.edit()
        editor.remove(`val`)
        editor.apply()
    }

    companion object {
        private val PREFERENCE_MAIN = PreferencesManager::class.java.getPackage().name
        private var prefInstance: PreferencesManager? = null

        @Synchronized
        fun getInstance(context: Context): PreferencesManager? {
            if (prefInstance == null) {
                prefInstance = PreferencesManager(context.applicationContext)
            }
            return prefInstance
        }
    }
}
