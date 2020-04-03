package xyz.mayahiro.simplerefreshjwtexample

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferenceManager {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("example", Context.MODE_PRIVATE)
    }

    // Token
    private const val KEY_TOKEN = "key_token"

    fun setToken(token: String) = sharedPreferences.edit(commit = true) { putString(KEY_TOKEN, token) }
    fun getToken(): String = sharedPreferences.getString(KEY_TOKEN, "") ?: ""
}
