package com.example.employeemanagementsystem.data.local

import android.content.Context
import android.content.SharedPreferences

class LocalDb(context: Context){
    private val sharedPref: SharedPreferences = context.getSharedPreferences("employee_manager", Context.MODE_PRIVATE)

    companion object {
        const val KEY_USERID = "userid"
        const val KEY_USERNAME = "username"
        const val KEY_LOGGED_IN = "isLoggedIn"
    }

    // Save username
    fun saveUsername(username: String) {
        sharedPref.edit().putString(KEY_USERNAME, username).apply()
    }

    // Save userid
    fun saveUserId(userId: Int) {
        sharedPref.edit().putInt(KEY_USERID, userId).apply()
    }

    fun logout(){
        println(getUsername())
        println(getUserId())
        sharedPref.edit().remove(KEY_USERNAME).apply()
        sharedPref.edit().remove(KEY_USERID).apply()
        println(getUserId())
        println(getUsername())
    }

    // Get username
    fun getUsername(): String? {
        return sharedPref.getString(KEY_USERNAME, null)
    }

    // Get userid
    fun getUserId(): Int {
        return sharedPref.getInt(KEY_USERID, -1)
    }

    // Get login status
    fun isLoggedIn(): Boolean {
        val userId = getUserId()
        return userId != -1
    }

    // Clear all preferences
    fun clearPrefs() {
        sharedPref.edit().clear().apply()
    }
}