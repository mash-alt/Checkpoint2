package com.example.checkpoint2.data

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun login(username: String) {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_USERNAME, username)
            .apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)

    fun getUsername(): String = prefs.getString(KEY_USERNAME, "Guest") ?: "Guest"

    companion object {
        private const val PREFS_NAME = "hotel_session"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_USERNAME = "username"
    }
}

