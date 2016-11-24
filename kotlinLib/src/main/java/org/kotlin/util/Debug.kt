package org.kotlin.util

import android.util.Log
import org.kotlin.BuildConfig

/**
 * Created by wiesen on 2016/11/23.
 */

inline fun<reified T> T.DebugE(msg:Any){
    if (BuildConfig.LOG_ENABLED) {
        Log.e(T::class.simpleName, msg.toString())

    }
}

inline fun <reified T> T.DebugI(msg:Any){
    if (BuildConfig.LOG_ENABLED){
        Log.i(T::class.simpleName,msg.toString())
    }
}

inline fun <reified T> T.DebugD(msg: Any){
    if (BuildConfig.LOG_ENABLED){
        Log.d(T::class.simpleName,msg.toString())
    }
}

inline fun <reified T> T.DebugV(msg: Any){
    if (BuildConfig.LOG_ENABLED){
        Log.v(T::class.simpleName,msg.toString())
    }
}

inline fun <reified T> T.DebugW(msg: Any){
    if (BuildConfig.LOG_ENABLED){
        Log.w(T::class.simpleName,msg.toString())
    }
}