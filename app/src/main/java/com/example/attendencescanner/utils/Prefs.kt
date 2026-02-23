package com.example.attendencescanner.utils

import android.content.Context

object Prefs {
    private const val FILE = "attendance_prefs"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_ROLE = "role"

    fun saveUser(context: Context, userId: Long, role: String) {
        context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_USER_ID, userId)
            .putString(KEY_ROLE, role)
            .apply()
    }

    fun getUserId(context: Context): Long =
        context.getSharedPreferences(FILE, Context.MODE_PRIVATE).getLong(KEY_USER_ID, -1)

    fun getRole(context: Context): String? =
        context.getSharedPreferences(FILE, Context.MODE_PRIVATE).getString(KEY_ROLE, null)
}


