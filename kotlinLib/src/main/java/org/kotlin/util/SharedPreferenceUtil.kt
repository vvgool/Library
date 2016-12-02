package org.kotlin.util

import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
/**
 * Created by wiesen on 2016/11/24.
 */
class SharedPreferenceUtil {
    companion object{
        private val mSharedPreferenceUtil: SharedPreferenceUtil by lazy { SharedPreferenceUtil() }
        private  var mSharedPreferences:SharedPreferences? = null

        fun createSharedPrePreference(sp:SharedPreferences): SharedPreferenceUtil {
            mSharedPreferences = sp
            return mSharedPreferenceUtil
        }
    }

    fun <T> getPreference(key :String,default:T):T = with(mSharedPreferences!!) {
        val value:Any= when (default) {
            is Boolean -> getBoolean(key,default)
            is Int     -> getInt(key,default)
            is Float   -> getFloat(key,default)
            is Long    -> getLong(key,default)
            is String  -> getString(key,default)
            else -> throw IllegalArgumentException("type error")
        }
        value as T
    }

    fun <T> putPreference(key:String,value:T) = with(mSharedPreferences!!.edit()){
        when(value){
            is Boolean -> putBoolean(key,value)
            is Int     -> putInt(key,value)
            is Float   -> putFloat(key,value)
            is Long    -> putLong(key,value)
            is String  -> putString(key,value)
            else -> throw IllegalArgumentException("type error")
        }.apply()
    }
}