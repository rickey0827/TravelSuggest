package com.soft507.travelsuggest.util

import android.content.Context
import android.content.SharedPreferences
import com.soft507.travelsuggest.TravelApp
import java.lang.RuntimeException


/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/1 12:42
 */
object SPUtils {

    private const val SPFileName = "travelConfig"
    private lateinit var sp: SharedPreferences


    /**
     * @param key
     * @param Value
     */
    fun put(context: Context, key: String, Value: Any) {
        sp = context.getSharedPreferences(SPFileName, Context.MODE_PRIVATE)
        when (Value) {
            Value is String -> sp.edit().putString(key, Value as String).apply()
            Value is Boolean -> sp.edit().putBoolean(key, Value as Boolean).apply()
            Value is Int -> sp.edit().putInt(key, Value as Int).apply()
        }
    }

    /**
     * @param key
     * @param Value
     */
    fun get(context: Context, key: String, Value: Any): Any? {
        sp = context.getSharedPreferences(SPFileName, Context.MODE_PRIVATE)
        return when (Value) {
            Value is String -> sp.getString(key, Value as String)
            Value is Boolean -> sp.getBoolean(key, Value as Boolean)
            Value is Int -> sp.getInt(key, Value as Int)
            else -> throw RuntimeException("type error")
        }
    }
}